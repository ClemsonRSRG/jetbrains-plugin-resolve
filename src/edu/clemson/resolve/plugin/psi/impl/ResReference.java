package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ArrayUtil;
import com.intellij.util.ObjectUtils;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.containers.OrderedSet;
import edu.clemson.resolve.plugin.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ResReference
        extends
            PsiPolyVariantReferenceBase<ResReferenceExpBase> {
    public static final Key<SmartPsiElementPointer<ResReferenceExpBase>> CONTEXT = Key.create("CONTEXT");
    public static final Key<String > ACTUAL_NAME = Key.create("ACTUAL_NAME");

    private static final ResolveCache
            .PolyVariantResolver<PsiPolyVariantReferenceBase> MY_RESOLVER =
            new ResolveCache.PolyVariantResolver<PsiPolyVariantReferenceBase>() {
                @NotNull
                @Override
                public ResolveResult[] resolve(
                        @NotNull PsiPolyVariantReferenceBase psiPolyVariantReferenceBase,
                        boolean incompleteCode) {
                    return ((ResReference)psiPolyVariantReferenceBase)
                            .resolveInner();
                }
            };

    @NotNull private PsiElement getIdentifier() {
        return myElement.getIdentifier();
    }

    public ResReference(@NotNull ResReferenceExpBase o) {
        super(o, TextRange.from(o.getIdentifier().getStartOffsetInParent(),
                o.getIdentifier().getTextLength()));
    }

    @NotNull private ResolveResult[] resolveInner() {
        if (!myElement.isValid()) return ResolveResult.EMPTY_ARRAY;
        Collection<ResolveResult> result = new OrderedSet<ResolveResult>();
        processResolveVariants(createResolveProcessor(result, myElement));
        return result.toArray(new ResolveResult[result.size()]);
    }

    @NotNull static ResScopeProcessor createResolveProcessor(
            @NotNull final Collection<ResolveResult> result,
            @NotNull final ResReferenceExpBase o) {
        return new ResScopeProcessor() {
            @Override
            public boolean execute(@NotNull PsiElement element,
                                   @NotNull ResolveState state) {
                if (element.equals(o)) {
                    return !result.add(
                            new PsiElementResolveResult(element));
                }
                String name = ObjectUtils.chooseNotNull(state.get(ACTUAL_NAME),
                        element instanceof PsiNamedElement ?
                                ((PsiNamedElement)element).getName() : null);
                if (name != null && o.getIdentifier().textMatches(name)) {
                    result.add(new PsiElementResolveResult(element));
                    return false;
                }
                return true;
            }
        };
    }

    @NotNull @Override public ResolveResult[] multiResolve(
            boolean incompleteCode) {
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
        ResReferenceExpBase qualifier = myElement.getQualifier();
        return qualifier != null ?
                processQualifierExpression(((ResFile)file), qualifier, processor, state) :
                processUnqualifiedResolve(((ResFile) file), processor, state, true);
    }


    private boolean processQualifierExpression(@NotNull ResFile file,
                                               @NotNull ResReferenceExpBase qualifier,
                                               @NotNull ResScopeProcessor processor,
                                               @NotNull ResolveState state) {
        PsiReference reference = qualifier.getReference();
        PsiElement target = reference != null ? reference.resolve() : null;
        if (target == null || target == qualifier) return false;
        if (target instanceof ResFacilityDecl) {
            ResFile spec = ((ResFacilityDecl)target).getSpecification();
            if (spec == null) return false;
            processModuleLevelEntities(spec, processor, state, false);
        }
        //Todo look into the logic down here
        if (target instanceof ResTypeOwner) {
            ResType type = ((ResTypeOwner)target).getResType(createContext());
            if (type != null && !processResType(type, processor, state)) return false;
        }
        return true;
    }

    private boolean processUnqualifiedResolve(@NotNull ResFile file,
                                              @NotNull ResScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              boolean localResolve) {
        PsiElement parent = myElement.getParent();
        if (parent instanceof ResSelectorExp) {
            boolean result = processSelector((ResSelectorExp)parent, processor, state, myElement);
            if (processor.isCompletion()) return result;
            if (!result || ResPsiImplUtil.prevDot(myElement)) return false;
        }
        PsiElement grandPa = parent.getParent();
        if (grandPa instanceof ResSelectorExp && !processSelector((ResSelectorExp)grandPa, processor, state, parent)) return false;
        if (ResPsiImplUtil.prevDot(parent)) return false;

        if (!processBlock(processor, state, true)) return false;
        if (!processParameterLikeThings(processor, state, true)) return false;
        if (!processModuleLevelEntities(file, processor, state, true)) return false;

        return true;
    }

    private boolean processSelector(@NotNull ResSelectorExp parent,
                                    @NotNull ResScopeProcessor processor,
                                    @NotNull ResolveState state,
                                    @Nullable PsiElement another) {
        List<ResExp> list = parent.getExpList();
        if (list.size() > 1 && list.get(1).isEquivalentTo(another)) {
            ResType type = list.get(0).getResType(createContext());
            if (type != null && !processResType(type, processor, state)) return false;
        }
        return true;
    }

    private boolean processResType(@NotNull ResType type,
                                   @NotNull ResScopeProcessor processor,
                                   @NotNull ResolveState state) {
        if (!processExistingType(type, processor, state)) return false;
        return processTypeRef(type, processor, state);
    }

    private boolean processTypeRef(@Nullable ResType type,
                                   @NotNull ResScopeProcessor processor,
                                   @NotNull ResolveState state) {
        return processInTypeRef(getTypeReference(type), type, processor, state);
    }

    private boolean processInTypeRef(@Nullable ResTypeReferenceExp refExp,
                                     @Nullable ResType recursiveStopper,
                                     @NotNull ResScopeProcessor processor,
                                     @NotNull ResolveState state) {
        PsiReference reference = refExp != null ? refExp.getReference() : null;
        PsiElement resolve = reference != null ? reference.resolve() : null;
        if (resolve instanceof ResTypeOwner) {
            ResType type = ((ResTypeOwner)resolve).getResType(state);
            if (type != null && !processResType(type, processor, state)) return false;
        }
        return true;
    }

    private boolean processExistingType(@NotNull ResType type,
                                        @NotNull ResScopeProcessor processor,
                                        @NotNull ResolveState state) {
        PsiFile file = type.getContainingFile();
        if (!(file instanceof ResFile)) return true;
        PsiFile myFile = ObjectUtils.notNull(getContextFile(state), myElement.getContainingFile());
        if (!(myFile instanceof ResFile)) return true;
        boolean localResolve = true;
        if (type instanceof ResTypeReprDecl) type = ((ResTypeReprDecl)type).getType();
        if (type instanceof ResRecordType) {
            ResScopeProcessorBase delegate = createDelegate(processor);
            type.processDeclarations(delegate, ResolveState.initial(), null, myElement);
            List<ResTypeReferenceExp> structRefs = ContainerUtil.newArrayList();
            for (ResRecordVarDeclGroup d : ((ResRecordType)type).getRecordVarDeclGroupList()) {
                if (!processNamedElements(processor, state, d.getFieldVarDeclGroup().getFieldDefList(), localResolve)) return false;
            }
            if (!processCollectedRefs(type, structRefs, processor, state)) return false;
        }
        return true;
    }

    private boolean processCollectedRefs(@NotNull ResType type,
                                         @NotNull List<ResTypeReferenceExp> refs,
                                         @NotNull ResScopeProcessor processor,
                                         @NotNull ResolveState state) {
        for (ResTypeReferenceExp ref : refs) {
            if (!processInTypeRef(ref, type, processor, state)) return false;
        }
        return true;
    }

    @Nullable public static ResTypeReferenceExp getTypeReference(
            @Nullable ResType o) {
        if (o == null) return null;
        return o.getTypeReferenceExp();
    }

    @NotNull public ResolveState createContext() {
        return ResolveState.initial().put(CONTEXT,
                SmartPointerManager.getInstance(myElement.getProject())
                        .createSmartPsiElementPointer(myElement));
    }

    private boolean processBlock(@NotNull ResScopeProcessor processor,
                                 @NotNull ResolveState state,
                                 boolean localResolve) {
        ResScopeProcessorBase delegate = createDelegate(processor);
        ResolveUtil.treeWalkUp(myElement, delegate);
        return processNamedElements(processor, state, delegate.getVariants(),
                localResolve);
    }

    static boolean processUsesRequests(@NotNull ResFile file,
                                       @NotNull ResScopeProcessor processor,
                                       @NotNull ResolveState state,
                                       @NotNull ResCompositeElement element,
                                       boolean searchImplicitUses) {
        for (ResUsesItem u : file.getUsesItems()) {
            //this file resolve is failing for whatever reason when we're trying to add completions... is this a concurrency thing maybe?
            //works the rest of the time...
            PsiElement resolvedModule = u.getModuleSpec().resolve();
            if (resolvedModule == null || !(resolvedModule instanceof ResFile)) continue;
            if (!processModuleLevelEntities((ResFile) resolvedModule, processor, state, false)) return false;
        }
        ResModuleDecl module = file.getEnclosedModule();

        //The "searchImplicitUses" is here since, in the context of a a concept (or enhancement) realiz
        //we don't want to find the type model, we should be dealing in repr types. So in the context
        //of the typeReference class our searchImplicitUses will be if module != ResConceptImplModule || module != ResConceptExtImplModule
        if (module != null && searchImplicitUses) {
            //Now process module decl implicit imports
            for (ResModuleSpec moduleSpec : module.getModuleSpecList()) {
                PsiElement resolvedModule = moduleSpec.resolve();
                if (resolvedModule == null || !(resolvedModule instanceof ResFile)) continue;
                if (!processModuleLevelEntities((ResFile) resolvedModule, processor, state, false)) return false;
            }
        }
        return true;
    }

    protected static boolean processModuleLevelEntities(@NotNull ResFile file,
                                                        @NotNull ResScopeProcessor processor,
                                                        @NotNull ResolveState state,
                                                        boolean localProcessing) {
        if (!processNamedElements(processor, state, file.getOperationLikeThings(), localProcessing)) return false;
        if (!processNamedElements(processor, state, file.getFacilities(), localProcessing)) return false;
        if (!processNamedElements(processor, state, file.getTypes(), localProcessing)) return false;
        if (!processNamedElements(processor, state, file.getMathDefinitionSignatures(), localProcessing)) return false;
        return true;
    }

    private boolean processParameterLikeThings(@NotNull ResScopeProcessor processor,
                                               @NotNull ResolveState state,
                                               boolean localResolve) {
        ResScopeProcessorBase delegate = createDelegate(processor);
        processParameterLikeThings(myElement, delegate);
        return processNamedElements(processor, state, delegate.getVariants(),
                localResolve);
    }

    protected static void processParameterLikeThings(
            @NotNull ResCompositeElement e,
            @NotNull ResScopeProcessorBase processor) {
        ResMathDefinitionDecl def =
                PsiTreeUtil.getParentOfType(e, ResMathDefinitionDecl.class);
        ResOperationLikeNode operation =
                PsiTreeUtil.getParentOfType(e, ResOperationLikeNode.class);
        ResModuleDecl module =
                PsiTreeUtil.getParentOfType(e, ResModuleDecl.class);
        if (def != null) processDefinitionParams(processor, def);
        if (operation != null) processProgParamDecls(processor, operation.getParamDeclList());
        if (module != null) processModuleParams(processor, module);
        //TODO: process moduleparams now
    }

    /** processing parameters of the definition we happen to be within */
    private static boolean processDefinitionParams(
            @NotNull ResScopeProcessorBase processor,
            @NotNull ResMathDefinitionDecl o) {
        List<ResMathDefinitionSignature> sigs = o.getSignatures();
        if (sigs.size() == 1) {
            ResMathDefinitionSignature sig = o.getSignatures().get(0);
            if (!processDefinitionParams(processor, sig.getParameters())) return false;
        } //size > 1 ? then we're categorical; size == 0, we're null
        return true;
    }

    private static boolean processDefinitionParams(
            @NotNull ResScopeProcessorBase processor,
            @NotNull List<ResMathVarDeclGroup> parameters) {
        for (ResMathVarDeclGroup declaration : parameters) {
            if (!processNamedElements(processor, ResolveState.initial(), declaration.getMathVarDefList(), true)) return false;
            //if (!processImplicitTypeParameters(processor, ResolveState.initial(), declaration.getMathExp(), true)) return false;
        }
        return true;
    }

    private static boolean processProgParamDecls(
            @NotNull ResScopeProcessorBase processor,
            @NotNull List<ResParamDecl> parameters) {
        for (ResParamDecl declaration : parameters) {
            if (!processNamedElements(processor, ResolveState.initial(),
                    declaration.getParamDefList(), true)) return false;
        }
        return true;
    }

    private static boolean processModuleParams(
            @NotNull ResScopeProcessorBase processor,
            @NotNull ResModuleDecl o) {
        ResModuleParameters paramNode = o.getModuleParameters();
        List<ResTypeParamDecl> typeParamDecls =
                new ArrayList<ResTypeParamDecl>();
        List<ResParamDecl> constantParamDeclGrps =
                new ArrayList<ResParamDecl>();
        List<ResMathDefinitionDecl> definitionParams =
                new ArrayList<ResMathDefinitionDecl>();
        if (paramNode instanceof ResSpecModuleParameters) {
            typeParamDecls.addAll(((ResSpecModuleParameters) paramNode).getTypeParamDeclList());
            constantParamDeclGrps.addAll(((ResSpecModuleParameters) paramNode).getParamDeclList());
        }
        //TODO: else if (paramNode instanceof ResImplModuleParameters) ..

        processProgParamDecls(processor, constantParamDeclGrps);
        processNamedElements(processor, ResolveState.initial(), typeParamDecls, true);
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

        for (ResNamedElement definition : elements) {
            //if (!definition.isValid() || checkContainingFile && !allowed(definition.getContainingFile(), contextFile)) continue;
            if ((localResolve || definition.isPublic()) &&
                    !processor.execute(definition, state)) return false;
        }
        return true;
    }

    @NotNull private ResVarReference.ResVarProcessor createDelegate(
            @NotNull ResScopeProcessor processor) {
        return new ResVarReference.ResVarProcessor(getIdentifier(), myElement,
                processor.isCompletion(), true) {
            @Override protected boolean crossOff(@NotNull PsiElement e) {
                if (e instanceof ResFieldDef) return true;
                return super.crossOff(e); //&& !(e instanceof ResTypeSpec);
            }
        };
    }

    private String getName() {
        return myElement.getIdentifier().getText();
    }

    @Nullable private static PsiFile getContextFile(
            @NotNull ResolveState state) {
        SmartPsiElementPointer<ResReferenceExpBase> context =
                state.get(CONTEXT);
        return context != null ? context.getContainingFile() : null;
    }
}