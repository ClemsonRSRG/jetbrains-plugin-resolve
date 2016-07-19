package edu.clemson.resolve.jetbrains.psi.impl;

import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.light.LightElement;
import com.intellij.psi.stubs.IStubElementType;
import edu.clemson.resolve.jetbrains.psi.ResCompositeElement;
import edu.clemson.resolve.jetbrains.psi.ResOperationLikeNode;
import edu.clemson.resolve.jetbrains.psi.ResType;
import edu.clemson.resolve.jetbrains.psi.ResTypeReferenceExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Adapted from go plugin, this is primarily used on functions so we can obtain type and parameter mode info about the
 * params we're currently filling in (for a call, facility, etc).
 * @param <E>
 */
public abstract class ResLightType<E extends ResCompositeElement> extends LightElement implements ResType {

    @NotNull
    protected final E myElement;

    protected ResLightType(@NotNull E e) {
        super(e.getManager(), e.getLanguage());
        myElement = e;
        setNavigationElement(e);
    }

    @Nullable
    @Override
    public ResTypeReferenceExp getTypeReferenceExp() {
        return null;
    }

    @Override
    public boolean shouldGoDeeper() {
        return false;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + myElement + "}";
    }

    static class LightFunctionType extends ResLightType<ResOperationLikeNode> {
        public LightFunctionType(@NotNull ResOperationLikeNode o) {
            super(o);
        }

        @Nullable
        public ResOperationLikeNode getOperationLikeNode() {
            return myElement;
        }

        @Override
        public String getText() {
            return myElement.getText();
        }
    }
}
