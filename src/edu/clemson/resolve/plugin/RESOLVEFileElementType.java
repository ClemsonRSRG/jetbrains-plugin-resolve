package edu.clemson.resolve.plugin;

import com.intellij.psi.tree.IStubFileElementType;
import edu.clemson.resolve.plugin.stubs.ResFileStub;

public class RESOLVEFileElementType extends IStubFileElementType<ResFileStub> {

    public static final IStubFileElementType INSTANCE = new RESOLVEFileElementType();
    public static final int VERSION = 13;

    private RESOLVEFileElementType() {
        super("RESOLVE_FILE", RESOLVELanguage.INSTANCE);
    }

    @Override public int getStubVersion() {
        return VERSION;
    }

}
