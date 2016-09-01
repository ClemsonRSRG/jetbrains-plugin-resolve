package edu.clemson.resolve.jetbrains.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.diagnostic.Logger;

import javax.swing.*;
import java.beans.PropertyDescriptor;

/**
 * Created by daniel on 9/1/16.
 */
public class PropertyToggleAction extends ToggleAction {

    private static final Logger LOG = Logger.getInstance("edu.clemson.resolve.jetbrains.actions.PropertyToggleAction");
    private final Object _target;
    private PropertyDescriptor _property;

    public PropertyToggleAction(String actionName,
                                String toolTip,
                                Icon icon,
                                Object target,
                                String property) {
        super(actionName, toolTip, icon);
        _target = target;
        _property = IntrospectionUtil.getProperty(target.getClass(), property);
        if (!isPropertyValid(property)) _property = null;
    }

    @Override
    public boolean isSelected(AnActionEvent e) {
        return false;
    }

    @Override
    public void setSelected(AnActionEvent e, boolean state) {

    }
}
