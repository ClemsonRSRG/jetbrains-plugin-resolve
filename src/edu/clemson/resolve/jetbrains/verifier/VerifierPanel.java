package edu.clemson.resolve.jetbrains.verifier;

import com.intellij.application.options.colors.PreviewPanel;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.colors.EditorFontType;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBLayeredPane;
import com.intellij.util.ui.JBFont;
import com.intellij.util.ui.UIUtil;
import edu.clemson.resolve.jetbrains.RESOLVEIcons;
import edu.clemson.resolve.jetbrains.highlighting.RESOLVEColorsAndFontsPage;
import org.jdesktop.swingx.border.DropShadowBorder;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/** Pairs a vc display panel with (optional) derivation info panel */
public class VerifierPanel extends JPanel {

    public static final Logger LOG = Logger.getInstance("RESOLVE VerifierPanel");
    private VerificationConditionPanel vcPanel;
    private final Project project;


    public VerifierPanel(Project project) {
        this.project = project;
        //revalidate();
        createBaseGUI();
    }

    private void createBaseGUI() {
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        this.add(new VCPanelMock().getComponent());
        //this.add(new VerificationConditionPanel().getOuterMostComponent());
        //this.getLayout().
        //this.vcPanel = getVerificationConditionPanel();
        //splitPane.setFirstComponent(vcPanel.getOuterMostComponent());

    }

    //
    public void addVerificationConditionPanel(VerificationConditionPanel vcp) {

        /*Splitter splitPane = new Splitter();
        splitPane.setFirstComponent(vcp.getOuterMostComponent());
        this.vcPanel = vcp;
        this.add(splitPane);
        revalidate();*/
       // this.v
        this.add(vcp.getOuterMostComponent(), BorderLayout.NORTH);
        revalidate();
    }

    public static class VCPanelMock {

        //we use a layered one because we'll have one layer for the goal, one for the givens...
        JPanel baseComponent;

        public VCPanelMock() {
            //WORK 1 below
            /*
            baseComponent = new JPanel();
            baseComponent.setLayout(new BoxLayout(baseComponent, BoxLayout.Y_AXIS));
            baseComponent.setOpaque(true);
            baseComponent.setBackground(JBColor.WHITE);
            baseComponent.setBorder(new LineBorder(JBColor.LIGHT_GRAY, 1, true));

            JLabel vcPanelLabel = new JBLabel("Requires clause of Increment");
            vcPanelLabel.setFont(createFont(true, 12));
            vcPanelLabel.setIcon(RESOLVEIcons.VC_PANEL);
            vcPanelLabel.setHorizontalAlignment(JLabel.LEFT);

            baseComponent.add(vcPanelLabel, BorderLayout.PAGE_START);

            JComponent goalComponent = new JPanel();
            Dimension size = new Dimension(150,100);
            goalComponent.setMaximumSize(size);
            goalComponent.setPreferredSize(size);
            goalComponent.setMinimumSize(size);

            //goal
            JTextArea goalTextArea = new JTextArea(1, 20);
            goalTextArea.setEditable(false);
            TitledBorder goalBorder = BorderFactory.createTitledBorder(
                    new LineBorder(JBColor.LIGHT_GRAY, 1, true), "Goal:");
            goalBorder.setTitleFont(createFont(true, 14));
            goalTextArea.setBorder(goalBorder);
            goalTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
            goalTextArea.setPreferredSize(new Dimension(4, 5));

            //givens
            //TODO

            baseComponent.add(goalTextArea);
            baseComponent.add(Box.createVerticalGlue());
            */



            //WORK 2 below:

            JPanel pane0 = new JPanel();
            JPanel pane1 = new JPanel();

            JComponent component = new JPanel();
            Dimension size = new Dimension(200,100);
            component.setMaximumSize(size);
            component.setPreferredSize(size);
            component.setMinimumSize(size);
            component.setOpaque(true);
            component.setBackground(JBColor.WHITE);

            TitledBorder goalBorder = new TitledBorder(new LineBorder(JBColor.LIGHT_GRAY, 1, true),
                    "Goal:",
                    TitledBorder.LEFT,
                    TitledBorder.DEFAULT_POSITION);
            goalBorder.setTitleFont(createFont(true, 14));
            goalBorder.setTitleColor(JBColor.BLACK);

            component.setBorder(goalBorder);

            JLabel label = new JLabel("This is a JLabel");
            label.setFont(createFont(true, 12));
            label.setIcon(RESOLVEIcons.VC_PANEL);
            label.setHorizontalAlignment(JLabel.LEFT);
            label.setLayout(new BoxLayout(label, BoxLayout.X_AXIS));
            label.add(Box.createHorizontalGlue());

            String title;
            //if (doItRight) {
            //    title = "Matched";
                //label.setAlignmentX(LEFT_ALIGNMENT);
           // } else {
                title = "Mismatched";
            //}

            pane0.setBorder(new LineBorder(JBColor.LIGHT_GRAY, 1, true));
            pane0.setLayout(new BorderLayout());
            pane0.setOpaque(true);
            pane0.setBackground(JBColor.WHITE);

            pane1.setBorder(new EmptyBorder(4, 4, 4, 4));
            pane1.setLayout(new BorderLayout());
            pane1.setOpaque(true);
            pane1.setBackground(JBColor.WHITE);

            pane1.add(label, BorderLayout.PAGE_START);
            pane1.add(component);

            pane0.add(pane1);

            baseComponent = pane0;



            //Work3 below
  /*          JPanel innerPanel = new JPanel();
            innerPanel.setBorder(new LineBorder(JBColor.LIGHT_GRAY, 1, true));
            innerPanel.setOpaque(true);
            innerPanel.setBackground(JBColor.WHITE);

            // give it a Y axis to stuff is added top to bottom
            innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

            // this is a temp panel ill used to add the labels
            JPanel tPanel = new JPanel();
            tPanel.setOpaque(true);
            tPanel.setBackground(JBColor.WHITE);
            // its an x axis to add stuff left to right
            tPanel.setLayout(new BoxLayout(tPanel, BoxLayout.X_AXIS));

            // create and add a label to the temp panel
            JLabel label = new JLabel("Requires clause of Increment");
            label.setFont(createFont(true, 12));
            label.setIcon(RESOLVEIcons.VC_PANEL);
            tPanel.add(label);
            // use our stretchy glue to fill the space to the right of the label
            tPanel.add(Box.createHorizontalGlue());


            innerPanel.add(tPanel);

            JComponent component = new JPanel();

            component.setOpaque(true);
            component.setBackground(JBColor.WHITE);

            TitledBorder goalBorder = new TitledBorder(new LineBorder(JBColor.LIGHT_GRAY, 1, true),
                    "Goal:",
                    TitledBorder.LEFT,
                    TitledBorder.DEFAULT_POSITION);
            goalBorder.setTitleFont(createFont(true, 14));
            goalBorder.setTitleColor(JBColor.BLACK);

            component.setBorder(goalBorder);



            // add the temp panel to the inner panel
            innerPanel.add(tPanel);
            innerPanel.add(component);

            baseComponent = innerPanel;*/
        }

        private Font createFont(boolean bold, int size) {
            int style = bold ? Font.BOLD : Font.PLAIN;
            return JBFont.create(new Font(UIUtil.getMenuFont().getName(), style, size));
        }

        public JComponent getComponent() {
            return baseComponent;
        }
    }
}
