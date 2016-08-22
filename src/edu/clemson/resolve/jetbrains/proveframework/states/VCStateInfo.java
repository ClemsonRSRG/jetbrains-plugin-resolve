package edu.clemson.resolve.jetbrains.proveframework.states;

public interface VCStateInfo {

    /** @return If vc is being run */
    boolean isInProgress();

    /** If state is defect something wrong is with it and should be shown properly in UI. */
    boolean isDefect();

    /** @return {@code true} if prover has been already launched */
    boolean wasLaunched();

    /**
     * Describes final states, e.g such states will not be changed after finished event.
     * @return {@code true} if is final.
     */
    boolean isFinal();

    /**
     * @return {@code true} if particular vc proof was terminated by the user
     */
    boolean wasTerminated();
}
