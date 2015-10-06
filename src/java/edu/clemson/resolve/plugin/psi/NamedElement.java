package edu.clemson.resolve.plugin.psi;

import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface NamedElement
        extends
        TypeOwner,
        ResolveCompositeElement,
            PsiNameIdentifierOwner,
            NavigationItem {

    boolean isPublic();

    @Nullable PsiElement getIdentifier();

    @Nullable String getQualifiedName();

    @NotNull
    FileNode getContainingFile();

    @Nullable
    Type findSiblingType();
}
