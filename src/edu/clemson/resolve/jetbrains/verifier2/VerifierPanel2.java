package edu.clemson.resolve.jetbrains.verifier2;

import com.intellij.application.options.colors.ColorAndFontSettingsListener;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.keymap.KeymapUtil;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollBar;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.ui.JBFont;
import com.intellij.util.ui.UIUtil;
import edu.clemson.resolve.jetbrains.ui.VerificationSelectorPanel;
import edu.clemson.resolve.jetbrains.verifier.VerificationEditorPreview;
import edu.clemson.resolve.proving.absyn.PExp;
import edu.clemson.resolve.vcgen.VC;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VerifierPanel2 extends JPanel {

    public List<VCSection> sections = new ArrayList<>();
    public static final Logger LOG = Logger.getInstance("RESOLVE VerifierPanel");

    public SideBar activeVCSideBar = null;
    private final Project project;
    public VerificationSelectorPanel vcSelectorPanel = null;

    public VerifierPanel2(Project project) {
        this.project = project;
        createBaseGUI();

    }

    public List<VerificationEditorPreview> getActivePreviewEditors() {
        List<VerificationEditorPreview> result = new ArrayList<>();
        if (activeVCSideBar != null) {
            for (VCSection s : activeVCSideBar.getSections()) {
                result.add(s.previewEditor);
            }
        }
        return result;
    }

    private void createBaseGUI() {
        //TODO: This should be drawn on the "activeVCsSidebar" JPanel
      /*  this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        String vcShortcut = KeymapUtil.getFirstKeyboardShortcutText("resolve.GenVCs");
        JBLabel emptyLabel = new JBLabel(
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
                "</html>", JBLabel.CENTER);
        emptyLabel.setFont(createFont(12));
        JPanel dummypanel = new JBPanel();
        dummypanel.setOpaque(false);
        this.add(Box.createRigidArea(new Dimension(0, 50)));
        this.add(emptyLabel);*/
    }

    public static class SideStuff extends JPanel {
        public SideStuff() {
            setBorder(BorderFactory.createLineBorder(JBColor.GRAY));
        }
    }

    //DemoSelectorPanel swingset3
    //prep VerifierPanel2 for showing vc tabs, toolbars, etc.
    public void createVerifierView() {
        //this.removeAll();
        //activeVCSideBar = new SideBar(SideBar.SideBarMode.TOP_LEVEL, true, 300, false);
        //activeVCSideBar.setLayout(new BoxLayout(activeVCSideBar, BoxLayout.Y_AXIS));
        SideStuff s = new SideStuff();
        s.setLayout(new BoxLayout(s, BoxLayout.Y_AXIS));
        for (int i = 0; i < 20; i++) {
            SideStuff vc = new SideStuff();
            vc.add(new JBLabel("VC: " + i));
            s.add(vc);
            //s.add(new JLabel("asdjansfkljasnflkasjnflaskjfbaslfbalskfbalsfjbaslfjbas;fjbalskfjbsalgjbaFoo: " + i));
        }
        JBScrollPane scrollPane = new JBScrollPane(s,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        //revalidate();
    }

    public void createVerifierView2(List<VC> vcs) {
        vcSelectorPanel = new VerificationSelectorPanel(project, vcs);
        add(vcSelectorPanel, BorderLayout.CENTER);
    }

    public void revertToBaseGUI() {
        if (activeVCSideBar != null) {
            //we're going back to the default screen, so if there were active editors (before say the user messed
            //with the doc) remove em' here.
            for (VCSection s : activeVCSideBar.getSections()) {
                s.previewEditor.removeNotify();
            }
        }
        this.removeAll();
        this.activeVCSideBar = null;
        createBaseGUI();
        revalidate();
    }
    int i =0;

    public static class Foo extends JPanel {
        public Foo(int i) {
            setPreferredSize(new Dimension(250, 30));
            setBorder(BorderFactory.createBevelBorder(1));
            add(new JLabel("FOOZ: " + i));
        }
    }
    public void addVCTab(VC x) {
        activeVCSideBar.add(new Foo(i));

        i++;
       // VCSection s = new VCSection(activeVCSideBar, x.getName(), getVCPreview(x));
       // activeVCSideBar.addSection(s);
       // add(activeVCSideBar);
    }

    public VerificationEditorPreview getVCPreview(VC vc) {
        List<PExp> antecedents = vc.getAntecedent().splitIntoConjuncts();
        String vcText = "";
        for (int i = 0; i < antecedents.size(); i++) {
            vcText += i + 1 + ". " + antecedents.get(i) + "\n";
        }
        vcText += "⊢\n";
        vcText += vc.getConsequent();
        VerificationEditorPreview preview = new VerificationEditorPreview(project, vcText);
        preview.setBackground(JBColor.WHITE);
        preview.addNotify();    //TODODO

        return preview;
    }

    private static Font createFont(int size) {
        return JBFont.create(new Font(UIUtil.getMenuFont().getName(), Font.PLAIN, size));
    }
}
