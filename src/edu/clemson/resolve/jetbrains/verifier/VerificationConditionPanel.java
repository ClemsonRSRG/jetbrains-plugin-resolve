package edu.clemson.resolve.jetbrains.verifier;

import com.intellij.ui.JBColor;

import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Created by daniel on 6/1/16.
 */
public class VerificationConditionPanel {
    private JPanel outerMostPanel;
    private JLabel vcNumberLabel;
    private JPanel titlePanel;
    private JLabel vcDescription;
    public VerifierPanel verifierPanel;

    public VerificationConditionPanel() {
       // $$$setupUI$$$();

        outerMostPanel.setBorder(new LineBorder(JBColor.LIGHT_GRAY, 1, true));
    }

    public JPanel getOuterMostComponent() {
        return outerMostPanel;
    }

    public void setVCNumber(int number) {
        this.vcNumberLabel.setText(Integer.toString(number));
    }

    public void setVCDescription(String description) {
        this.vcDescription.setText(description);
    }
}
