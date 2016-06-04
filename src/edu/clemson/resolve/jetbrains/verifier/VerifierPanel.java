package edu.clemson.resolve.jetbrains.verifier;

import com.intellij.openapi.actionSystem.IdeActions;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.impl.EditorEmptyTextPainter;
import com.intellij.openapi.keymap.KeymapUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Splitter;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.JBFont;
import com.intellij.util.ui.UIUtil;
import com.sun.istack.internal.NotNull;
import edu.clemson.resolve.jetbrains.RESOLVEIcons;
import edu.clemson.resolve.jetbrains.actions.GenerateVCsAction;
import edu.clemson.resolve.proving.Antecedent;
import edu.clemson.resolve.proving.Consequent;
import edu.clemson.resolve.proving.absyn.PExp;
import edu.clemson.resolve.vcgen.VC;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
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
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        String vcShortcut = KeymapUtil.getFirstKeyboardShortcutText("resolve.GenVCs");
        JLabel emptyLabel = new JBLabel(
                "<html>" +
                "<div style='text-align: center;'>" +
                "<font color='#7E7C7B'>" +
                "<b>No Verification Conditions (VCs)<br>generated</b>" +
                "<br><br>" +
                "Right-click an open editor and select<br>" +
                "\"RESOLVE Generate VCs\"" +
                "<br><br>" +
                "shortcut: <span style=\"color: #7CB5FA\">" + vcShortcut + "</span>" +
                "</font>" +
                "</html>", JLabel.CENTER);
        emptyLabel.setFont(createFont(false, 12));
        JPanel dummypanel = new JBPanel();
        dummypanel.setOpaque(false);
        this.add(Box.createRigidArea(new Dimension(0, 50)));
        this.add(emptyLabel);
        /*
        Splitter splitPane = new Splitter(true);
        splitPane.setFirstComponent(new VCPanelMock().getComponent());
        splitPane.setSecondComponent(new AssertiveCodeBrowserMock().getComponent());
        this.add(splitPane);
        */

        //OLD
        //this.add(vcPanel.getComponent());

    }

    public void addVerificationConditionPanel(VCPanelMock vcp) {
        /*Splitter splitPane = new Splitter();
        splitPane.setFirstComponent(vcp.getOuterMostComponent());
        this.vcPanel = vcp;
        this.add(splitPane);
        revalidate();*/
       // this.v
        this.add(vcp.getComponent(), BorderLayout.NORTH);
        revalidate();
    }

    public static class VCPanelMock {

        private JPanel baseComponent;
        private final String explanation, goal, givens;

        public VCPanelMock(VC vc) {
            this.explanation = vc.getConsequentInfo().explanation;
            this.goal = vc.getConsequent().toString();

            int i = 1;
            boolean first = true;
            StringBuilder sb = new StringBuilder();
            for (PExp e : vc.getAntecedent()) {
                if (first) {
                    sb.append(i).append(".").append(e.toString());
                    first = false;
                }
                else {
                    sb.append("\n");
                    sb.append(i).append(".").append(e.toString());
                }
                i++;
            }
            this.givens = sb.toString();
        }

        private void createGUI() {
            //WORK 2 below:

            JPanel pane0 = new JBPanel();
            JPanel pane1 = new JBPanel();

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
            JComponent givensComponent = new JBPanel();
            givensComponent.setOpaque(true);
            givensComponent.setBackground(JBColor.WHITE);

            TitledBorder givenBorder = new TitledBorder(new LineBorder(JBColor.LIGHT_GRAY, 1, true),
                    "Givens:",
                    TitledBorder.LEFT,
                    TitledBorder.DEFAULT_POSITION);
            givenBorder.setTitleFont(createFont(true, 14));
            givenBorder.setTitleColor(JBColor.BLACK);
            givensComponent.setBorder(givenBorder);

            JPanel titlePanel = new JBPanel();
            titlePanel.setOpaque(true);
            titlePanel.setBackground(JBColor.WHITE);
            // its an x axis to add stuff left to right
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
            // create and add a label to the temp panel
            JLabel label = new JLabel();
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
            pane1.add(Box.createRigidArea(new Dimension(0, 30)));

            pane0.add(pane1);
            baseComponent = pane0;
        }

        public JComponent getComponent() {
            return baseComponent;
        }
    }

    public static class AssertiveCodeBrowserMock {
        private final JPanel baseComponent;

        public AssertiveCodeBrowserMock() {
            baseComponent = new JPanel();
            baseComponent.setOpaque(true);
            baseComponent.setBackground(JBColor.WHITE);
            baseComponent.setBorder(new LineBorder(JBColor.LIGHT_GRAY, 1, true));
        }

        public JComponent getComponent() {
            return baseComponent;
        }
    }
    /*
    private static TitledBorder createBorder(String label) {
        TitledBorder result = new TitledBorder(new LineBorder(JBColor.LIGHT_GRAY, 1, true),
                label,
                TitledBorder.LEFT,
                TitledBorder.DEFAULT_POSITION);
        result.setTitleFont(createFont(true, 14));
        result.setTitleColor(JBColor.BLACK);
        return result;
    }
    */
    private static Font createFont(boolean bold, int size) {
        int style = bold ? Font.BOLD : Font.PLAIN;
        return JBFont.create(new Font(UIUtil.getMenuFont().getName(), style, size));
    }
}
