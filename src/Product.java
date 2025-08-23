import javax.swing.*;
import java.awt.*;

public class Product {

    public static void addToPanelWith(String imageFile, String label, JPanel productPanel) {
        // image
        ImageIcon icon = new ImageIcon(imageFile); 
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        productPanel.add(imageLabel);

        //text
        JLabel textLabel = new JLabel(label);
        textLabel.setFont(new Font("Serif", Font.PLAIN, 16));

        //scale cart to 10% of the product 
        //int cartWidth = icon.getIconWidth() / 10;
        //int cartHeight = icon.getIconHeight() / 10;
        //infoPanel.add(cartLabel);
        ImageIcon cartIcon = new ImageIcon("SHOPPING-CART-SMALL.JPG");
        JLabel cartLabel = new JLabel(cartIcon);


        //JLabel cartLabel = new JLabel(new ImageIcon(cartImg));

        //info panel
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        infoPanel.setOpaque(false);
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT); 

        infoPanel.add(textLabel);
        infoPanel.add(cartLabel);

        productPanel.add(infoPanel);
    }
}
