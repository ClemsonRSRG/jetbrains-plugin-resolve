package edu.clemson.resolve.plugin;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.LayeredIcon;
import com.sun.tools.corba.se.idl.toJavaPortable.Helper;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public interface RESOLVEIcons {

    public static final Icon MODULE = IconLoader.getIcon("/edu/clemson/resolve/icons/module.png");
    public static final Icon FILE = IconLoader.getIcon("/edu/clemson/resolve/icons/file.png");
    public static final Icon FACILITY = IconLoader.getIcon("/edu/clemson/resolve/icons/facility.png");
    public static final Icon IMPL = IconLoader.getIcon("/edu/clemson/resolve/icons/implementation.png");

    public static final Icon CONCEPT = IconLoader.getIcon("/edu/clemson/resolve/icons/concept.png");
    public static final Icon SPEC_EXTENSION = IconLoader.getIcon("/edu/clemson/resolve/icons/spec_extension.png");

    public static final Icon PRECIS = IconLoader.getIcon("/edu/clemson/resolve/icons/precis.png");
    public static final Icon PRECIS_EXTENSION = IconLoader.getIcon("/edu/clemson/resolve/icons/precis_extension.png");

    public static final Icon TYPE = IconLoader.getIcon("/edu/clemson/resolve/icons/type.png");
    public static final Icon OPERATION = AllIcons.Nodes.Function;
    public static final Icon VARIABLE = AllIcons.Nodes.Variable;
    Icon FIELD = AllIcons.Nodes.Field;
}
