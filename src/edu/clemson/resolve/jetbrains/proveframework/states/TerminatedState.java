package edu.clemson.resolve.jetbrains.proveframework.states;

public class TerminatedState extends AbstractState {
    public static final TerminatedState INSTANCE = new TerminatedState();

    protected TerminatedState() {
    }

    @Override
    public boolean isInProgress() {
        return false;
    }

    @Override
    public boolean isDefect() {
        return true;
    }

    @Override
    public boolean wasLaunched() {
        return true;
    }

    @Override
    public boolean isFinal() {
        return true;
    }

    @Override
    public boolean wasTerminated() {
        return true;
    }
}
