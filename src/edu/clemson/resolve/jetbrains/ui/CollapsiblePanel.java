package edu.clemson.resolve.jetbrains.ui;

import edu.clemson.resolve.jetbrains.verifier2.VerifierPanel2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 *
 * @author aim
 */
public class CollapsiblePanel extends JPanel {
    public enum Orientation { HORIZONTAL, VERTICAL }

    private JPanel panel;
    private JComponent child;

    private JCheckBox expandCheckBox; // may be null, if no title was supplied
    private Orientation orientation = Orientation.VERTICAL;
    private Dimension childPrefSize;
    private boolean expanded = true;

    public CollapsiblePanel(JComponent child) {
        this(child, Orientation.VERTICAL);
    }

    public CollapsiblePanel(JComponent child, Orientation orientation) {
        this.orientation = orientation;
        this.child = child;
        setLayout(new BorderLayout());
        panel = new JPanel(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        panel.add(child, BorderLayout.CENTER);
    }

    /** Creates a new instance of CollapsiblePanel */
    public CollapsiblePanel(JComponent child, String title, String tooltip) {
        this(child, Orientation.VERTICAL, title, tooltip);
    }

    public CollapsiblePanel(JComponent child, String title) {
        this(child, Orientation.VERTICAL, title, null);
    }

    public CollapsiblePanel(JComponent child, Orientation orientation,
                            String title, String tooltip) {
        this(child, orientation);
        add(createCollapseControl(title, tooltip),
                orientation == Orientation.HORIZONTAL?
                        BorderLayout.WEST : BorderLayout.NORTH);

    }

    protected Component createCollapseControl(String title, String tooltip) {
        // Add upper panel to hold collapse control
        Box box = Box.createHorizontalBox();

        expandCheckBox = new JCheckBox(title);
        expandCheckBox.setFont(VerifierPanel2.createFont(12));
        expandCheckBox.setBorder(new EmptyBorder(0,4,0,0));
        expandCheckBox.setToolTipText(tooltip);
        expandCheckBox.setHorizontalTextPosition(JCheckBox.RIGHT);
        //expandCheckBox.setSelectedIcon(new ArrowIcon(ArrowIcon.SOUTH));
        //expandCheckBox.setIcon(new ArrowIcon(ArrowIcon.EAST));
        expandCheckBox.setSelected(isExpanded());

        expandCheckBox.addChangeListener(new CollapseListener());
        box.add(expandCheckBox);

        return box;
    }

    public void setExpanded(boolean expanded) {
        boolean oldExpanded = this.expanded;
        if (oldExpanded != expanded) {
            if (expandCheckBox != null) {
                expandCheckBox.setSelected(expanded);
            }
            childPrefSize = child.getPreferredSize();
            this.expanded = expanded;

            /*if (isShowing()) {
                // only animate if currently showing
                Animator animator = null;
                if (orientation == Orientation.VERTICAL) {
                    animator = new Animator(600, new PropertySetter(this, "collapseHeight",
                            expanded ? 0 : childPrefSize.height, expanded ? childPrefSize.height : 0));

                }
                if (orientation == Orientation.HORIZONTAL) {
                    animator = new Animator(600, new PropertySetter(this, "collapseWidth",
                            expanded ? 0 : childPrefSize.width, expanded ? childPrefSize.width : 0));
                }
                animator.setStartDelay(10);
                animator.setAcceleration(.2f);
                animator.setDeceleration(.3f);
                animator.start();
            } else {*/
                if (orientation == Orientation.VERTICAL) {
                    setCollapseHeight(expanded? childPrefSize.height : 0);

                } else if (orientation == Orientation.HORIZONTAL) {
                    setCollapseWidth(expanded? childPrefSize.width : 0);
                }
         //   }
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

    public void setExpandedIcon(Icon expandedIcon) {
        if (expandCheckBox != null) {
            expandCheckBox.setSelectedIcon(expandedIcon);
        }
    }

    public void setCollapsedIcon(Icon collapsedIcon) {
        if (expandCheckBox != null) {
            expandCheckBox.setIcon(collapsedIcon);
        }
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        if (expandCheckBox != null) {
            expandCheckBox.setFont(font);
        }
    }

    @Override
    public void setForeground(Color foreground) {
        super.setForeground(foreground);
        if (expandCheckBox != null) {
            expandCheckBox.setForeground(foreground);
        }
    }

    @Override
    public void updateUI() {
        super.updateUI();
        //configureDefaults();
    }

    protected void configureDefaults() {
        if (expandCheckBox != null) {
            if (UIManager.getLookAndFeel().getName().equals("Nimbus")) {
                expandCheckBox.setBorder(new EmptyBorder(0,4,0,0));
            } else {
                expandCheckBox.setBorder(new EmptyBorder(0,0,0,0));
            }
        }
    }

    // only used if checkbox is present
    private class CollapseListener implements ChangeListener {
        public void stateChanged(ChangeEvent event) {
            setExpanded(expandCheckBox.isSelected());
        }
    }
}