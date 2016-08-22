package edu.clemson.resolve.jetbrains.proveframework.states;

/** Indicates that a vc is in the process of being proven (or at least is in line) */
public class VCInProgressState extends AbstractState {

    public static final VCInProgressState TEST = new VCInProgressState();

    protected VCInProgressState() {
    }

    @Override
    public boolean isInProgress() {
        return true;
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
        return false;
    }

    @Override
    public boolean wasTerminated() {
        return false;
    }
}
