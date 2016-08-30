package edu.clemson.resolve.jetbrains.ui;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;
import java.awt.*;

public class JCheckBoxCustomIcon extends JFrame {

    public JCheckBoxCustomIcon() {
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JCheckBox checkBox = new JCheckBox("Check me!");
        checkBox.setSelected(false);
        Icon  x =  new ImageIcon("precis.png");

        // Set default icon for checkbox
        checkBox.setIcon(x);
        checkBox.setSelectedIcon(new ImageIcon("record_field.png"));
        // Set selected icon when checkbox state is selected
        //  checkBox.setSelectedIcon(new ImageIcon("edu/clemson/resolve/jetbrains/ui/record_field.png"));
        // Set disabled icon for checkbox
        //  checkBox.setDisabledIcon(new ImageIcon("edu/clemson/resolve/jetbrains/ui/type_repr.png"));
        // Set disabled-selected icon for checkbox
     /*   checkBox.setDisabledSelectedIcon(new ImageIcon("disabledSelectedIcon.png"));
        // Set checkbox icon when checkbox is pressed
        checkBox.setPressedIcon(new ImageIcon("pressedIcon.png"));
        // Set icon when a mouse is over the checkbox
        checkBox.setRolloverIcon(new ImageIcon("rolloverIcon.png"));
        // Set icon when a mouse is over a selected checkbox
        checkBox.setRolloverSelectedIcon(new ImageIcon("rolloverSelectedIcon.png"));*/

        getContentPane().add(checkBox);
    }

    public static void main(String[] args) {
        new JCheckBoxCustomIcon().setVisible(true);
    }
}