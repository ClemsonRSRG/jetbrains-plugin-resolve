package edu.clemson.resolve.jetbrains.verifier;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.ui.JBColor;
import com.intellij.ui.JBDefaultTreeCellRenderer;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.treeStructure.Tree;
import com.sun.istack.internal.NotNull;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Displays a hierarchy of useful math symbols and the corresponding live template commands to insert them into
 * the active editor.
 */
public class MathSymbolPanel extends JBPanel {

    private final JTree tree;

    public MathSymbolPanel(Project project) {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("top");
        createSections(top);
        this.tree = new Tree(top);
        JBDefaultTreeCellRenderer renderer = new JBDefaultTreeCellRenderer(tree);
        renderer.setLeafIcon(null);
        renderer.setClosedIcon(AllIcons.Nodes.NewFolder);
        renderer.setOpenIcon(AllIcons.Nodes.NewFolder);
        tree.setCellRenderer(renderer);

        /*this.tree.addFocusListener(new FocusListener() {
            private RangeHighlighter activeHighlighter = null;

            @Override
            public void focusGained(FocusEvent e) {

                if (editor instanceof EditorEx) {
                    TextAttributes y = new TextAttributes();
                    y.setBackgroundColor(JBColor.GREEN);
                    int expectedOffset = ((EditorEx) editor).getCaretModel().getOffset();

                    activeHighlighter = ((EditorEx) editor).getMarkupModel().addRangeHighlighter(
                            expectedOffset-1, expectedOffset, HighlighterLayer.CARET_ROW, y,
                            HighlighterTargetArea.EXACT_RANGE);
                    activeHighlighter.setGreedyToLeft(false);
                    activeHighlighter.setGreedyToRight(true);

                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (activeHighlighter != null && editor != null) {
                    editor.getMarkupModel().removeAllHighlighters();
                    activeHighlighter = null;
                }
            }
        });*/

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

               // e.getNewLeadSelectionPath().getLastPathComponent().
                SymbolInfo s = (SymbolInfo)node.getUserObject();

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        document.insertString(tailOffset, s.symbol);
                    }
                };
                WriteCommandAction.runWriteCommandAction(project, runnable);

                //document.insertString(tailOffset, s.symbol);
                editor.getCaretModel().moveToOffset(tailOffset + 1);

            }
        });
/*
        //fires when a section is opened or closed.
        tree.addTreeExpansionListener(new TreeExpansionListener() {
            @Override
            public void treeExpanded(TreeExpansionEvent event) {
                setEditorCaretEnabledAndVisible(project);
            }

            @Override
            public void treeCollapsed(TreeExpansionEvent event) {
                setEditorCaretEnabledAndVisible(project);
            }
        });*/

        //Create a tree that allows one selection at a time.
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        this.tree.setRootVisible(false); //give things the appearance of multiple levels.

        JScrollPane treeView = new JBScrollPane(tree);
        setLayout(new BorderLayout());
        add(treeView);
    }

    private void setEditorCaretEnabledAndVisible(Project p) {
        Editor editor = FileEditorManager.getInstance(p).getSelectedTextEditor();
        if (editor instanceof EditorEx) {
            //int x = ((EditorEx)editor).getExpectedCaretOffset();
            TextAttributes y = new TextAttributes();
            y.setBackgroundColor(JBColor.GREEN);
            ((EditorEx) editor).getMarkupModel().addLineHighlighter(
                    3, HighlighterLayer.ELEMENT_UNDER_CARET, y);
        }

    }

    public void createSections(@NotNull DefaultMutableTreeNode e) {
        addArrowsSection(e);
        addGreekAlphabetSection(e);
        //TODO
    }

    private void addArrowsSection(@NotNull DefaultMutableTreeNode e) {
        DefaultMutableTreeNode category = new DefaultMutableTreeNode("Arrows");
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⟶", "longrightarrow")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("⟹", "Longrightarrow")));
        e.add(category);
    }

    private void addGreekAlphabetSection(@NotNull DefaultMutableTreeNode e) {
        DefaultMutableTreeNode category = new DefaultMutableTreeNode("Greek alphabet");
        category.add(new DefaultMutableTreeNode(new SymbolInfo("α", "alpha")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("β", "beta")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("γ", "gamma")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("δ", "delta")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("ε", "epsilon")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("ζ", "zeta")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("η", "eta")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("θ", "theta")));
        category.add(new DefaultMutableTreeNode(new SymbolInfo("ι", "iota")));
        e.add(category);
    }

    private static class SymbolInfo {
        private final String symbol, command;
        private final boolean activated;

        SymbolInfo(@NotNull String s, @NotNull String command) {
            this.symbol = s;
            this.command = command;
            this.activated = true;
        }

        @Override
        public String toString() {
            return symbol + "   " + command;
        }
    }
}
