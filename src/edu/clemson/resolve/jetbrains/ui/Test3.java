package edu.clemson.resolve.jetbrains.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Test3 {
    // Field members
    static JPanel panel = new JPanel();
    static Integer indexer = 1;
    static List<JLabel> listOfLabels = new ArrayList<JLabel>();
    static List<JTextField> listOfTextFields = new ArrayList<JTextField>();

    public static void main(String[] args)
    {
        // Construct frame
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(990, 990));
        frame.setTitle("My Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Construct button
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ButtonListener());

        // Add button to frame
        frame.add(addButton);

        // Construct panel
        panel.setLayout(new GridBagLayout());
        panel.setBorder(LineBorder.createBlackLineBorder());

        JScrollPane scrollPane = new JScrollPane(panel,   ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Add panel to frame
        frame.add(scrollPane);

        // Pack frame
        //frame.pack();

        // Make frame visible
        frame.setVisible(true);
    }

    static class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            // Clear panel
           // panel.removeAll();
           // panel.add(new JLabel("FOO"));
           // panel.updateUI();
        }
    }
}