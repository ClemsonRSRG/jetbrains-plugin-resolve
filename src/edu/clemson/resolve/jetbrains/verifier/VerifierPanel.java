package edu.clemson.resolve.jetbrains.verifier;

import com.intellij.application.options.colors.PreviewPanel;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.colors.EditorFontType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Splitter;
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
    private VCPanelMock vcPanel;
    private final Project project;


    public VerifierPanel(Project project) {
        this.project = project;
        //revalidate();
        createBaseGUI();
    }

    private void createBaseGUI() {
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        this.vcPanel = new VCPanelMock();
        /*Splitter splitPane = new Splitter();
        splitPane.setFirstComponent(vcp.getOuterMostComponent());
        this.vcPanel = vcp;
        this.add(splitPane);
        revalidate();*/

        this.add(vcPanel.getComponent());
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
            //WORK 2 below:

            JPanel pane0 = new JPanel();
            JPanel pane1 = new JPanel();

            //for the goal box
            JComponent goalComponent = new JPanel();
            goalComponent.setOpaque(true);
            goalComponent.setBackground(JBColor.WHITE);

            TitledBorder goalBorder = new TitledBorder(new LineBorder(JBColor.LIGHT_GRAY, 1, true),
                    "Goal:",
                    TitledBorder.LEFT,
                    TitledBorder.DEFAULT_POSITION);
            goalBorder.setTitleFont(createFont(true, 14));
            goalBorder.setTitleColor(JBColor.BLACK);

            goalComponent.setBorder(goalBorder);

            //for the givens box
            JComponent givensComponent = new JPanel();
            givensComponent.setOpaque(true);
            givensComponent.setBackground(JBColor.WHITE);

            TitledBorder givenBorder = new TitledBorder(new LineBorder(JBColor.LIGHT_GRAY, 1, true),
                    "Givens:",
                    TitledBorder.LEFT,
                    TitledBorder.DEFAULT_POSITION);
            givenBorder.setTitleFont(createFont(true, 14));
            givenBorder.setTitleColor(JBColor.BLACK);
            givensComponent.setBorder(givenBorder);


            JPanel titlePanel = new JPanel();
            titlePanel.setOpaque(true);
            titlePanel.setBackground(JBColor.WHITE);
            // its an x axis to add stuff left to right
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
            // create and add a label to the temp panel
            JLabel label = new JLabel("Requires clause of Push()");
            label.setFont(createFont(true, 12));
            label.setIcon(RESOLVEIcons.VC_PANEL);
            titlePanel.add(label);
            // use our stretchy glue to fill the space to the right of the label
            titlePanel.add(Box.createHorizontalGlue());


            pane0.setBorder(new LineBorder(JBColor.LIGHT_GRAY, 1, true));
            pane0.setLayout(new BorderLayout());
            pane0.setOpaque(true);
            pane0.setBackground(JBColor.WHITE);

            pane1.setBorder(new EmptyBorder(4, 4, 4, 4));
            pane1.setLayout(new BoxLayout(pane1, BoxLayout.Y_AXIS));
            pane1.setOpaque(true);
            pane1.setBackground(JBColor.WHITE);

            pane1.add(titlePanel);
            pane1.add(Box.createRigidArea(new Dimension(0, 10)));
            pane1.add(goalComponent);
            pane1.add(givensComponent);

            pane0.add(pane1);

            baseComponent = pane0;
        }
        /*
        private TitledBorder createBorder(String label) {
            TitledBorder result = new TitledBorder(new LineBorder(JBColor.LIGHT_GRAY, 1, true),
                    label,
                    TitledBorder.LEFT,
                    TitledBorder.DEFAULT_POSITION);
            result.setTitleFont(createFont(true, 14));
            result.setTitleColor(JBColor.BLACK);
            return result;
        }*/

        private Font createFont(boolean bold, int size) {
            int style = bold ? Font.BOLD : Font.PLAIN;
            return JBFont.create(new Font(UIUtil.getMenuFont().getName(), style, size));
        }

        public JComponent getComponent() {
            return baseComponent;
        }
    }
}
