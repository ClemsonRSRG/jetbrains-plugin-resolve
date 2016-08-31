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

    static {
        FRAMES[0] = RESOLVEIcons.PROCESSING1;
        FRAMES[1] = RESOLVEIcons.PROCESSING2;
        FRAMES[2] = RESOLVEIcons.PROCESSING3;
        FRAMES[3] = RESOLVEIcons.PROCESSING4;
        FRAMES[4] = RESOLVEIcons.PROCESSING5;
        FRAMES[5] = RESOLVEIcons.PROCESSING6;
        FRAMES[6] = RESOLVEIcons.PROCESSING7;
        FRAMES[7] = RESOLVEIcons.PROCESSING8;
    }

    public static enum State {
        PROVED {
            @Override
            public Icon getIcon() {
                return RESOLVEIcons.PROVED;
            }
        },
        NOT_PROVED {
            @Override
            public Icon getIcon() {
                return RESOLVEIcons.NOT_PROVED;
            }
        },
        TIMED_OUT {
            @Override
            public Icon getIcon() {
                return RESOLVEIcons.TIMED_OUT;
            }
        },
        PROCESSING {
            @Override
            //Shouldn't get called. TODO: Would be nice if I could figure out how to get this returning an intellij AnimatedIcon
            public Icon getIcon() {
                return RESOLVEIcons.TIMED_OUT;
            }
        };

        public abstract Icon getIcon();
    }

    public enum Orientation { HORIZONTAL, VERTICAL }

    private JPanel panel;
    private JComponent child;

    public AnimatedIcon processingSpinner;
    public JToggleButton expandButton; // may be null, if no title was supplied
    private Orientation orientation = Orientation.VERTICAL;
    private Dimension childPrefSize;
    private boolean expanded = true;
    private Box collapseControlBar = null;
    private State state;

    public ConditionCollapsiblePanel(@NotNull JComponent child,
                                     @NotNull Orientation orientation,
                                     @NotNull String title,
                                     @Nullable String tooltip) {
        this(child, orientation, title, tooltip, State.PROCESSING);
    }

    public ConditionCollapsiblePanel(@NotNull JComponent child,
                                     @NotNull Orientation orientation,
                                     @NotNull String title,
                                     @Nullable String tooltip,
                                     @NotNull State state) {
        this.orientation = orientation;
        this.child = child;
        this.processingSpinner = new AnimatedIcon("processing", FRAMES, FRAMES[0], 800);
        this.state = state;

        setLayout(new BorderLayout());
        panel = new JPanel(new BorderLayout());
        add(processingSpinner, BorderLayout.EAST);

        add(panel, BorderLayout.CENTER);
        panel.add(child, BorderLayout.CENTER);
        collapseControlBar = createCollapseControl(title, tooltip);

        if (state == State.PROCESSING) {
            collapseControlBar.add(processingSpinner, RIGHT_ALIGNMENT);
            collapseControlBar.add(Box.createRigidArea(new Dimension(10, 0)), RIGHT_ALIGNMENT);
        }
        add(collapseControlBar, orientation == Orientation.HORIZONTAL ? BorderLayout.WEST : BorderLayout.NORTH);
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
        expandButton.addChangeListener(new CollapseListener());
        box.add(expandButton);

        //little bit of space between the 'expand' triangle and the "VC #.." text
        box.add(Box.createRigidArea(new Dimension(3, 0)));
        box.add(label, LEFT_ALIGNMENT);
        return box;
    }

    public void changeToFinalState(State e) {
        if (e == State.PROCESSING) {
            throw new IllegalArgumentException("final state means not processing!");
        }
        this.state = e;
        collapseControlBar.remove(processingSpinner);
        collapseControlBar.add(new JLabel(e.getIcon()), RIGHT_ALIGNMENT);
        collapseControlBar.add(Box.createRigidArea(new Dimension(10, 0)), RIGHT_ALIGNMENT);
        //vcNameLabel.setIcon(state.getIcon());
        revalidate();
    }

    public void setExpanded(boolean expanded) {
        boolean oldExpanded = this.expanded;
        if (oldExpanded != expanded) {
            if (expandButton != null) {
                expandButton.setSelected(expanded);
            }
            childPrefSize = child.getPreferredSize();
            this.expanded = expanded;
            if (orientation == Orientation.VERTICAL) {
                setCollapseHeight(expanded? childPrefSize.height : 0);

            } else if (orientation == Orientation.HORIZONTAL) {
                setCollapseWidth(expanded ? childPrefSize.width : 0);
            }
            firePropertyChange("expanded", oldExpanded, expanded);

        }
    }

    // intended only for animator, but must be public
    public void setCollapseHeight(int height) {
        panel.setPreferredSize(new Dimension(childPrefSize.width, height));
        child.revalidate();
        repaint();
    }

    // intended only for animator, but must be public
    public void setCollapseWidth(int width) {
        panel.setPreferredSize(new Dimension(width, childPrefSize.height));
        child.revalidate();
        repaint();
    }

    public boolean isExpanded() {
        return expanded;
    }

    // only used if checkbox is present
    private class CollapseListener implements ChangeListener {
        public void stateChanged(ChangeEvent event) {
            setExpanded(expandButton.isSelected());
        }
    }
}