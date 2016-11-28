package edu.clemson.resolve.jetbrains.verifier;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.keymap.KeymapUtil;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.JBFont;
import com.intellij.util.ui.UIUtil;
import edu.clemson.resolve.vcgen.VC;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

public class VerifierPanel extends JBPanel {

    public static final Logger LOG = Logger.getInstance("RESOLVE VerifierPanel");

    private final Project project;

    public VerificationConditionSelectorPanel vcSelectorPanel = null;
    public JPanel startingPanel = null;

    public VerifierPanel(Project project) {
        super(new BorderLayout());
        this.project = project;
        createStartingGUI();
    }

    public List<VerificationPreviewEditor> getActivePreviewEditors() {
        List<VerificationPreviewEditor> result = new ArrayList<>();
        if (vcSelectorPanel != null) result.addAll(vcSelectorPanel.previewEditors);
        return result;
    }

    @Nullable
    public VerificationConditionSelectorPanel getVcSelectorPanel() {
        return vcSelectorPanel;
    }

    private void createStartingGUI() {
        startingPanel = new JPanel();
        startingPanel.setLayout(new BoxLayout(startingPanel, BoxLayout.Y_AXIS));
        startingPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        String proverShortcut = KeymapUtil.getFirstKeyboardShortcutText("resolve.ProveVCs");
        JBLabel startingLabel = new JBLabel(
                "<html>" +
                "<div style='text-align: center;'>" +
                "<font color='#7E7C7B'>" +
                "<b>Program has not yet been verified</b>" +
                "<br><br>" +
                "Right-click an open editor and select<br>" +
                "\"RESOLVE Prove Program\"" +
                "<br>" +
                "(shortcut: <span style=\"color: #7CB5FA\">" + proverShortcut + "</span>)" +
                "<br><br>" +
                "Left click the VC buttons<br> in the gutter to view specific proof obligations" + //todo: Show vc button icon in the instruction text...
                "</font>" +
                "</html>", JBLabel.CENTER);
        startingLabel.setFont(createFont(12));
        startingPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        startingPanel.add(startingLabel);
        add(startingPanel);
    }

    public void createVerifierView(Collection<VC> vcs) {
        this.removeAll();
        vcSelectorPanel = new VerificationConditionSelectorPanel(project, vcs);
        add(vcSelectorPanel, BorderLayout.CENTER);
        revalidate();
    }

    public void createVCOnlyView(Collection<VC> vcs) {
        this.removeAll();
        vcSelectorPanel = new VerificationConditionSelectorPanel(project, vcs);
        add(vcSelectorPanel, BorderLayout.CENTER);
        revalidate();
    }

    public void revertToBaseGUI() {
        for (VerificationPreviewEditor e : getActivePreviewEditors()) {
            e.removeNotify();
        }
        this.removeAll();
        this.vcSelectorPanel = null;
        createStartingGUI();
        revalidate();
    }

    public static Font createFont(int size) {
        return JBFont.create(new Font(UIUtil.getMenuFont().getName(), Font.PLAIN, size));
    }
}
