package edu.clemson.resolve.plugin.psi;

import com.intellij.psi.StubBasedPsiElement;
import edu.clemson.resolve.plugin.stubs.ResTypeStub;
import org.jetbrains.annotations.Nullable;

public interface ResType extends ResCompositeElement, StubBasedPsiElement<ResTypeStub> {

    @Nullable ResTypeReferenceExpression getTypeReferenceExpression();
}
