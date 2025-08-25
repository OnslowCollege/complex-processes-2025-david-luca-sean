//mac os 9
//package checkout;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Application1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Check out");
        frame.setSize(900, 900);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Java 1.1 competiblity
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
        // Step 2: Create a JPanel for the header/logo
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.blue);
        // Create a label for the header
        JLabel headerLabel = new JLabel("SIGMABUSTER VIDEO");
        headerLabel.setForeground(Color.yellow); // set text color to yellow
        headerLabel.setFont(new Font("Serif", Font.BOLD, 25));
        // Add the label to the header panel
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // align text to the left
        headerPanel.add(headerLabel);

        // Step 3: add the panel to the frame
        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);
        

        //pannel that I could add iamge 
        JPanel productPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                //fill white back ground
                g.setColor(Color.white);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        productPanel.setPreferredSize(new Dimension(200, 200));

        //frame.getContentPane().add(productPanel, BorderLayout.WEST);
        JScrollPane productScroll = new JScrollPane(
            productPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        productScroll.setBorder(null);
        productScroll.getViewport().setBackground(Color.white);
        frame.getContentPane().add(productScroll, BorderLayout.WEST);

        //search bar
        headerPanel.add(new Searchbar());


        //product 1 (apple)
        JPanel applePanel = new JPanel();
        applePanel.setLayout(new BoxLayout(applePanel, BoxLayout.Y_AXIS));
        Product.addToPanelWith("apple.jpg", "Apple - $1.50", applePanel);
        

        //add the apple panel into the product panel
        productPanel.add(applePanel);
        frame.setVisible(true);

        //info panel
        JPanel infoPanel = new JPanel();
        //align items so text is left and cart is right
        infoPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        infoPanel.setOpaque(false);

        //define labels before adding them
        
        //second product
        productPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 0));
        // productPanel.removeAll(); 

        //give the row width to add the second product near the first
        productPanel.setPreferredSize(new Dimension(800, 200));
        JPanel testPanel = new JPanel();
        testPanel.setLayout(new BoxLayout(testPanel, BoxLayout.Y_AXIS));
        Product.addToPanelWith("apple.jpg", "Apple - $1.50", testPanel);
        productPanel.add(testPanel);

        //sinfoPanel.add(textLabel);
        //infoPanel.add(cartLabel);
        //productPanel.add(infoPanel);

        // update frame
        frame.validate();
        frame.repaint();
    }

}
