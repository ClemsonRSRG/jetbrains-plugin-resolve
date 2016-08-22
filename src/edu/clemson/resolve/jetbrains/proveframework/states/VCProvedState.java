package edu.clemson.resolve.jetbrains.proveframework.states;

public class VCProvedState extends AbstractState {

    public static final VCProvedState INSTANCE = new VCProvedState();

    private VCProvedState() {
    }

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
        return true;
    }

    @Override
    public boolean isFinal() {
        return true;
    }

    @Override
    public boolean wasTerminated() {
        return false;
    }
}
