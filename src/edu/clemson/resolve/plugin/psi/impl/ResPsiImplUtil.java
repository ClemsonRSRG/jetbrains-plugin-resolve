package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.RecursionManager;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceOwner;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.PsiFileReference;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiModificationTracker;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.plugin.ResTypes;
import edu.clemson.resolve.plugin.psi.*;
import edu.clemson.resolve.plugin.psi.impl.imports.ResModuleReferenceSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ResPsiImplUtil {

    @NotNull public static TextRange getModuleSpecTextRange(
            @NotNull ResModuleSpec moduleSpec) {
        String text = moduleSpec.getText();
        return !text.isEmpty() ? TextRange.create(0, text.length() - 1) :
                TextRange.EMPTY_RANGE;
    }

    @Nullable public static ResFile getSpecification(ResFacilityDecl o) {
        if (o.getModuleSpecList().isEmpty()) return null;
        ResModuleSpec specification = o.getModuleSpecList().get(0);
        PsiFile specFile = specification.resolve();
        if (!(specFile instanceof ResFile)) return null;
        return (ResFile) specFile;
    }

    @NotNull public static PsiElement getIdentifier(ResMathReferenceExp o) {
        return PsiTreeUtil.getChildOfType(o, ResMathNameIdentifier.class);
    }

    @Nullable public static ResMathReferenceExp getQualifier(
            @NotNull ResMathReferenceExp o) {
        return PsiTreeUtil.getChildOfType(o, ResMathReferenceExp.class);
    }

    @NotNull public static ResMathVarLikeReference getReference(
            @NotNull ResMathReferenceExp o) {
        return new ResMathVarLikeReference(o);
    }

    @NotNull public static PsiReference[] getReferences(
            @NotNull ResModuleSpec o) {
        if (o.getTextLength() == 0) return PsiReference.EMPTY_ARRAY;
        return new ResModuleReferenceSet(o).getAllReferences();
    }

    @NotNull public static String getText(@Nullable ResType o) {
        if (o == null) return "";
        String text = o.getText();
        if (text == null) return "";
        return text.replaceAll("\\s+", " ");
    }

    /**
     * ok, in the go plugin don't be fooled by the seeming lack of connection between
     * UsesReferenceHelper and the FileContextProvider -- these are responsible
     * for setting getDefaultContext to "resolve/src/" etc...
     */
    @Nullable public static PsiFile resolve(
            @NotNull ResModuleSpec moduleSpec) {
        PsiReference[] references = moduleSpec.getReferences();
        for (PsiReference reference : references) {
            if (reference instanceof FileReferenceOwner) {
                PsiFileReference lastFileReference =
                        ((FileReferenceOwner) reference).getLastFileReference();
                PsiElement result = lastFileReference != null ?
                        lastFileReference.resolve() : null;
                return result instanceof ResFile ? ((ResFile) result) : null;
            }
        }
        return null;
    }

    public static boolean processDeclarations(@NotNull ResCompositeElement o,
                                              @NotNull PsiScopeProcessor processor,
                                              @NotNull ResolveState state,
                                              PsiElement lastParent,
                                              @NotNull PsiElement place) {
        boolean isAncestor = PsiTreeUtil.isAncestor(o, place, false);
        //if (o instanceof ResVarSpec) return isAncestor || ResCompositeElementImpl.processDeclarationsDefault(o, processor, state, lastParent, place);
        if (isAncestor)
            return ResCompositeElementImpl.processDeclarationsDefault(o, processor, state, lastParent, place);

        if (o instanceof ResBlock) { //||
            // o instanceof ResIfStatement ||
            // o instanceof ResWhileStatement  {
            return processor.execute(o, state);
        }
        return ResCompositeElementImpl.processDeclarationsDefault(
                o, processor, state, lastParent, place);
    }

    @Nullable public static ResTypeReferenceExp getQualifier(
            @NotNull ResTypeReferenceExp o) {
        return PsiTreeUtil.getChildOfType(o, ResTypeReferenceExp.class);
    }

    @NotNull public static PsiReference getReference(
            @NotNull ResTypeReferenceExp o) {
        return new ResTypeReference(o);
    }

    public static boolean prevDot(@Nullable PsiElement parent) {
        PsiElement prev = parent == null ? null :
                PsiTreeUtil.prevVisibleLeaf(parent);
        return prev instanceof LeafElement &&
                ((LeafElement) prev).getElementType() == ResTypes.DOT;
    }

    /** An expression describing the type of a mathematical expression
     *  {@code o} written in terms of another mathematical expression.
     */
    @Nullable public static ResMathExp getResMathMetaTypeExp(
            @NotNull final ResMathExp o, @Nullable final ResolveState context) {
        return RecursionManager.doPreventingRecursion(o, true,
                new Computable<ResMathExp>() {
                    @Override public ResMathExp compute() {
                        if (context != null) return getGoTypeMetaExpInner(o, context);
                        return CachedValuesManager.getCachedValue(o,
                                new CachedValueProvider<ResMathExp>() {
                            @Nullable @Override public Result<ResMathExp> compute() {
                                return Result.create(getGoTypeMetaExpInner(o, null),
                                        PsiModificationTracker.MODIFICATION_COUNT);
                            }
                        });
                    }
                });
    }

    @Nullable public static ResMathExp getGoTypeMetaExpInner(
            @NotNull final ResMathExp o, @Nullable ResolveState context) {
        if (o instanceof ResMathReferenceExp) {
            PsiReference reference = o.getReference();
            PsiElement resolve = reference != null ? reference.resolve() : null;
            int i;
            i=0;
            //if (resolve instanceof ReTypeOwner) return typeOrParameterType((GoTypeOwner)resolve, context);
        }
        else if (o instanceof ResMathSelectorExp) {
            ResMathExp item = ContainerUtil.getLastItem(
                    ((ResMathSelectorExp) o).getMathExpList());
            return item != null ? item.getResMathMetaTypeExp(context) : null;
        }
        return null;
    }
}