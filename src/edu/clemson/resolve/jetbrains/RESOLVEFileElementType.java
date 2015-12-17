package edu.clemson.resolve.jetbrains;

import com.intellij.psi.tree.IStubFileElementType;
import edu.clemson.resolve.jetbrains.psi.ResFile;

public class RESOLVEFileElementType
        extends
            IStubFileElementType<ResFile.ResFileStub> {

    public static final IStubFileElementType INSTANCE =
            new RESOLVEFileElementType();
    public static final int VERSION = 13;

    private RESOLVEFileElementType() {
        super("RESOLVE_FILE", RESOLVELanguage.INSTANCE);
    }

    @Override public int getStubVersion() {
        return VERSION;
    }
}
