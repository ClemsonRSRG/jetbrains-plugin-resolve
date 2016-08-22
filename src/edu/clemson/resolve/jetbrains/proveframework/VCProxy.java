package edu.clemson.resolve.jetbrains.proveframework;

import edu.clemson.resolve.jetbrains.proveframework.states.AbstractState;
import edu.clemson.resolve.jetbrains.proveframework.states.NotRunState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VCProxy {

    private final String name;
    private boolean isSuite;

    //private final boolean preservePresentableName;

    private List<VCProxy> children;
    private VCProxy parent;

    private AbstractState myState = NotRunState.getInstance();
    private Long duration = null; // duration is unknown
    private boolean hasProvedVCs = false;

    public VCProxy(String vcName, boolean isSuite, @Nullable String locationUrl) {
        this(vcName, isSuite, locationUrl, false);
    }

    public VCProxy(String vcName, boolean isSuite, @Nullable String locationUrl, boolean preservePresentableName) {
        this.name = vcName;
        this.isSuite = isSuite;
        //myLocationUrl = locationUrl;
        //myPreservePresentableName = preservePresentableName;
    }

    public boolean isInProgress() {
        return myState.isInProgress();
    }

    public boolean isDefect() {
        return myState.isDefect();
    }

    public boolean shouldRun() {
        return true;
    }
}
