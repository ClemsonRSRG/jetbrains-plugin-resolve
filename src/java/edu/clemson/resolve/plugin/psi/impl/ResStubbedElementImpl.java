package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.extapi.psi.StubBasedPsiElementBase;
import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubBase;
import edu.clemson.resolve.plugin.psi.ResCompositeElement;
import edu.clemson.resolve.plugin.psi.ResFile;
import edu.clemson.resolve.plugin.stubs.TextHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResStubbedElementImpl<T extends StubBase<?>>
        extends
            StubBasedPsiElementBase<T> implements ResCompositeElement {

    public ResStubbedElementImpl(@NotNull T stub,
                                 @NotNull IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public ResStubbedElementImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override public String toString() {
        return getNode().getElementType().toString();
    }

    @Nullable @Override public String getText() {
        T stub = getStub();
        if (stub instanceof TextHolder) {
            String text = ((TextHolder)stub).getText();
            if (text != null) return text;
        }
        return super.getText();
    }

    @NotNull @Override public ResFile getContainingFile() {
        return (ResFile)super.getContainingFile();
    }

    @Override public boolean shouldGoDeeper() {
        return true;
    }
}
