package edu.clemson.resolve.plugin;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public interface RESOLVEIcons {

    public static final Icon MODULE = IconLoader.getIcon("/edu/clemson/resolve/icons/module.png");
    public static final Icon FILE = IconLoader.getIcon("/edu/clemson/resolve/icons/file.png");
    public static final Icon FACILITY = IconLoader.getIcon("/edu/clemson/resolve/icons/facility.png");
    public static final Icon CONCEPT = IconLoader.getIcon("/edu/clemson/resolve/icons/concept.png");

    public static final Icon PRECIS = IconLoader.getIcon("/edu/clemson/resolve/icons/precis.png");

    public static final Icon TYPE = IconLoader.getIcon("/edu/clemson/resolve/icons/type.png");
    public static final Icon OPERATION = AllIcons.Nodes.Function;
    public static final Icon VARIABLE = AllIcons.Nodes.Variable;
    Icon FIELD = AllIcons.Nodes.Field;

}
