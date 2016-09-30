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
    private boolean expanded = true;
    private Box collapseControlBar = null;
    private State state;

    public ConditionCollapsiblePanel(@NotNull JComponent child,
                                     @NotNull String title,
                                     @Nullable String tooltip) {
        this(child, title, tooltip, State.PROCESSING);
    }

    public ConditionCollapsiblePanel(@NotNull JComponent child,
                                     @NotNull String title,
                                     @Nullable String tooltip,
                                     @NotNull State state) {
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

    public void changeToFinalState(State e, double duration) {
        if (e == State.PROCESSING) {
            throw new IllegalArgumentException("final state means not processing!");
        }
        this.state = e;
        collapseControlBar.remove(processingSpinner);

        JLabel durationLab = new JLabel("<html><font color='#404040'><b>" + String.valueOf(duration) + "ms</b></html>");
        durationLab.setFont(VerifierPanel.createFont(9));
        durationLab.setHorizontalAlignment(JLabel.RIGHT);
        
        collapseControlBar.add(durationLab, RIGHT_ALIGNMENT);
        collapseControlBar.add(Box.createRigidArea(new Dimension(8, 0))); //some space between duration and the result icon
        collapseControlBar.add(new JLabel(e.getIcon()), RIGHT_ALIGNMENT);
        collapseControlBar.add(Box.createRigidArea(new Dimension(10, 0)), RIGHT_ALIGNMENT);
        revalidate();
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