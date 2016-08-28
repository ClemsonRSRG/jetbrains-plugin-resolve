package edu.clemson.resolve.jetbrains.ui.ex1;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;


public class MainFrame extends JFrame {

    private JButton btn;
    private TextPanel textPanel;

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(600, 500);
        setLayout(new BorderLayout());

        btn = new JButton("Click Me!");
        textPanel = new TextPanel();

        add(btn, BorderLayout.SOUTH);
        add(textPanel, BorderLayout.CENTER);

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                textPanel.addText("Hello\n");
            }
        });
    }


}