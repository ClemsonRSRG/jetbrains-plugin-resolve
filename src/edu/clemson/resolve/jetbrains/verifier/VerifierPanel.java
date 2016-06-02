package edu.clemson.resolve.jetbrains.verifier;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.colors.EditorFontType;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBLayeredPane;
import com.intellij.util.ui.JBFont;
import com.intellij.util.ui.UIUtil;
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
        setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height));
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
            this.baseComponent = new JPanel();
            baseComponent.setOpaque(true);
            baseComponent.setBackground(JBColor.WHITE);
            baseComponent.setBorder(new LineBorder(JBColor.LIGHT_GRAY, 1, true));

            //goal
            JTextArea goalTextArea = new JTextArea(1, 20);
            goalTextArea.setEditable(false);
            TitledBorder goalBorder = BorderFactory.createTitledBorder(
                    new LineBorder(JBColor.LIGHT_GRAY, 1, true), "Goal:");
            goalBorder.setTitleFont(JBFont.create(new Font(UIUtil.getMenuFont().getName(), Font.BOLD, 18)));
            goalTextArea.setBorder(goalBorder);

            //givens
            //TODO

            baseComponent.add(goalTextArea);
        }

        public JComponent getComponent() {
            return baseComponent;
        }
    }
}
