package edu.clemson.resolve.plugin.stubs.index;

import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StringStubIndexExtension;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.psi.stubs.StubIndexKey;
import edu.clemson.resolve.plugin.RESOLVEFileType;
import edu.clemson.resolve.plugin.psi.impl.ResAbstractTypeDecl;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class RESOLVETypesIndex extends StringStubIndexExtension<ResAbstractTypeDecl> {
    public static final StubIndexKey<String, ResAbstractTypeDecl> KEY =
            StubIndexKey.createIndexKey("go.type.name");

    @Override
    public int getVersion() {
        return RESOLVEFileType.VERSION + 2;
    }

    @NotNull
    @Override
    public StubIndexKey<String, ResAbstractTypeDecl> getKey() {
        return KEY;
    }

    @NotNull
    public static Collection<ResAbstractTypeDecl> find(@NotNull String name,
                                                       @NotNull Project project,
                                                       GlobalSearchScope scope) {
        return StubIndex.getElements(KEY, name, project, scope,
                ResAbstractTypeDecl.class);
    }
}