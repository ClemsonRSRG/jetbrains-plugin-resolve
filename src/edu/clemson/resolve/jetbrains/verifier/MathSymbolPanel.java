package edu.clemson.resolve.jetbrains.verifier;

import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
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

public class MathSymbolPanel extends JBPanel {

    private final JTree tree;

    public MathSymbolPanel(Project project) {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("top");
        createSections(top);
        
        this.tree = new Tree(top);
        this.tree.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                System.out.println("Focus Gained!!!!!!!");
            }

            @Override
            public void focusLost(FocusEvent e) {
                System.out.println("Focus Lost!!!!!!!");
            }
        });
        //fires when an element is selected
     /*   tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                setEditorCaretEnabledAndVisible(project);
            }
        });

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
