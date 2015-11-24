package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.BaseScopeProcessor;

public abstract class ResScopeProcessor extends BaseScopeProcessor {

    public boolean isCompletion() {
        return false;
    }
}
