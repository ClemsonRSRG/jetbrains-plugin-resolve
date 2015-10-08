package edu.clemson.resolve.plugin.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import edu.clemson.resolve.plugin.psi.ResTypeReprDecl;

public class ResTypeReprDeclStub
        extends
            ResNamedStub<ResTypeReprDecl> {

    public ResTypeReprDeclStub(StubElement parent,
                               IStubElementType elementType,
                               StringRef name, boolean isPublic) {
        super(parent, elementType, name, isPublic);
    }
}
