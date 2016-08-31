package edu.clemson.resolve.jetbrains.ui;

import com.intellij.openapi.util.IconLoader;
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

    public JToggleButton expandButton; // may be null, if no title was supplied
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
        Box box = Box.createHorizontalBox();

        //THIS ONE KINDA WORKS...
        expandButton = new JToggleButton(IconLoader.getIcon("/edu/clemson/resolve/icons/righttriangle.png"));
        expandButton.setSelectedIcon(IconLoader.getIcon("/edu/clemson/resolve/icons/downtriangle.png"));

        JLabel label = new JLabel(title);
        label.setFont(VerifierPanel2.createFont(12));

        expandButton.setBorder(new EmptyBorder(0,4,0,0));
        expandButton.setToolTipText(tooltip);
        expandButton.setHorizontalTextPosition(JCheckBox.RIGHT);

        expandButton.setSelected(isExpanded());
        expandButton.addChangeListener(new CollapseListener());
        box.add(expandButton);
        box.add(Box.createRigidArea(new Dimension(3,0)));
        box.add(label, LEFT_ALIGNMENT);
        return box;
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