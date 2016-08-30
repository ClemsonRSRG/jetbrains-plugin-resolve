package edu.clemson.resolve.jetbrains.ui;

import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.StateRestoringCheckBox;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import edu.clemson.resolve.jetbrains.RESOLVEIcons;
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

    public JCheckBox expandCheckBox; // may be null, if no title was supplied
    private Orientation orientation = Orientation.VERTICAL;
    private Dimension childPrefSize;
    private boolean expanded = true;

    public CollapsiblePanel(JComponent child, Orientation orientation,
                            String title, String tooltip) {
        this.orientation = orientation;
        this.child = child;
        setLayout(new BorderLayout());
        panel = new JPanel(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        panel.add(child, BorderLayout.CENTER);

        add(createCollapseControl(title, tooltip),
                orientation == Orientation.HORIZONTAL ? BorderLayout.WEST : BorderLayout.NORTH);
    }

    protected Component createCollapseControl(String title, String tooltip) {
        // Add upper panel to hold collapse control
        JPanel box = new JPanel();

        //THIS ONE KINDA WORKS...
        JToggleButton b = new JToggleButton(IconLoader.getIcon("/precis.png"));
        b.setSelectedIcon(IconLoader.getIcon("/concept.png"));
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        box.add(b);

        //JRadioButton
        //StateRestoringCheckBox b = new StateRestoringCheckBox(IconLoader.getIcon("/precis.png"));
       // b.setIcon(IconLoader.getIcon("/precis.png"));
        //b.setSelectedIcon(IconLoader.getIcon("/concept.png"));
        //box.add(b);
        /*Font font = VerifierPanel2.createFont(12);
        box.add(expandCheckBox);

        //expandCheckBox.setIcon(x);

        expandCheckBox.setFont(VerifierPanel2.createFont(12));
        expandCheckBox.setBorder(new EmptyBorder(0,4,0,0));
        expandCheckBox.setToolTipText(tooltip);
        expandCheckBox.setHorizontalTextPosition(JCheckBox.RIGHT);

*/
        //expandCheckBox.setSelectedIcon(x);

        //expandCheckBox.setSelected(isExpanded());
        //expandCheckBox.addChangeListener(new CollapseListener());
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