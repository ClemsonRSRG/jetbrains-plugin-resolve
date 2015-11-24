package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.formatter.FormatterUtil;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ArrayUtil;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.containers.OrderedSet;
import edu.clemson.resolve.plugin.psi.ResFile;
import edu.clemson.resolve.plugin.psi.ResMathSymbolRefExp;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class ResMathVarLikeReference
        extends
            PsiPolyVariantReferenceBase<ResMathSymbolRefExp> {

    public ResMathVarLikeReference(@NotNull ResMathSymbolRefExp o) {
        super(o, TextRange.from(o.getIdentifier().getStartOffsetInParent(),
                o.getIdentifier().getTextLength()));
    }

    private static final ResolveCache
            .PolyVariantResolver<PsiPolyVariantReferenceBase> MY_RESOLVER =
            new ResolveCache.PolyVariantResolver<PsiPolyVariantReferenceBase>() {
                @NotNull @Override public ResolveResult[] resolve(
                        @NotNull PsiPolyVariantReferenceBase psiPolyVariantReferenceBase,
                        boolean incompleteCode) {
                    return ((ResMathVarLikeReference)psiPolyVariantReferenceBase)
                            .resolveInner();
                }
            };

    @NotNull private ResolveResult[] resolveInner() {
        Collection<ResolveResult> result = new OrderedSet<ResolveResult>();
        processResolveVariants(ResReference.createResolveProcessor(result, myElement));
        return result.toArray(new ResolveResult[result.size()]);
    }

    @NotNull private PsiElement getIdentifier() {
        return myElement.getIdentifier();
    }

    @NotNull @Override public ResolveResult[] multiResolve(boolean b) {
        if (!myElement.isValid()) return ResolveResult.EMPTY_ARRAY;
        return ResolveCache.getInstance(myElement.getProject())
                .resolveWithCaching(this, MY_RESOLVER, false, false);
    }

    @NotNull @Override public Object[] getVariants() {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    public boolean processResolveVariants(@NotNull ResScopeProcessor processor) {
        PsiFile file = myElement.getContainingFile();
        if (!(file instanceof ResFile)) return false;
        ResolveState state = ResolveState.initial();
        ResMathSymbolRefExp qualifier = myElement.getQualifier();
        if (qualifier != null) {
            return false;
            //return processQualifierExpression(((ResFile)file), qualifier,
            //        processor, state);
        }
        return processUnqualifiedResolve(((ResFile)file), processor, state, true);
    }

    private boolean processUnqualifiedResolve(@NotNull ResFile file,
                                              @NotNull ResScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              boolean localResolve) {
        ResScopeProcessorBase delegate = createDelegate(processor);
        ResolveUtil.treeWalkUp(myElement, delegate);
        /*Collection<? extends GoNamedElement> result = delegate.getVariants();
        if (!processNamedElements(processor, state, result, localResolve)) return false;
        if (!processFileEntities(file, processor, state, localResolve)) return false;
        PsiDirectory dir = file.getOriginalFile().getParent();
        if (!GoReference.processDirectory(dir, file, file.getPackageName(), processor, state, true)) return false;
        if (GoReference.processImports(file, processor, state, myElement)) return false;
        if (processBuiltin(processor, state, myElement)) return false;
        if (getIdentifier().textMatches(GoConstants.NIL) && PsiTreeUtil.getParentOfType(myElement, GoTypeCaseClause.class) != null) {
            GoType type = PsiTreeUtil.getParentOfType(myElement, GoType.class);
            if (FormatterUtil.getPrevious(type != null ? type.getNode() : null, GoTypes.CASE) == null) return true;
            GoFile builtinFile = GoSdkUtil.findBuiltinFile(myElement);
            if (builtinFile == null) return false;
            GoVarDefinition nil = ContainerUtil.find(builtinFile.getVars(), new Condition<GoVarDefinition>() {
                @Override
                public boolean value(GoVarDefinition v) {
                    return GoConstants.NIL.equals(v.getName());
                }
            });
            if (nil != null && !processor.execute(nil, state)) return false;
        }*/
        return true;
    }

    @NotNull private ResMathVarLikeProcessor createDelegate(
            @NotNull ResScopeProcessor processor) {
        return new ResMathVarLikeProcessor(myElement, processor.isCompletion());
    }

    protected static class ResMathVarLikeProcessor
            extends
                ResScopeProcessorBase {
        public ResMathVarLikeProcessor(@NotNull ResMathSymbolRefExp origin,
                                       boolean completion) {
            super(origin.getIdentifier(), origin, completion);
        }
        @Override protected boolean condition(@NotNull PsiElement element) {
            return true;
        }
    }
}
