package edu.clemson.resolve.jetbrains.ui;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.keymap.KeymapUtil;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.JBFont;
import com.intellij.util.ui.UIUtil;
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

    public VerifierPanel2() {
        createBaseGUI();
    }

    private void createBaseGUI() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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
        this.add(emptyLabel);
    }

    //prep VerifierPanel2 for showing vc tabs, toolbars, etc.
    public void createVerifierView() {
        this.removeAll();
        activeVCSideBar = new SideBar(SideBar.SideBarMode.TOP_LEVEL, true, 300, true);
        add(activeVCSideBar);
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

    public void addVCTab(VC x) {
        VCSection s = new VCSection(activeVCSideBar, x.getName(), getInner4());
        activeVCSideBar.addSection(s);
        add(activeVCSideBar);
    }

    private static JList<String> getInner4() {
        DefaultListModel<String> model = new DefaultListModel<String>();
        model.add(0, "Bill Gates");
        model.add(1, "Steven Spielberg");
        model.add(3, "Steve Jobs");

        JList<String> list = new JList<String>();

        list.setModel(model);
        return list;
    }

    private static Font createFont(int size) {
        return JBFont.create(new Font(UIUtil.getMenuFont().getName(), Font.PLAIN, size));
    }
}
