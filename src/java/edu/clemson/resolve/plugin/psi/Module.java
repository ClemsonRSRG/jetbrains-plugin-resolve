package edu.clemson.resolve.plugin.psi;

import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Module
        extends
        ResolveCompositeElement, PsiNameIdentifierOwner, NavigationItem {

    boolean isPublic();

    @Nullable PsiElement getIdentifier();

    @NotNull
    FileNode getContainingFile();
}
