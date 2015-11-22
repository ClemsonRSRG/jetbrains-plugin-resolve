package edu.clemson.resolve.plugin.psi;

import org.jetbrains.annotations.Nullable;

public interface ResMathDefinitionDecl extends ResMathNamedElement {

    @Nullable ResMathSignature getMathSignature();
}
