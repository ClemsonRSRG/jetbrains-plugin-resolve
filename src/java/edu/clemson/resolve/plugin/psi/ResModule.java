package edu.clemson.resolve.plugin.psi;

import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResModule
        extends
            ResCompositeElement, PsiNameIdentifierOwner, NavigationItem {

    boolean isPublic();

    @Nullable ResBlock getBlock();

    @Nullable PsiElement getIdentifier();
}
