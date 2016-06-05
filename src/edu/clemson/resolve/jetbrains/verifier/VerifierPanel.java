package edu.clemson.resolve.jetbrains.verifier;

import com.intellij.codeInsight.preview.ColorPreviewComponent;
import com.intellij.ide.highlighter.JavaHighlightingColors;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.IdeActions;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.fileEditor.impl.EditorEmptyTextPainter;
import com.intellij.openapi.keymap.KeymapUtil;
import com.intellij.openapi.preview.PreviewPanelProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Splitter;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.Gray;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.JBFont;
import com.intellij.util.ui.UIUtil;
import com.sun.istack.internal.NotNull;
import edu.clemson.resolve.jetbrains.RESOLVEFileType;
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
    private VCPanelMock activeVcPanel;
    private final Project project;

    public VerifierPanel(Project project) {
        this.project = project;
        //revalidate();
        createBaseGUI();
    }

    public List<VerificationEditorPreview> getActivePreviewEditors() {
        List<VerificationEditorPreview> result = new ArrayList<>();
        if (activeVcPanel != null && activeVcPanel.goalPreview != null) {
            result.add(activeVcPanel.goalPreview);
        }
        return result;
    }

    private void createBaseGUI() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        String vcShortcut = KeymapUtil.getFirstKeyboardShortcutText("resolve.GenVCs");
        JLabel emptyLabel = new JBLabel(
                "<html>" +
                "<div style='text-align: center;'>" +
                "<font color='#7E7C7B'>" +
                "<b>No Verification Condition (VC)<br>Selected</b>" +
                "<br><br>" +
                "Right-click an open editor and select<br>" +
                "\"RESOLVE Generate VCs\"" +
                "<br>" +
                "(shortcut: <span style=\"color: #7CB5FA\">" + vcShortcut + "</span>)" +
                "<br><br>" +
                "Then left-click one of the VC icons<br>in the gutter to view" +
                "</font>" +
                "</html>", JLabel.CENTER);
        emptyLabel.setFont(createFont(12));
        JPanel dummypanel = new JBPanel();
        dummypanel.setOpaque(false);
        this.add(Box.createRigidArea(new Dimension(0, 50)));
        this.add(emptyLabel);
    }

    public void revertToBaseGUI() {
        if (activeVcPanel != null && activeVcPanel.goalPreview != null) {
            //we're going back to the default screen, so if there were active editors (before say the user messed
            //with the doc) remove em' here.
            //activeVcPanel.goalPreview.removeNotify();
        }
        this.removeAll();
        this.activeVcPanel = null;
        createBaseGUI();
        revalidate();
    }

    public void setActiveVcPanel(VCPanelMock vcp) {
        if (this.activeVcPanel != null && activeVcPanel.goalPreview != null) {
            //before we set the new one, let's try to remember to dispose of the existing one correctly
            //activeVcPanel.goalPreview.disposeUIResources();
            //activeVcPanel.goalPreview.removeNotify();
        }
        this.activeVcPanel = vcp;
        this.removeAll();   // clear any old stuff first
        Splitter splitPane = new Splitter(true);
        splitPane.setFirstComponent(vcp.getComponent());
        splitPane.setSecondComponent(new AssertiveCodeBrowserMock().getComponent());
        this.add(splitPane);
        revalidate();
    }

    public static class VCPanelMock  {
        private VerificationEditorPreview goalPreview = null;

        private JPanel baseComponent;
        private final String explanation, goal, givens;
        private final int vcNumber;
        private final Project project;

        public VCPanelMock(Project project, VC vc) {
            this.project = project;

            this.explanation = vc.getExplanation();
            this.goal = "test";
            this.givens = "1. test1<br>2. test2";
            this.vcNumber = vc.getNumber();
            this.baseComponent = createGUI();
            //this.explanation = vc.getConsequentInfo().explanation;
            //this.goal = vc.getConsequent().toString();

           /* int i = 1;
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
            this.givens = sb.toString();*/
        }

        private JPanel createGUI() {
            //WORK 2 below:

            JPanel pane0 = new JBPanel();
            JPanel pane1 = new JBPanel();

            //for the goal box
            JComponent goalComponent = new JPanel();
            goalComponent.setLayout(new BoxLayout(goalComponent, BoxLayout.Y_AXIS));
            goalComponent.setOpaque(true);
            goalComponent.setBackground(JBColor.WHITE);
            goalPreview = new VerificationEditorPreview(project, "Max_Length = 0");
            goalPreview.setOneLineMode(false);
            //goalPreview.addNotify();
            //v1.setBorder(new EmptyBorder(10, 10, 10 , 10));
            //v1.getEditor().getContentComponent().setBorder(new EmptyBorder(5, 5, 5,5 ));

            /*
            EditorTextField tf = new EditorTextField(editorDocument, project, RESOLVEFileType.INSTANCE, true);
            tf.setBorder(new EmptyBorder(3,3,3,3));
            tf.setOneLineMode(false);
            tf.setFileType(RESOLVEFileType.INSTANCE);
            tf.setBackground(Gray._237);
            tf.setFontInheritedFromLAF(true);
            tf.addNotify();
            if (tf.getEditor() != null) {
                tf.getEditor().setBorder(null);
            }*/
            goalComponent.setPreferredSize(goalComponent.getPreferredSize());
            goalComponent.add(goalPreview, CENTER_ALIGNMENT);

            TitledBorder goalBorder = new TitledBorder(new LineBorder(JBColor.LIGHT_GRAY, 1, true),
                    "<html>" +
                    "<font color='#515151' size='5'>" +
                    "<b>Goal:</b>" +
                    "</font>" +
                    "</html>",
                    TitledBorder.LEFT,
                    TitledBorder.DEFAULT_POSITION);

            goalBorder.setTitleFont(createFont(14));
            goalBorder.setTitleColor(JBColor.BLACK);

            goalComponent.setBorder(goalBorder);

            //for the givens box
            JComponent givensComponent = new JBPanel();
            givensComponent.setOpaque(true);
            givensComponent.setBackground(JBColor.WHITE);

            TitledBorder givenBorder = new TitledBorder(new LineBorder(JBColor.LIGHT_GRAY, 1, true),
                    "<html>" +
                    "<font color='#515151' size=\"12\">" +
                    "<b>Givens:</b>" +
                    "</font>" +
                    "</html>",
                    TitledBorder.LEFT,
                    TitledBorder.DEFAULT_POSITION);
            givensComponent.setBorder(givenBorder);

            JPanel titlePanel = new JBPanel();
            titlePanel.setOpaque(true);
            titlePanel.setBackground(JBColor.WHITE);
            // its an x axis to add stuff left to right
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
            // create and add a label to the temp panel
            String numAndExplanation =
                    "<html>" +
                        "<font color='#4A4F51'>" +
                            "<b>#" + vcNumber + " |</b> "  + explanation +
                        "</font>" +
                    "</html>";
            JLabel label = new JLabel(numAndExplanation);
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
            pane1.add(Box.createRigidArea(new Dimension(0, 20)));

            pane0.add(pane1);
            return pane0;
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
    private static Font createFont(int size) {
        return JBFont.create(new Font(UIUtil.getMenuFont().getName(), Font.PLAIN, size));
    }
}
