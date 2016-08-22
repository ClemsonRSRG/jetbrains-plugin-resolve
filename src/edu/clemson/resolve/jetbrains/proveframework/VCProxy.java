package edu.clemson.resolve.jetbrains.proveframework;

/**
 * Created by daniel on 8/22/16.
 */
public class VCProxy {

    private final String name;
    private final boolean preservePresentableName;
    private VCProxy parent;

    private AbstractState myState = NotRunState.getInstance();
}
