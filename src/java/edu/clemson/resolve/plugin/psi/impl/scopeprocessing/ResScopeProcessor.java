package edu.clemson.resolve.plugin.psi.impl.scopeprocessing;

import com.intellij.psi.scope.BaseScopeProcessor;

public abstract class ResScopeProcessor extends BaseScopeProcessor {
    public boolean isCompletion() {
        return false;
    }
}
