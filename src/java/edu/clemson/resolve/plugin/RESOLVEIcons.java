package edu.clemson.resolve.plugin;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.LayeredIcon;
import com.sun.tools.corba.se.idl.toJavaPortable.Helper;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public interface RESOLVEIcons {

    public static final Icon FILE = IconLoader.getIcon("/edu/clemson/resolve/icons/file.png");
    public static final Icon CONCEPT = IconLoader.getIcon("/edu/clemson/resolve/icons/concept.png");
    public static final Icon ENHANCEMENT = IconLoader.getIcon("/edu/clemson/resolve/icons/enhancement.png");
    public static final Icon FACILITY = IconLoader.getIcon("/edu/clemson/resolve/icons/facility.png");
    public static final Icon PRECIS = IconLoader.getIcon("/edu/clemson/resolve/icons/precis.png");
    public static final Icon IMPL = IconLoader.getIcon("/edu/clemson/resolve/icons/implementation.png");
    public static final Icon MODULE = IconLoader.getIcon("/edu/clemson/resolve/icons/module.png");
    public static final Icon APPLICATION_RUN = Helper.createIconWithShift(MODULE, AllIcons.Nodes.RunnableMark);

    class Helper {
        @NotNull public static LayeredIcon createIconWithShift(
                @NotNull final Icon base, Icon mark) {
            LayeredIcon icon = new LayeredIcon(2) {
                @Override public int getIconHeight() { return base.getIconHeight(); }
            };
            icon.setIcon(base, 0);
            icon.setIcon(mark, 1, 0, base.getIconWidth() / 2);
            return icon;
        }
    }
}
