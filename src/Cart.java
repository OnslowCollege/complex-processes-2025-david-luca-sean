/* 
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Cart {

    public  Vector cartItemList = new Vector();
    public  JPanel cartPanelRef;
    public  JLabel cartLabelRef;
    public  JLabel totalLabelRef;
    public  Component cartGlueRef;
    public void addToCart(String name, double price, int quantity) {
    // create a new cart item and add it to the list
    final CartItem newItem = new CartItem(name, price, quantity);
    cartItemList.addElement(newItem);

    // build a row for cart
    final JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    itemPanel.setBackground(Color.lightGray);
    itemPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.black));
    itemPanel.setMaximumSize(new Dimension(200, 25));

    JLabel itemLabel = new JLabel(newItem.getDetails());
    itemLabel.setFont(new Font("Serif", Font.PLAIN, 12));
    final JButton removeButton = new JButton("X");

    itemPanel.add(itemLabel);
    itemPanel.add(removeButton);
    int insertIndex = Math.max(0, cartPanelRef.getComponentCount() - 2);
    cartPanelRef.add(itemPanel, insertIndex);

    // update labels
    cartLabelRef.setText("Shopping Cart(" + cartItemList.size() + ")");
    totalLabelRef.setText("Total: $" + calculatetotal());

    // refresh the ui
    cartPanelRef.validate();
    cartPanelRef.repaint();

    removeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        cartItemList.removeElement(newItem);
        cartPanelRef.remove(itemPanel);
        cartLabelRef.setText("Shopping Cart(" + cartItemList.size() + ")");
        totalLabelRef.setText("Total: $" + calculatetotal());
        cartPanelRef.validate();
        cartPanelRef.repaint();
      }
    });
  }

}
}
 */