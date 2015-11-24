package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.psi.impl.ResReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResRefExp extends ResExp {

    @NotNull public PsiElement getIdentifier();

    @NotNull public ResReference getReference();

    @Nullable ResRefExp getQualifier();
}
