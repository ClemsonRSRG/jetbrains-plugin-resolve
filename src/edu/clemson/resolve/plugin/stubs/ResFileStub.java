package edu.clemson.resolve.plugin.stubs;

import com.intellij.psi.stubs.PsiFileStubImpl;
import edu.clemson.resolve.plugin.psi.ResFile;

public class ResFileStub extends PsiFileStubImpl<ResFile> {
    public ResFileStub(ResFile file) {
        super(file);
    }
}
