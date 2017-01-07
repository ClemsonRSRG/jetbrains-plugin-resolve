package edu.clemson.resolve.jetbrains.verifier;

import com.intellij.openapi.util.IconLoader;
import com.intellij.util.ui.AnimatedIcon;
import edu.clemson.resolve.jetbrains.RESOLVEIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Adapted from the "swingset3" java project available here:
 * https://java.net/projects/swingset3/sources/svn/show/trunk/SwingSet3/src/com/sun/swingset3/demos
 */
public class ConditionCollapsiblePanel extends JPanel {

    private static final int FRAMES_COUNT = 8;
    public static final Icon[] FRAMES = new Icon[FRAMES_COUNT];
    public enum Orientation { HORIZONTAL, VERTICAL }

    private final JPanel panel;
    private final JComponent child;

    public AnimatedIcon processingSpinner;
    public JToggleButton expandButton; // may be null, if no title was supplied
    private boolean expanded = true;
    private Box collapseControlBar = null;

    public ConditionCollapsiblePanel(@NotNull JComponent child,
                                     @NotNull String title,
                                     @Nullable String tooltip) {
        this.child = child;

        setLayout(new BorderLayout());
        panel = new JPanel(new BorderLayout());

        add(panel, BorderLayout.CENTER);
        panel.add(child, BorderLayout.CENTER);
        collapseControlBar = createCollapseControl(title, tooltip);

        /*if (state == State.PROCESSING) {
            collapseControlBar.add(processingSpinner, RIGHT_ALIGNMENT);
            collapseControlBar.add(Box.createRigidArea(new Dimension(10, 0)), RIGHT_ALIGNMENT);
        }*/
        add(collapseControlBar, BorderLayout.NORTH);
    }

    private Box createCollapseControl(String title, String tooltip) {
        // Add upper panel to hold collapse control
        Box box = Box.createHorizontalBox();

        //TODO: Get rid of annoying JToggleButtonGradiant by subclassing and overriding the color() method perhaps?
        expandButton = new JToggleButton(IconLoader.getIcon("/edu/clemson/resolve/icons/righttriangle.png"));
        expandButton.setSelectedIcon(IconLoader.getIcon("/edu/clemson/resolve/icons/downtriangle.png"));

        JLabel label = new JLabel(title);
        label.setFont(VerifierPanel.createFont(12));

        expandButton.setBorder(new EmptyBorder(0,4,0,0));
        expandButton.setToolTipText(tooltip);
        expandButton.setHorizontalTextPosition(JCheckBox.RIGHT);

        expandButton.setSelected(isExpanded());
        expandButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setExpanded(expandButton.isSelected());
            }
        });
        box.add(expandButton);

        //little bit of space between the 'expand' triangle and the "VC #.." text
        box.add(Box.createRigidArea(new Dimension(3, 0)));
        box.add(label, LEFT_ALIGNMENT);
        return box;
    }

    public void setExpanded(boolean expand) {
        this.expanded = expand;
        expandButton.setSelected(expand);
        if (expand) {
            Dimension childPrefSize = child.getPreferredSize();
            panel.setPreferredSize(new Dimension(30, childPrefSize.height));
        }
        else {
            panel.setPreferredSize(new Dimension(0, 0));
        }
        child.revalidate();
        repaint();

    }

    public boolean isExpanded() {
        return expanded;
    }
}