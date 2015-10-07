package edu.clemson.resolve.plugin.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import edu.clemson.resolve.plugin.psi.ResOperationProcedureDeclaration;

public class ResOperationProcedureDeclarationStub
        <T extends ResOperationProcedureDeclaration> extends ResNamedStub<T> {
    protected ResOperationProcedureDeclarationStub(StubElement parent,
                                                   IStubElementType elementType,
                                                   StringRef name,
                                                   boolean isPublic) {
        super(parent, elementType, name, isPublic);
    }
    protected ResOperationProcedureDeclarationStub(StubElement parent,
                                                IStubElementType elementType,
                                                String name, boolean isPublic) {
        super(parent, elementType, name, isPublic);
    }
}