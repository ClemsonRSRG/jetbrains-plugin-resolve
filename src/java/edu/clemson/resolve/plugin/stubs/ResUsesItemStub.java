package edu.clemson.resolve.plugin.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import edu.clemson.resolve.plugin.psi.ResUsesItem;

public class ResUsesItemStub extends ResNamedStub<ResUsesItem> {

    public ResUsesItemStub(StubElement parent, IStubElementType elementType,
                           StringRef name, boolean isPublic) {
        super(parent, elementType, name, isPublic);
    }
}
