package edu.clemson.resolve.jetbrains.proveframework.states;

public class NotRunState implements VCStateInfo {

    private static final NotRunState INSTANCE = new NotRunState();

    private NotRunState() {
    }

    /** This state is common for all instances and doesn't contains instance-specific information */
    public static NotRunState getInstance() {
        return INSTANCE;
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
