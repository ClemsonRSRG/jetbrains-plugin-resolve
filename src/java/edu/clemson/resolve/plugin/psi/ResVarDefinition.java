package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import edu.clemson.resolve.plugin.stubs.ResVarDefinitionStub;
import org.jetbrains.annotations.NotNull;

public interface ResVarDefinition
        extends
            ResNamedElement, StubBasedPsiElement<ResVarDefinitionStub> {

    @NotNull PsiElement getIdentifier();
}
