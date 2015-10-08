package edu.clemson.resolve.plugin.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import edu.clemson.resolve.plugin.psi.ResVarDefinition;

public class ResVarDefinitionStub extends ResNamedStub<ResVarDefinition> {

    public ResVarDefinitionStub(StubElement parent,
                                IStubElementType elementType,
                                StringRef name, boolean isPublic) {
        super(parent, elementType, name, isPublic);
    }
}
