package edu.clemson.resolve.jetbrains.verifier;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.colors.EditorColorsListener;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.ui.JBColor;
import com.intellij.ui.JBDefaultTreeCellRenderer;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.ui.treeStructure.filtered.FilteringTreeStructure;
import com.intellij.util.ui.JBFont;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import com.sun.istack.internal.NotNull;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/**
 * Displays a hierarchy of useful math symbols and the corresponding live template commands to insert them into
 * the active editor.
 */
public class MathSymbolPanel extends JBPanel {

    private final Tree tree;

    public MathSymbolPanel(Project project) {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("top");
        this.tree = new Tree(top);
        JBDefaultTreeCellRenderer renderer = new JBDefaultTreeCellRenderer(tree);
        renderer.setLeafIcon(null);
        renderer.setClosedIcon(AllIcons.Nodes.NewFolder);
        renderer.setOpenIcon(AllIcons.Nodes.NewFolder);
        renderer.setFont(JBFont.create(new Font(EditorColorsManager.getInstance()
                .getGlobalScheme().getEditorFontName(), Font.PLAIN, 12)));
        tree.setCellRenderer(renderer);
        createSections(top);

        TreeFilteringModel filteringModel = new TreeFilteringModel(new DefaultTreeModel(top));
        this.tree.setModel(filteringModel);

        //allows us to change font dynamically in the symbol browser
        //(it matters cause the symbols are utf8 and various fonts render them differently)
        ApplicationManager.getApplication().getMessageBus().connect()
                .subscribe(EditorColorsManager.TOPIC, new EditorColorsListener() {
            @Override
            public void globalSchemeChange(EditorColorsScheme scheme) {
                TreeCellRenderer r = tree.getCellRenderer();
                if (!(r instanceof JBDefaultTreeCellRenderer)) return;
                JBDefaultTreeCellRenderer rAsJBCellRenderer = (JBDefaultTreeCellRenderer)r;
                rAsJBCellRenderer.setFont(JBFont.create(new Font(EditorColorsManager.getInstance()
                        .getGlobalScheme().getEditorFontName(), Font.PLAIN, 12)));
                rAsJBCellRenderer.revalidate();
            }
        });

        //fires when an element is selected
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                if (editor == null) return;
                int tailOffset = editor.getCaretModel().getOffset();
                Document document = editor.getDocument();
                PsiDocumentManager.getInstance(project).commitDocument(document);
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (node == null) return;
                if (!(node.getUserObject() instanceof SymbolInfo)) return;
                SymbolInfo s = (SymbolInfo)node.getUserObject();

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        document.insertString(tailOffset, s.symbol);
                    }
                };
                WriteCommandAction.runWriteCommandAction(project, runnable);
                editor.getCaretModel().moveToOffset(tailOffset + 1);
                tree.clearSelection();
            }
        });

        //Create a tree that allows multiple (non contiguous) expansions at a time
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        this.tree.setRootVisible(false);
        JScrollPane treeView = new JBScrollPane(tree);
        treeView.setBorder(BorderFactory.createEmptyBorder());
        setLayout(new BorderLayout());

        JTextField filter = createFilterField();
        filter.getDocument().addDocumentListener(createDocumentListener(tree, filter));

        add(filter, BorderLayout.NORTH);
        add(treeView);
    }

    private JTextField createFilterField() {
        JTextField filterField = new JBTextField("filter");
        filterField.setFont(UIUtil.getTreeFont());

        filterField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (filterField.getText().equals("filter")) filterField.setText("");
            }

            public void focusLost(FocusEvent e) {
            }
        });
        filterField.setBorder(new RoundedCornerBorder());
        return filterField;
    }

    private static javax.swing.event.DocumentListener createDocumentListener(final JTree tree,
                                                                             final JTextField filter) {
        return new javax.swing.event.DocumentListener() {

            @Override
            public void insertUpdate(final javax.swing.event.DocumentEvent e) {
                applyFilter();
            }

            @Override
            public void removeUpdate(final javax.swing.event.DocumentEvent e) {
                applyFilter();
            }

            @Override
            public void changedUpdate(final javax.swing.event.DocumentEvent e) {
                applyFilter();
            }

            void applyFilter() {
                TreeFilteringModel filteringModel = (TreeFilteringModel) tree.getModel();
                filteringModel.setFilter(filter.getText());

                DefaultTreeModel treeModel = (DefaultTreeModel) filteringModel.getTreeModel();
                treeModel.reload();

                for (int i = 0; i < tree.getRowCount(); i++) {
                    tree.expandRow(i);
                }
            }
        };
    }

    private void createSections(@NotNull DefaultMutableTreeNode e) {
        addArrowsSection(e);
        addGreekSection(e);
        addLettersSection(e);
        addLogicSection(e);
        addPunctuationSection(e);
        addOperatorSection(e);
        addRelationSection(e);
        //TODO: Prune any not currently accepted by resolve's grammar. This is enough for now.
    }

    private void addArrowsSection(@NotNull DefaultMutableTreeNode e) {
        DefaultMutableTreeNode category = new DefaultMutableTreeNode("Arrows");

        category.add(new DefaultMutableTreeNode(new SymbolInfo("←", "leftarrow")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⇐", "Leftarrow")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⟵", "longleftarrow")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⟸", "Longleftarrow")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("→", "rightarrow")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⇒", "Rightarrow")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⟶", "longrightarrow")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⟹", "Longrightarrow")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("↔", "leftrightarrow")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⇔", "Leftrightarrow")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⟷", "longleftrightarrow")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⟺", "Longleftrightarrow")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("↩", "hookleftarrow")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("↪", "hookrightarrow")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("↽", "leftharpoondown")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⇁", "rightharpoondown")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("↼", "leftharpoonup")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⇀", "rightharpoonup")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⇌", "leftrightharpoons")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("↝", "leadsto")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⇃", "downharpoonleft")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⇂", "downharpoonright")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("↿", "upharpoonleft")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("↾", "upharpoonright")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("↑", "up")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⇑", "Up")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("↓", "down")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⇓", "Down")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("↕", "updown")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⇕", "Updown")));
        e.add(category);
    }

    private void addGreekSection(@NotNull DefaultMutableTreeNode e) {
        DefaultMutableTreeNode category = new DefaultMutableTreeNode("Greek");

        //lowercase
        category.add(new DefaultMutableTreeNode(new SymbolInfo("α", "alpha")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("β", "beta")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("γ", "gamma")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("δ", "delta")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("ε", "epsilon")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("ζ", "zeta")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("η", "eta")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("θ", "theta")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("ι", "iota")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("κ", "kappa")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("λ", "lambda")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("μ", "mu")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("ν", "nu")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("ξ", "xi")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("ο", "omnicron")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("π", "pi")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("ρ", "rho")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("σ", "sigma")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("τ", "tau")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("υ", "upsilon")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("φ", "phi")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("χ", "chi")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("ψ", "psi")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("ω", "omega")));

        //capitals
        category.add(new DefaultMutableTreeNode(new SymbolInfo("Γ", "Gamma")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("Δ", "Delta")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("Λ", "Lambda")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("Ξ", "Xi")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("Π", "Pi")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("Σ", "Sigma")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("Υ", "Upsilon")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("Φ", "Phi")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("Ψ", "Psi")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("Ω", "Omega")));
        e.add(category);
    }

    private void addLettersSection(@NotNull DefaultMutableTreeNode e) {
        DefaultMutableTreeNode category = new DefaultMutableTreeNode("Letters");
        category.add(new DefaultMutableTreeNode(new SymbolInfo("ℂ", "Complex")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("ℕ", "Natual")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("ℚ", "Rational")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("ℝ", "Real")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("ℤ", "Integer")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("℘", "Powerset")));
        e.add(category);
    }

    private void addLogicSection(@NotNull DefaultMutableTreeNode e) {
        DefaultMutableTreeNode category = new DefaultMutableTreeNode("Logic");

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊥", "bottom")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊤", "top")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("∧", "and")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⋀", "And")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("∨", "or")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⋁", "Or")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("∀", "forall")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("∃", "exists")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("¬", "not")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("□", "box")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("◇", "diamond")));
        e.add(category);
    }

    private void addPunctuationSection(@NotNull DefaultMutableTreeNode e) {
        DefaultMutableTreeNode category = new DefaultMutableTreeNode("Punctuation");

        category.add(new DefaultMutableTreeNode(new SymbolInfo("∷", "coloncolon")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⟨", "langle")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⟩", "rangle")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⌈", "lceil")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⌉", "rceil")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⌊", "lfloor")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⌋", "rfloor")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⎝", "lcup")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⎠", "rcup")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⎛", "lcap")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⎞", "rcap")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⦇", "lparr")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⦈", "rparr")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⟦", "lbrakk")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⟧", "rbrakk")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("«", "guillemotleft")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("»", "guillemotright")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⎥", "mid")));
        e.add(category);
    }

    private void addOperatorSection(@NotNull DefaultMutableTreeNode e) {
        DefaultMutableTreeNode category = new DefaultMutableTreeNode("Operators");

        category.add(new DefaultMutableTreeNode(new SymbolInfo("∩", "cap")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⋂", "Cap")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("∪", "cup")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⋃", "Cup")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊔", "sqcup")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⨆", "Sqcup")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊓", "sqcap")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⨅", "Sqcap")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("∝", "propto")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊎", "cupplus")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⨄", "Cupplus")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("±", "plusminus")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("∓", "minusplus")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("×", "times")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("÷", "div")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⋅", "cdot")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⋆", "star")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("∙", "bullet")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("∘", "circ")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊕", "oplus")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⨁", "Oplus")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊗", "otimes")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⨂", "Otimes")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊙", "odot")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⨀", "Odot")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊖", "ominus")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊘", "oslash")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⟕", "lojoin")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⟖", "rojoin")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⟗", "fojoin")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("∑", "Sum")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("∏", "Prod")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⨿", "coprod")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("∐", "Coprod")));
        e.add(category);
    }

    private void addRelationSection(@NotNull DefaultMutableTreeNode e) {
        DefaultMutableTreeNode category = new DefaultMutableTreeNode("Relations");

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊢", "turnstile")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊨", "Turnstile")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊩", "tturnstile")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊫", "TTurnstile")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊣", "stileturn")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("√", "surd")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("≤", "le")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("≥", "ge")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("≪", "lless")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("≫", "ggreater")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("≲", "lesssim")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("≳", "greatersim")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⪅", "lessapprox")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⪆", "greaterapprox")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("∈", "in")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("∉", "notin")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊂", "subset")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊃", "supset")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊆", "subseteq")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊇", "supseteq")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊏", "sqsubset")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊐", "sqsupset")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊑", "sqsubseteq")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊒", "sqsupseteq")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("≠", "noteq")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("∼", "sim")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("≐", "doteq")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("≃", "simeq")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("≈", "approx")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("≍", "asymp")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("≅", "cong")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("≡", "equiv")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("≼", "preceq")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("≽", "succeq")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊲", "lhd")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊳", "rhd")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊴", "unlhd")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⊵", "unrhd")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("◃", "triangleleft")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("▹", "triangleright")));

        category.add(new DefaultMutableTreeNode(new SymbolInfo("△", "triangle")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("≜", "triangleq")));
        e.add(category);
    }

    /** A class for grouping all math glyph related information. */
    private static class SymbolInfo {
        private final String symbol, command;

        SymbolInfo(@NotNull String s, @NotNull String command) {
            this.symbol = s;
            this.command = command;
        }

        @Override
        public String toString() {
            return symbol + "   " + command;
        }
    }

    /**
     * An extension of {@link AbstractBorder} for the symbol/glyph search box embedded
     * at the top of the panel
     */
    static class RoundedCornerBorder extends AbstractBorder {
        @Override public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int r = height - 1;
            RoundRectangle2D round = new RoundRectangle2D.Float(x, y, width - 1, height - 1, r, r);
            Container parent = c.getParent();
            if (parent != null) {
                g2.setColor(JBColor.WHITE);
                Area corner = new Area(new Rectangle2D.Float(x, y, width, height));
                corner.subtract(new Area(round));
                g2.fill(corner);
            }
            g2.setColor(JBColor.GRAY);
            g2.draw(round);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return JBUI.insets(4, 8);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = 8;
            insets.top = insets.bottom = 4;
            return insets;
        }
    }
}
