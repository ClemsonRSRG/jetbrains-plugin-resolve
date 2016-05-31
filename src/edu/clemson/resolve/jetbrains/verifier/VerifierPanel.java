package edu.clemson.resolve.jetbrains.verifier;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/** Pairs a vc display panel with (optional) derivation info panel */
public class VerifierPanel extends JPanel {

    public static final Logger LOG = Logger.getInstance("RESOLVE VerifierPanel");

    public VerifierPanel(Project project) {
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height));
        revalidate();
    }

}
