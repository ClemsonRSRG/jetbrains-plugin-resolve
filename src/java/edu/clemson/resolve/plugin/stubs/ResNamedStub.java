package edu.clemson.resolve.plugin.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import edu.clemson.resolve.plugin.psi.ResNamedElement;

public class ResNamedStub<T extends ResNamedElement> extends NamedStubBase<T> {
    private final boolean myIsPublic;

    public ResNamedStub(StubElement parent, IStubElementType elementType,
                        StringRef name, boolean isPublic) {
        super(parent, elementType, name);
        myIsPublic = isPublic;
    }

    public ResNamedStub(StubElement parent, IStubElementType elementType,
                        String name, boolean isPublic) {
        super(parent, elementType, name);
        myIsPublic = isPublic;
    }

    public boolean isPublic() {
        return myIsPublic;
    }
}
