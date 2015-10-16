package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import edu.clemson.resolve.plugin.RESOLVEIcons;
import edu.clemson.resolve.plugin.ResTypes;
import edu.clemson.resolve.plugin.psi.ResConceptModule;
import edu.clemson.resolve.plugin.psi.ResModule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public abstract class ResAbstractModuleImpl
        extends
            ResNamedElementImpl implements ResModule {

    public ResAbstractModuleImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull @Override public Icon getIcon(int i) {
        Icon result = RESOLVEIcons.FILE;
        if (this instanceof ResFacilityModuleImpl) {
            result = RESOLVEIcons.FACILITY;
        }
        else if (this instanceof ResConceptModule) {
            result = RESOLVEIcons.CONCEPT;
        }
        return result;
    }

    @Override public boolean isPublic() {
        return true;
    }

    @Nullable @Override public PsiElement getIdentifier() {
        return findNotNullChildByType(ResTypes.IDENTIFIER);
    }

}
