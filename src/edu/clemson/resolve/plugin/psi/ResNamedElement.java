package edu.clemson.resolve.plugin.psi;

import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResNamedElement
        extends
            ResTypeOwner, ResCompositeElement, PsiNameIdentifierOwner, NavigationItem {

    public boolean isPublic();

    @Nullable public PsiElement getIdentifier();

    @NotNull public PsiFile getContainingFile();

    @Nullable ResType findSiblingType();

}