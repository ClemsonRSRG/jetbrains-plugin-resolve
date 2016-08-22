package edu.clemson.resolve.jetbrains.proveframework.states;

/**
 * Created by daniel on 8/22/16.
 */
public class VCNotProvedState extends AbstractState {
    @Override
    public boolean isInProgress() {
        return false;
    }

    @Override
    public boolean isDefect() {
        return false;
    }

    @Override
    public boolean wasLaunched() {
        return false;
    }

    @Override
    public boolean isFinal() {
        return false;
    }

    @Override
    public boolean wasTerminated() {
        return false;
    }
}
