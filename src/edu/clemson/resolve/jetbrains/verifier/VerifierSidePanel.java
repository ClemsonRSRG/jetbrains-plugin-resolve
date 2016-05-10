package edu.clemson.resolve.jetbrains.verifier;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBList;

import java.awt.*;

import javax.swing.*;

/** holds the various expandable vc buttons containing the givens and goals */
public class VerifierSidePanel extends JPanel {

    public enum SideBarMode { TOP_LEVEL, INNER_LEVEL; }

    public static final Logger LOG = Logger.getInstance("RESOLVE VerifierPanel");

    /** box layout to contain side bar sections arranged vertically */
    private BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
    private VerifierVCSectionPanel currentSection = null;

    public VerifierSidePanel(Project project) {
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        setLayout(boxLayout);
        setPreferredSize(new Dimension(100, getPreferredSize().height));



        revalidate();

    }

    public void addSection(VerifierVCSectionPanel newSection, boolean collapse) {
        add(newSection);
        if (collapse) {
            newSection.collapse(false);
        }
        else {
            setCurrentSection(newSection);
        }
    }

    public void addSection(VerifierVCSectionPanel e) {
        addSection(e, true);
    }

    public VerifierVCSectionPanel getCurrentSection() {
        return currentSection;
    }

    public void setCurrentSection(VerifierVCSectionPanel section) {
        currentSection = section;
    }
}
