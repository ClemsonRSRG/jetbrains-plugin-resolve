package edu.clemson.resolve.plugin.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import edu.clemson.resolve.plugin.psi.ResType;
import org.jetbrains.annotations.Nullable;

public class ResTypeStub extends StubWithText<ResType> {

    protected ResTypeStub(StubElement parent, IStubElementType elementType,
                          @Nullable StringRef ref) {
        super(parent, elementType, ref);
    }
}
