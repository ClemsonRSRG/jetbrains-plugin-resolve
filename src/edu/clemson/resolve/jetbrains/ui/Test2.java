package edu.clemson.resolve.jetbrains.ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class Test2 {
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

        // Frame constraints

        // Construct button
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ButtonListener());


        frame.add(addButton);

        JScrollPane scrollPane = new JScrollPane(panel,   ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(600, 600));
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    static class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            // Clear panel
            panel.removeAll();

            // Create label and text field
            JTextField jTextField = new JTextField();
            jTextField.setSize(100, 200);
            listOfTextFields.add(jTextField);
            listOfLabels.add(new JLabel("Label " + indexer));

            // Create constraints
            GridBagConstraints textFieldConstraints = new GridBagConstraints();
            GridBagConstraints labelConstraints = new GridBagConstraints();

            // Add labels and text fields
            for(int i = 0; i < indexer; i++)
            {
                // Text field constraints
                textFieldConstraints.gridx = 1;
                textFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
                textFieldConstraints.weightx = 0.5;
                textFieldConstraints.insets = new Insets(10, 10, 10, 10);
                textFieldConstraints.gridy = i;

                // Label constraints
                labelConstraints.gridx = 0;
                labelConstraints.gridy = i;
                labelConstraints.insets = new Insets(10, 10, 10, 10);

                // Add them to panel
                panel.add(listOfLabels.get(i), labelConstraints);
                panel.add(listOfTextFields.get(i), textFieldConstraints);
            }

            // Align components top-to-bottom
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = indexer;
            c.weighty = 1;
            panel.add(new JLabel(), c);

            // Increment indexer
            indexer++;
            panel.updateUI();
        }
    }
}