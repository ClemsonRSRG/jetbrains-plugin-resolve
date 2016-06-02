package edu.clemson.resolve.jetbrains.verifier;

import javax.swing.*;

/**
 * Created by daniel on 6/1/16.
 */
public class VerificationConditionPanel {
    private JPanel outerMostPanel;
    private JLabel vcNumberLabel;
    private JPanel titlePanel;
    private JLabel vcDescriptionLabel;
    private JTextArea goalText;
    private JTextArea givensText;
    public VerifierPanel verifierPanel;

    public VerificationConditionPanel(final VerifierPanel verifierPanel) {
        this.verifierPanel = verifierPanel;
    }

    public JPanel getComponent() {
        return outerMostPanel;
    }
}
