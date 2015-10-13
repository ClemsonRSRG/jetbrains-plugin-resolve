package edu.clemson.resolve.plugin.stubs.index;

import com.intellij.psi.stubs.StringStubIndexExtension;
import com.intellij.psi.stubs.StubIndexKey;
import edu.clemson.resolve.plugin.RESOLVEFileType;
import edu.clemson.resolve.plugin.psi.ResNamedElement;
import org.jetbrains.annotations.NotNull;

/**
 * Created by daniel on 10/13/15.
 */
public class RESOLVEAllVisibleNamesIndex
        extends
            StringStubIndexExtension<ResNamedElement> {
    public static final StubIndexKey<String, ResNamedElement> ALL_PUBLIC_NAMES =
            StubIndexKey.createIndexKey("resolve.all.name");

    @Override public int getVersion() {
        return RESOLVEFileType.VERSION;
    }

    @NotNull @Override public StubIndexKey<String, ResNamedElement> getKey() {
        return ALL_PUBLIC_NAMES;
    }
}