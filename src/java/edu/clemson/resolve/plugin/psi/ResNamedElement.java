package edu.clemson.resolve.plugin.psi;

import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiNameIdentifierOwner;

public interface ResNamedElement
        extends
            ResTypeOwner, ResCompositeElement, PsiNameIdentifierOwner, NavigationItem {
}
