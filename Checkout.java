// package for mac os 9
//package Checkout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Checkout {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Product Display");
        frame.setSize(900, 000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Java 1.1 competiblity
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });

        //pannel that I could add iamge im
        JPanel bluePanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                //fill white back ground
                g.setColor(Color.white);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bluePanel.setLayout(new BoxLayout(bluePanel, BoxLayout.Y_AXIS));
        bluePanel.setPreferredSize(new Dimension(200, 200));

        //image
        ImageIcon icon = new ImageIcon("apple.jpg"); 
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        //add to frame
        frame.getContentPane().add(bluePanel);
        frame.pack();
        frame.setVisible(true);
    }
}
