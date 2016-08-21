package edu.clemson.resolve.jetbrains.proveframework;

import com.intellij.execution.testframework.actions.TestTreeExpander;
import com.intellij.ide.OccurenceNavigator;
import com.intellij.openapi.Disposable;

import javax.swing.*;

/**
 * Created by daniel on 8/21/16.
 */
public class ToolbarPanel extends JPanel implements OccurenceNavigator, Disposable {
    protected final TestTreeExpander vcTreeExpander = new TestTreeExpander();

    @Override
    public boolean hasNextOccurence() {
        return false;
    }

    @Override
    public boolean hasPreviousOccurence() {
        return false;
    }

    @Override
    public OccurenceInfo goNextOccurence() {
        return null;
    }

    @Override
    public OccurenceInfo goPreviousOccurence() {
        return null;
    }

    @Override
    public String getNextOccurenceActionName() {
        return null;
    }

    @Override
    public String getPreviousOccurenceActionName() {
        return null;
    }

    @Override
    public void dispose() {

    }
}
