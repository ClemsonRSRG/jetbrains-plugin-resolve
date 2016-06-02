package edu.clemson.resolve.jetbrains.verifier;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Splitter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/** Pairs a vc display panel with (optional) derivation info panel */
public class VerifierPanel extends JPanel {

    public static final Logger LOG = Logger.getInstance("RESOLVE VerifierPanel");
    private VerificationConditionPanel vcPanel;
    private final Project project;

    public VerifierPanel(Project project) {
        //this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.project = project;
        setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height));
        revalidate();
        createGUI();
    }

    private void createGUI() {
        this.setLayout(new BorderLayout());

        Splitter splitPane = new Splitter();
        this.vcPanel = getVerificationConditionPanel();
        splitPane.setFirstComponent(vcPanel.getComponent());
    }

    public VerificationConditionPanel getVerificationConditionPanel() {
        LOG.info("createVerificationConditionPanel" + " " + project.getName());
        return new VerificationConditionPanel(this);
    }
}
