package edu.clemson.resolve.jetbrains.verifier;

import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBScrollPane;
import edu.clemson.resolve.proving.absyn.PExp;
import edu.clemson.resolve.vcgen.VC;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerificationConditionSelectorPanel extends JPanel {

    private static final Border CHISEL_BORDER = new ChiselBorder();
    private static final Border CATEGORY_BORDER = new CompoundBorder(CHISEL_BORDER, new EmptyBorder(0, 0, 10, 0)); //VARY THICKNESS OF HORIZONTAL RECT HERE

    private Icon expandedIcon;
    private Icon collapsedIcon;

    private final Project project;
    private JBScrollPane scrollPane;
    public final Map<Integer, ConditionCollapsiblePanel> vcTabs = new HashMap<>();
    public final List<VerificationEditorPreview> previewEditors = new ArrayList<>();

    public VerificationConditionSelectorPanel(@NotNull Project project, @NotNull List<VC> vcs) {
        super(new BorderLayout());
        JComponent selector = createVerificationConditionSelector(vcs);

        this.project = project;
        this.scrollPane = new JBScrollPane(selector);
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
    }

    protected JComponent createVerificationConditionSelector(@NotNull List<VC> vcs) {
        JPanel selectorPanel = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        selectorPanel.setLayout(gridbag);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        GridBagLayout categoryGridbag = null;
        GridBagConstraints cc = new GridBagConstraints();
        cc.gridx = cc.gridy = 0;
        cc.weightx = 1;
        cc.fill = GridBagConstraints.HORIZONTAL;

        for (VC vc : vcs) {
            JPanel categoryPanel = new JPanel();
            categoryGridbag = new GridBagLayout();
            categoryPanel.setLayout(categoryGridbag);

            ConditionCollapsiblePanel collapsePanel =
                    new ConditionCollapsiblePanel(categoryPanel, ConditionCollapsiblePanel.Orientation.VERTICAL,
                            "<html><font color='#404040'><b>VC #" + vc.getNumber() + "</b></html>",
                            "click to expand and view VC information");
            vcTabs.put(vc.getNumber(), collapsePanel);
            collapsePanel.setExpanded(false);

            collapsePanel.setBorder(CATEGORY_BORDER);

            gridbag.addLayoutComponent(collapsePanel, c);
            selectorPanel.add(collapsePanel);
            c.gridy++;
            VerificationEditorPreview preview = getVCPreview(vc);
            previewEditors.add(preview);
            categoryGridbag.addLayoutComponent(preview, cc);
            cc.gridy++;
            categoryPanel.add(preview);
        }
        // add empty component to take up any extra room on bottom
        JPanel trailer = new JPanel();
        c.weighty = 1.0;
        gridbag.addLayoutComponent(trailer, c);
        selectorPanel.add(trailer);

        //applyDefaults();
        return selectorPanel;
    }

    private static class ChiselBorder implements Border {
        private Insets insets = new Insets(1, 0, 1, 0);

        public ChiselBorder() {
        }

        public Insets getBorderInsets(Component c) {
            return insets;
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setStroke(new BasicStroke(3));
            //g2d.setColor(JBColor.LIGHT_GRAY);
            //g2d.drawLine(x, y, x + width, y);
            g2d.setColor(JBColor.LIGHT_GRAY);
            g2d.drawLine(x, y + height - 1, x + width, y + height - 1);
        }
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
        preview.addNotify();
        return preview;
    }
}
