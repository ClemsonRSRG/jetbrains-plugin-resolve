package edu.clemson.resolve.jetbrains.verifier;

import javax.swing.*;
import java.awt.*;

/**
 * Created by daniel on 6/3/16.
 */
public class main extends JFrame {

    public static void main(String[] args) throws InterruptedException {
        main m = new main();
        m.setVisible(true);
    }

    public main() {
        // setup stuff
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(100, 100, 500, 500);

        // this is the panel I will add to the frame
        JPanel innerPanel = new JPanel();
        // give it a Y axis to stuff is added top to bottom
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

        // this is a temp panel ill used to add the labels
        JPanel tPanel = new JPanel();
        // its an x axis to add stuff left to right
        tPanel.setLayout(new BoxLayout(tPanel, BoxLayout.X_AXIS));

        // create and add a label to the temp panel
        JLabel label = new JLabel("Some text");
        tPanel.add(label);
        // use our stretchy glue to fill the space to the right of the label
        tPanel.add(Box.createHorizontalGlue());

        // add the temp panel to the inner panel
        innerPanel.add(tPanel);
        // create a spacer with 0 width and 10 height
        innerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // reinitialize the temp panel for another label
        tPanel = new JPanel();
        tPanel.setLayout(new BoxLayout(tPanel, BoxLayout.X_AXIS));
        label = new JLabel("Some other text");
        // add the other label to the temp panel
        tPanel.add(label);
        // more stretchy glue
        tPanel.add(Box.createHorizontalGlue());

        // add the temp panel
        innerPanel.add(tPanel);
        // add verticle stretchy glue to fill the rest of the space below the
        // labels
        innerPanel.add(Box.createVerticalGlue());

        // add to the frame
        this.add(innerPanel);
    }

}
