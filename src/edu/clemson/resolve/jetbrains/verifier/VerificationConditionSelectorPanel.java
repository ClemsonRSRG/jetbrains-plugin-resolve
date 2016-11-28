package edu.clemson.resolve.jetbrains.verifier;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBScrollPane;
import edu.clemson.resolve.jetbrains.RESOLVEIcons;
import edu.clemson.resolve.proving.absyn.PExp;
import edu.clemson.resolve.vcgen.VC;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

public class VerificationConditionSelectorPanel extends JPanel {

    public static final String ID_ACTION_GROUP = "RESOLVEVerifierActionGroup";
    public static final String ID_ACTION_TOOLBAR = "RESOLVEVerifierActionToolbar";

    private static final Border CHISEL_BORDER = new ChiselBorder();
    private static final Border SEP = new ToolBarBorder();

    private static final Border CATEGORY_BORDER = new CompoundBorder(CHISEL_BORDER, new EmptyBorder(0, 0, 10, 0)); //VARY THICKNESS OF HORIZONTAL RECT HERE
    private static final Border TOOLBAR_BORDER = new CompoundBorder(SEP, new EmptyBorder(0, 0, 10, 0)); //VARY THICKNESS OF HORIZONTAL RECT HERE

    private Icon expandedIcon;
    private Icon collapsedIcon;

    private final Project project;
    private JBScrollPane scrollPane;
    public final Map<Integer, ConditionCollapsiblePanel> vcTabs = new HashMap<>();
    public final List<VerificationPreviewEditor> previewEditors = new ArrayList<>();

    public VerificationConditionSelectorPanel(@NotNull Project project,
                                              @NotNull Collection<VC> vcs) {
        super(new BorderLayout());
        JComponent selector = createVerificationConditionSelector(vcs);

        this.project = project;

        JPanel x = new JPanel();
        x.setLayout(new BorderLayout());

        this.scrollPane = new JBScrollPane(selector);
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.setBorder(BorderFactory.createEmptyBorder());
        x.add(createButtonBar());
        x.add(scrollPane);
        add(createButtonBar(), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

    }

    private JComponent createButtonBar() {
        ActionManager actionManager = ActionManager.getInstance();

        DefaultActionGroup actionGroup = new DefaultActionGroup(ID_ACTION_GROUP, false);
        /*actionGroup.add(new AnAction("Reprove", "Stop any ongoing proofs and rerun the prover on the current collection of VCs", RESOLVEIcons.RERUN) {
            @Override
            public void actionPerformed(AnActionEvent e) {
                Task proverTask = new RunProverAction.ProveBackgroundableTask(project, proverListener, vcs, activeVerifierPanel);
                ProgressManager.getInstance().run(proverTask);
            }
        });*/

        //TODO: Need an export option...
        /*actionGroup.add(new AnAction("Cancel", "Stop the prover", RESOLVEIcons.STOP) {
            @Override
            public void actionPerformed(AnActionEvent e) {
                listener.cancelled = true;
            }
        });*/
        //actionGroup.addSeparator();

        actionGroup.add(new AnAction("Collapse all VCs", "Collapse all", RESOLVEIcons.COLLAPSE) {
            @Override
            public void actionPerformed(AnActionEvent e) {
                for (ConditionCollapsiblePanel v : vcTabs.values()) {
                    v.setExpanded(false);
                }
            }
        });
        actionGroup.add(new AnAction("Expand all VCs", "Expand all", RESOLVEIcons.EXPAND) {
            @Override
            public void actionPerformed(AnActionEvent e) {
                for (ConditionCollapsiblePanel v : vcTabs.values()) {
                    v.setExpanded(true);
                }
            }
        });
        ActionToolbar toolBar = actionManager.createActionToolbar(ID_ACTION_TOOLBAR, actionGroup, true);

        JComponent buttonBar = toolBar.getComponent();
        buttonBar.setBorder(TOOLBAR_BORDER);
        return buttonBar;
    }

    protected JComponent createVerificationConditionSelector(@NotNull Collection<VC> vcs) {
        JPanel selectorPanel = new JPanel();

        GridBagLayout gridbag = new GridBagLayout();
        selectorPanel.setLayout(gridbag);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        for (VC vc : vcs) {
            JPanel categoryPanel = new JPanel();
            categoryPanel.setLayout(new BorderLayout());

            ConditionCollapsiblePanel collapsePanel =
                    new ConditionCollapsiblePanel(categoryPanel,
                            "<html><font color='#404040'><b>VC #" + vc.getNumber() + "</b></html>",
                            "click to expand and view VC information");
            vcTabs.put(vc.getNumber(), collapsePanel);
            collapsePanel.setExpanded(false);

            collapsePanel.setBorder(CATEGORY_BORDER);

            gridbag.addLayoutComponent(collapsePanel, c);
            selectorPanel.add(collapsePanel);
            c.gridy++;
            VerificationPreviewEditor preview = getVCPreview(vc);
            previewEditors.add(preview);
            categoryPanel.add(preview);
        }
        // add empty component to take up any extra room on bottom
        JPanel trailer = new JPanel();
        c.weighty = 1.0;
        gridbag.addLayoutComponent(trailer, c);
        selectorPanel.add(trailer);

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
            g2d.setColor(JBColor.LIGHT_GRAY);
            g2d.drawLine(x, y+(height), x + width, y+(height));
        }
    }

    private static class ToolBarBorder extends ChiselBorder {

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(JBColor.LIGHT_GRAY);
            g2d.drawLine(x, y+(height-6), x + width, y+(height-6));
        }
    }

    public VerificationPreviewEditor getVCPreview(VC vc) {
        String vcText = vc.toString();
        VerificationPreviewEditor preview = new VerificationPreviewEditor(project, vcText);
        preview.setBackground(JBColor.WHITE);
        //preview.addNotify();
        return preview;
    }
}
