package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.util.ArrayUtil;
import com.intellij.util.ObjectUtils;
import com.intellij.util.containers.OrderedSet;
import edu.clemson.resolve.plugin.psi.*;
import edu.clemson.resolve.plugin.psi.impl.scopeprocessing.ResScopeProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ResReference
        extends
            PsiPolyVariantReferenceBase<ResReferenceExpressionBase> {
    //doing types should be similar to the way go handles struct field accesses...
    public static final Key<String> ACTUAL_NAME = Key.create("ACTUAL_NAME");
    public static final Key<SmartPsiElementPointer<ResReferenceExpressionBase>> CONTEXT = Key.create("CONTEXT");

    private static final ResolveCache.PolyVariantResolver<PsiPolyVariantReferenceBase> MY_RESOLVER =
            new ResolveCache.PolyVariantResolver<PsiPolyVariantReferenceBase>() {
                @NotNull
                @Override
                public ResolveResult[] resolve(
                        @NotNull PsiPolyVariantReferenceBase psiPolyVariantReferenceBase,
                        boolean incompleteCode) {
                    return ((ResReference) psiPolyVariantReferenceBase)
                            .resolveInner();
                }
            };

    public ResReference(@NotNull ResReferenceExpressionBase o) {
        super(o, TextRange.from(o.getIdentifier().getStartOffsetInParent(),
                o.getIdentifier().getTextLength()));
    }

    //this is the big starting point (I think...)
    @NotNull private ResolveResult[] resolveInner() {
        Collection<ResolveResult> result = new OrderedSet<ResolveResult>();
        processResolveVariants(createResolveProcessor(result, myElement));
        return result.toArray(new ResolveResult[result.size()]);
    }

    @NotNull
    static ResScopeProcessor createResolveProcessor(
            @NotNull final Collection<ResolveResult> result,
            @NotNull final ResReferenceExpressionBase o) {
        return new ResScopeProcessor() {
            @Override
            public boolean execute(@NotNull PsiElement element,
                                   @NotNull ResolveState state) {
                if (element.equals(o))
                    return !result.add(new PsiElementResolveResult(element));

                String name = ObjectUtils.chooseNotNull(state.get(ACTUAL_NAME),
                        element instanceof PsiNamedElement ? ((PsiNamedElement) element).getName() : null);
                if (name != null && o.getIdentifier().textMatches(name)) {
                    result.add(new PsiElementResolveResult(element));
                    return false;
                }
                return true;
            }
        };
    }

    public boolean processResolveVariants(@NotNull ResScopeProcessor processor) {
        PsiFile file = myElement.getContainingFile();
        if (!(file instanceof ResFile)) return false;
        ResolveState state = createContext();
        ResReferenceExpressionBase qualifier = myElement.getQualifier();
        return //qualifier != null
                // ? processQualifierExpression(((GoFile)file), qualifier, processor, state)
                //:
                processUnqualifiedResolve(((ResFile) file), processor, state, true);
    }

    @NotNull public ResolveState createContext() {
        return ResolveState.initial().put(CONTEXT,
                SmartPointerManager.getInstance(myElement.getProject()).createSmartPsiElementPointer(myElement));
    }


    @Override @NotNull public ResolveResult[] multiResolve(
            boolean incompleteCode) {
        if (!myElement.isValid()) return ResolveResult.EMPTY_ARRAY;
        return ResolveCache.getInstance(myElement.getProject())
                .resolveWithCaching(this, MY_RESOLVER, false, false);
    }

    @NotNull @Override public Object[] getVariants() {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    static boolean processUsesRequests(@NotNull ResFile file,
                                       @NotNull ResScopeProcessor processor,
                                       @NotNull ResolveState state,
                                       @NotNull ResCompositeElement element) {
        List<PsiFile> usesItemsAsPsiFiles = new ArrayList<PsiFile>();

        for (ResUsesItem u : file.getUsesItems()) {
           /* if (file.getProject().getProjectFile() == null) continue;
            VirtualFile targetFile =
                    file.getProject().getProjectFile().findChild(u.getText());
            if (targetFile == null) continue;
            usesItemsAsPsiFiles.add(PsiManager.getInstance(file.getProject())
                    .findFile(targetFile));*/

            PsiFile x = u.resolve();
            int i;
            i=0;
        }

        /*for (PsiFile f : usesItemsAsPsiFiles) {
            if (!(f instanceof ResFile)) continue;
            if (!processFileEntities((ResFile)f, processor, state, true)) return false;
        }*/
       /* for (PsiFile f : dir.getFiles()) {
            if (!(f instanceof GoFile) || Comparing.equal(getPath(f), filePath)) continue;
            if (packageName != null && !packageName.equals(((GoFile)f).getPackageName())) continue;
            if (allowed(f, isTesting) && !processFileEntities((GoFile)f, processor, state, localProcessing)) return false;
        }*/
        return true;
    }

    private boolean processUnqualifiedResolve(@NotNull ResFile file,
                                              @NotNull ResScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              boolean localResolve) {
        return true;
    }

    private static boolean processFileEntities(@NotNull ResFile file,
                                               @NotNull ResScopeProcessor processor,
                                               @NotNull ResolveState state,
                                               boolean localProcessing) {
        //if (!processNamedElements(processor, state, file.getConstants(), localProcessing)) return false;
        //if (!processNamedElements(processor, state, file.getVars(), localProcessing)) return false;
        //if (!processNamedElements(processor, state, file.getFunctions(), localProcessing)) return false;
        if (!processNamedElements(processor, state, file.getTypes(), localProcessing)) return false;
        return true;
    }

    static boolean processNamedElements(@NotNull PsiScopeProcessor processor,
                                        @NotNull ResolveState state,
                                        @NotNull Collection<? extends ResNamedElement> elements,
                                        boolean localResolve) {
        return processNamedElements(processor, state, elements, localResolve, false);
    }

    static boolean processNamedElements(@NotNull PsiScopeProcessor processor,
                                        @NotNull ResolveState state,
                                        @NotNull Collection<? extends ResNamedElement> elements,
                                        boolean localResolve,
                                        boolean checkContainingFile) {
        PsiFile contextFile = checkContainingFile ? getContextFile(state) : null;
        for (ResNamedElement definition : elements) {
            //if (!definition.isValid() || checkContainingFile && !allowed(definition.getContainingFile(), contextFile)) continue;
            if ((localResolve || definition.isPublic()) && !processor.execute(definition, state)) return false;
        }
        return true;
    }

    @Nullable private static PsiFile getContextFile(
            @NotNull ResolveState state) {
        SmartPsiElementPointer<ResReferenceExpressionBase> context =
                state.get(CONTEXT);
        return context != null ? context.getContainingFile() : null;
    }
}