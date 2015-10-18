package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.util.ArrayUtil;
import com.intellij.util.containers.OrderedSet;
import edu.clemson.resolve.plugin.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class ResTypeReference
        extends
            PsiPolyVariantReferenceBase<ResTypeReferenceExpression> {

    public ResTypeReference(@NotNull ResTypeReferenceExpression o) {
        super(o, TextRange.from(o.getIdentifier().getStartOffsetInParent(),
                o.getIdentifier().getTextLength()));
    }

    private static final ResolveCache.PolyVariantResolver<PsiPolyVariantReferenceBase> MY_RESOLVER =
            new ResolveCache.PolyVariantResolver<PsiPolyVariantReferenceBase>() {
                @NotNull
                @Override
                public ResolveResult[] resolve(
                        @NotNull PsiPolyVariantReferenceBase psiPolyVariantReferenceBase,
                        boolean incompleteCode) {
                    return ((ResTypeReference)
                            psiPolyVariantReferenceBase).resolveInner();
                }
            };

    @NotNull @Override public Object[] getVariants() {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    @NotNull @Override public ResolveResult[] multiResolve(boolean b) {
        return myElement.isValid()
                ? ResolveCache.getInstance(myElement.getProject())
                .resolveWithCaching(this, MY_RESOLVER, false, false)
                : ResolveResult.EMPTY_ARRAY;
    }

    @NotNull private ResolveResult[] resolveInner() {
        Collection<ResolveResult> result = new OrderedSet<ResolveResult>();
        processResolveVariants(ResReference.createResolveProcessor(result, myElement));
        /*if (result.isEmpty() && myElement.getParent() instanceof ResReceiverType) {
            PsiElement resolve = new ResReference(myElement).resolve();
            if (resolve != null) {
                return PsiElementResolveResult.createResults(resolve);
            }
        }*/
        return result.toArray(new ResolveResult[result.size()]);
    }

    public boolean processResolveVariants(@NotNull ResScopeProcessor processor) {
        PsiFile file = myElement.getContainingFile();
        if (!(file instanceof ResFile)) return false;
        ResolveState state = ResolveState.initial();
        ResTypeReferenceExpression qualifier = myElement.getQualifier();
        if (qualifier != null) {
            return processQualifierExpression(
                    ((ResFile) file), qualifier, processor, state);
        }
        return processUnqualifiedResolve(
                ((ResFile) file), processor, state, true);
    }

    private static boolean processQualifierExpression(@NotNull ResFile file,
                                                      @NotNull ResTypeReferenceExpression qualifier,
                                                      @NotNull ResScopeProcessor processor,
                                                      @NotNull ResolveState state) {
        PsiReference targetRef = qualifier.getReference();
        PsiElement target = targetRef.resolve();
        if (target == null) return false;
        if (target instanceof ResFacilityDecl) {
            List<ResUsesSpec> facilitySpecs =
                    ((ResFacilityDecl)target).getUsesSpecList();
            if (facilitySpecs.size() < 1) return false;

            //resolve through the specification of the facility
            PsiFile specFile = facilitySpecs.get(0).resolve();

            if (specFile != null) {
                ResReference.processFileEntities(
                        (ResFile) specFile, processor, state, false);
            }
            //target =
        }
        //        ((ResFacilityDecl)target).getSpec()     //need to get PsiFile corresponding to spec...
        /*if (target instanceof GoImportSpec) target = ((GoImportSpec)target).getImportString().resolve();
        if (target instanceof PsiDirectory) {
            GoReference.processDirectory((PsiDirectory)target, file, null, processor, state, false);
        }*/
        return false;
    }

    private boolean processUnqualifiedResolve(@NotNull ResFile file,
                                              @NotNull ResScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              boolean localResolve) {
        ResScopeProcessorBase delegate = createDelegate(processor);
        ResolveUtil.treeWalkUp(myElement, delegate);
        Collection<? extends ResNamedElement> result = delegate.getVariants();

        //these two will search locally...
        if (!processNamedElements(processor, state, result, localResolve)) return false;
        if (!processFileEntities(file, processor, state, localResolve)) return false;

        if (ResReference.processUsesRequests(file, processor, state, myElement)) return false;

        return true;
    }

    private boolean processFileEntities(@NotNull ResFile file,
                                        @NotNull ResScopeProcessor processor,
                                        @NotNull ResolveState state,
                                        boolean localProcessing) {
        if (!processNamedElements(processor, state, file.getTypes(),
                localProcessing)) return false;
        return true;
    }

    private boolean processNamedElements(@NotNull PsiScopeProcessor processor,
                                         @NotNull ResolveState state,
                                         @NotNull Collection<? extends ResNamedElement> elements,
                                         boolean localResolve) {
        for (ResNamedElement definition : elements) {
            //if (definition instanceof ResTypeLikeNodeDecl && !allowed((GoTypeSpec)definition)) continue;
            if ((definition.isPublic() || localResolve) &&
                    !processor.execute(definition, state)) return false;
        }
        return true;
    }


    @NotNull private ResTypeProcessor createDelegate(
            @NotNull ResScopeProcessor processor) {
        return new ResTypeProcessor(myElement, processor.isCompletion());
    }
}
