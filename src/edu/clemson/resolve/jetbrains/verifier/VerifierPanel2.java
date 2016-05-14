package edu.clemson.resolve.jetbrains.verifier;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/** holds the various expandable vc buttons containing the givens and goals */
public class VerifierPanel2 extends JPanel {

    public static final Logger LOG = Logger.getInstance("RESOLVE VerifierPanel");

    /** box layout to contain side bar sections arranged vertically */
    private BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
    private VCSectionPanel currentSection = null;
    private final List<VCSectionPanel> sections = new ArrayList<>();
    boolean animate = false;

    public VerifierPanel2(Project project, boolean animate) {
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.animate = animate;
        setLayout(boxLayout);

        setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height));
        revalidate();
    }

    public void addVCSection(VCSectionPanel newSection, boolean collapsed) {
        add(newSection);
        if (collapsed) {
            newSection.collapse(false);
        }
        else {
            setCurrentSection(newSection);
        }
    }

    public void removeAll() {
        for (VCSectionPanel section : sections) {
            this.remove(section);
        }
    }

    public void addVCSection(VCSectionPanel e) {
        addVCSection(e, true);
    }

    public VCSectionPanel getCurrentSection() {
        return currentSection;
    }

    public void setCurrentSection(VCSectionPanel section) {
        currentSection = section;
    }

    public static class VerifierSidePanelAnimation extends Animation {

        private VCSectionPanel vcPanel;

        public VerifierSidePanelAnimation(VCSectionPanel vcPanel, int durationMs) {
            super(durationMs);
            this.vcPanel = vcPanel;
        }

        @Override
        public void starting () {
            vcPanel.contentPane.setVisible(true);
        }

        @Override
        protected void render(int value) {
            vcPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, value));
            vcPanel.contentPane.setVisible(true);
            vcPanel.revalidate();
        }

        @Override
        public void stopped () {
            vcPanel.contentPane.setVisible(true);
            vcPanel.revalidate();
            //sideBarSection.printDimensions();
        }
    }

}
