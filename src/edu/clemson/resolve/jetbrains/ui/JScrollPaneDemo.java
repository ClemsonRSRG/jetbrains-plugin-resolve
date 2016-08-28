package edu.clemson.resolve.jetbrains.ui;

import com.intellij.ui.components.JBScrollPane;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 * A complete Java class to demonstrate the use of a JScrollPane.
 *
 * @author alvin alexander, devdaily.com.
 *
 */
public class JScrollPaneDemo
{
    public static void main(String[] args)
    {
        new JScrollPaneDemo();
    }

    public static class Foo extends JPanel {
        public Foo(int i) {
            add(new JLabel("Foo: " + i));
        }
    }

    public JScrollPaneDemo()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                JFrame frame = new JFrame("JScrollPane Test");

                JPanel intermediate = new JPanel();
                intermediate.setLayout(new BoxLayout(intermediate, BoxLayout.Y_AXIS));
                intermediate.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
                intermediate.removeAll();

                intermediate.setLayout(new BoxLayout(intermediate, BoxLayout.Y_AXIS));
                intermediate.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
                // create a jtextarea
                JPanel textArea = new JPanel();
                textArea.setLayout(new BoxLayout(textArea, BoxLayout.Y_AXIS));
                for (int i = 0; i < 40; i++) {
                    Foo x = new Foo(i);
                    textArea.add(x);
                }
                // add text to it; we want to make it scroll

                // create a scrollpane, givin it the textarea as a constructor argument
                JScrollPane scrollPane = new JBScrollPane(textArea);
                intermediate.add(scrollPane, BorderLayout.CENTER);

                // now add the scrollpane to the jframe's content pane, specifically
                // placing it in the center of the jframe's borderlayout
                frame.getContentPane().add(intermediate, BorderLayout.CENTER);

                // make it easy to close the application
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // set the frame size (you'll usually want to call frame.pack())
                frame.setSize(new Dimension(240, 180));

                // center the frame
                frame.setLocationRelativeTo(null);

                // make it visible to the user
                frame.setVisible(true);
            }
        });
    }
}