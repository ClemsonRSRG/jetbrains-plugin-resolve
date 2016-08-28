package edu.clemson.resolve.jetbrains.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by daniel on 8/28/16.
 */
public class Test extends JPanel {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("vc tests");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        VerifierPanel2 verifierpanel = new VerifierPanel2();
    }
}
