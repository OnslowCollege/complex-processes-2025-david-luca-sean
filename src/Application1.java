//mac os 9
//package Checkout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.Hashtable;
import java.sql.*;
//import java.util.Map;
//import java.util.Set;
//import java.util.ArrayList;
import java.util.Enumeration;
//import java.util.HashMap;

public class Application1 {
  private static final Hashtable saleHistory = new Hashtable();
  public static JPanel cartPanelRef;
  public static JLabel cartLabelRef;
  public static JLabel totalLabelRef;
  public static Component cartGlueRef;

  public static int orderNumber = 1;

  // ArrayList to hold cart items
  public static Vector cartItemList = new Vector();
  private static int itemCount = cartItemList.size(); // item counter for shopping cart

  public static void main(String[] args) {

    // Hashmap to store sale history
    // Map saleHistory = new HashMap();
    // final Hashtable saleHistory = new Hashtable();
    /*
     * SOURCE JFRAME
     * The main window of sigmabuster video
     * Will contain a header, shopping cart and product display area
     */
    // Step 1: Create a new JFrame (window)
    JFrame frame = new JFrame("SIGMABUSTER VIDEO .inc");
    frame.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent e) {
        System.exit(0);
      }
    });
    frame.setSize(1000, 700);
    // frame is divded into NORTH, SOUTH, EAST, WEST and CENTER
    frame.getContentPane().setLayout(new BorderLayout());
    // centre the frame on the screen
    // frame.setLocationRelativeTo(null);
    frame.setResizable(false);

    // Step 2: Create a JPanel for the header/logo
    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setBackground(Color.blue);
    // Create a label for the header
    JLabel headerLabel = new JLabel(" SIGMABUSTER VIDEO");
    headerLabel.setForeground(Color.yellow); // set text color to yellow
    headerLabel.setFont(new Font("Serif", Font.BOLD, 35));
    headerPanel.add(new Searchbar(), BorderLayout.EAST);
    // Add the label to the header panel
    frame.getContentPane().add(headerPanel, BorderLayout.NORTH);
    // align the text to the left
    // headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    headerPanel.add(headerLabel);

    // add the panel to the frame
    // frame.getContentPane().add(headerPanel, BorderLayout.NORTH);

    // panel that I could add iamge
    JPanel productPanel = new JPanel() {

    };

    productPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

    // frame.getContentPane().add(productPanel, BorderLayout.WEST);
    JScrollPane productScroll = new JScrollPane(
        productPanel,
        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    productScroll.setBorder(null);
    productScroll.getViewport().setBackground(Color.white);
    frame.getContentPane().add(productScroll, BorderLayout.WEST);

    // search bar
    // headerPanel.add(new Searchbar());

    // headerPanel.add(headerLabel, BorderLayout.WEST);
    // headerPanel.add(new Searchbar(), BorderLayout.EAST);
    // track how much time the customer puchase
    int purchasenumber = 0;

    // adding products to the product panel
    Vector products = productReader.readProductsFromFile("classFiles/products.txt");
    for (int i = 0; i < products.size(); i++) {
      ProductItem p = (ProductItem) products.elementAt(i);
      JPanel itemPanel = p.createProductPanel(p);
      productPanel.add(itemPanel);
    }

    // headerPanel.add(new Searchbar());

    productPanel.revalidate();
    productPanel.repaint();
    frame.validate();
    frame.setVisible(true);

    final JPanel cartPanel = new JPanel();
    cartPanel.setBackground(Color.white);
    // create a border around the panel
    cartPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 8));
    // set preferred size for the shopping cart panel
    cartPanel.setPreferredSize(new Dimension(200, 250));

    // setting the layout
    cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
    // Create a label for the shopping cart
    final JLabel cartLabel = new JLabel("Shopping Cart(" + itemCount + ")");
    cartLabel.setFont(new Font("Serif", Font.BOLD, 15));
    // set the label to be centered
    cartLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    cartPanel.add(cartLabel);

    Application1.cartPanelRef = cartPanel;
    Application1.cartLabelRef = cartLabel;

    // test items in the cart
    // CartItem item1 = new CartItem("placeholder 1", 9.99, 1);
    // CartItem item2 = new CartItem("placeholder 2", 19.99, 2);

    // itemCount = cartItemList.size();
    // cartLabel.setText("Shopping Cart(" + itemCount + ")");
    // cartItemList.addElement(item1);
    // cartItemList.addElement(item2);

    itemCount = cartItemList.size();
    cartLabel.setText("Shopping Cart(" + itemCount + ")");

    // add items to the cart panel

    // adding items into the cart panel
    // for every item in the cart, add it to the cart panel
    for (int i = 0; i < cartItemList.size(); i++) {
      CartItem item = (CartItem) cartItemList.elementAt(i);
      final CartItem currentItem = item; // final variable for the action listener

      final JPanel itemPanel = new JPanel();
      itemPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
      itemPanel.setBackground(Color.lightGray);
      itemPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.black));
      itemPanel.setMaximumSize(new Dimension(200, 25));

      JLabel itemLabel = new JLabel(item.getDetails());
      itemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      itemLabel.setFont(new Font("Serif", Font.PLAIN, 12));
      // create a button to remove the item from the cart
      JButton removeButton = new JButton("X");

      itemPanel.add(itemLabel);
      itemPanel.add(removeButton);
      // add the item panel to the cart panel
      cartPanel.add(itemPanel);

      removeButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          // remove the item from the cart
          cartItemList.removeElement(currentItem);
          cartPanel.remove(itemPanel);
          cartPanel.validate();
          cartPanel.repaint();
          // itemCount--; // decrement the item count
          itemCount = cartItemList.size();
          cartLabel.setText("Shopping Cart(" + itemCount + ")");
        }
      });

    }

    // create a label to show total price
    final JLabel totalLabel = new JLabel("Total: $" + calculatetotal());
    totalLabel.setFont(new Font("Serif", Font.BOLD, 15));
    totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    // create a button to go to checkout
    JButton checkoutButton = new JButton("Go to Checkout ->");

    checkoutButton.setFont(new Font("Serif", Font.BOLD, 20));
    // set the button to be centered
    checkoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    // move the button to the bottom of the panel
    cartPanel.add(Box.createVerticalGlue());
    cartPanel.add(totalLabel);
    Application1.totalLabelRef = totalLabel;
    cartPanel.add(checkoutButton);
    checkoutButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        /*
         * NEW CHECKOUT FRAME
         * When the checkout button is clicked, a new window will open
         * This window will show the items in the cart and the total price
         * There will be a button to confirm the purchase
         * There will also be a history of past purchases on the right side
         */
        // opens up a new window for checkout
        final JFrame checkoutFrame = new JFrame("Checkout");
        checkoutFrame.addWindowListener(new java.awt.event.WindowAdapter() {
          public void windowClosing(java.awt.event.WindowEvent e) {
            checkoutFrame.dispose();
          }
        });
        checkoutFrame.setSize(560, 400);
        checkoutFrame.getContentPane().setLayout(new BorderLayout());
        // size the check out screen
        java.awt.Dimension scr = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int w = (scr.width * 5) / 10;
        int h = (scr.height * 5) / 10;
        checkoutFrame.setSize(w, h);
        // purchase history
        /*
         * Vector saleHistoryItemList = new Vector();
         * for (int i = 0; i < cartItemList.size(); i = i + 1) {
         * saleHistoryItemList.addElement(cartItemList.elementAt(i));
         * }
         * String orderKey = "Order " + orderNumber + " - " + totalLabel.getText();
         * saleHistory.put(orderKey, saleHistoryItemList);
         * orderNumber = orderNumber + 1;
         */
        /* CHECKOUT HEADER */
        JPanel checkoutHeaderPanel = new JPanel();
        checkoutHeaderPanel.setBackground(Color.blue);
        // Create a label for the header
        JLabel checkoutHeaderLabel = new JLabel(" SIGMABUSTER CHECKOUT");
        checkoutHeaderLabel.setForeground(Color.yellow); // set text color to yellow
        checkoutHeaderLabel.setFont(new Font("Serif", Font.BOLD, 27));
        // Add the label to the header panel
        // align the text to the left
        checkoutHeaderPanel.setLayout(new BorderLayout());
        checkoutHeaderPanel.add(checkoutHeaderLabel, BorderLayout.WEST);
        /*
         * {
         * JPanel sb = new JPanel(new FlowLayout(FlowLayout.RIGHT));
         * sb.add(new JLabel("Search:"));
         * sb.add(new JTextField(12));
         * checkoutHeaderPanel.add(sb, BorderLayout.EAST);
         * }
         */
        checkoutHeaderPanel.add(new Searchbar(), BorderLayout.EAST);
        // add a history of products panel
        final JPanel historyPanel = new JPanel();
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        historyPanel.setBackground(Color.lightGray);
        historyPanel.setPreferredSize(new Dimension(200, 400));
        historyPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 8));
        final JLabel historyLabel = new JLabel("Purchase History");
        historyLabel.setFont(new Font("Serif", Font.BOLD, 15));
        historyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // clear the pannel to repaint new one
        historyPanel.removeAll();
        historyPanel.add(historyLabel);
        // historyLabel.setFont(new Font("Serif", Font.BOLD, 15));

        // create a checkout panel
        JPanel checkoutPanel = new JPanel();
        checkoutPanel.setLayout(new BoxLayout(checkoutPanel, BoxLayout.Y_AXIS));
        checkoutPanel.setBackground(Color.white);
        // create a border around the panel
        checkoutPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 10));
        // Create a label for the checkout
        JLabel checkoutLabel = new JLabel("YOUR CART");
        checkoutLabel.setFont(new Font("Serif", Font.BOLD, 15));
        checkoutLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkoutPanel.add(checkoutLabel);

        // add items in the cart to the checkout panel
        for (int i = 0; i < cartItemList.size(); i++) {
          CartItem item = (CartItem) cartItemList.elementAt(i);
          JLabel itemLabel = new JLabel(item.getDetails());
          itemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
          itemLabel.setFont(new Font("Serif", Font.PLAIN, 12));
          // add a line breaker in between each cart item
          JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
          separator.setPreferredSize(new Dimension(200, 1));
          separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 5));
          // add the item label and separator to the checkout panel
          checkoutPanel.add(itemLabel);
          checkoutPanel.add(Box.createRigidArea(new Dimension(0, 5)));
          checkoutPanel.add(separator);
          checkoutPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        }

        // create buy now button
        JButton buyButton = new JButton("BUY NOW " + totalLabel.getText());
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyButton.setFont(new Font("Serif", Font.BOLD, 20));
        buyButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (cartItemList.isEmpty()) {
              // if the cart is empty, show a message
              JOptionPane.showMessageDialog(checkoutFrame, "Your cart is empty!", "Empty Cart",
                  JOptionPane.WARNING_MESSAGE);
              return; // exit the action listener
            } else {
              // action to perform when buy now button is clicked
              JOptionPane.showMessageDialog(checkoutFrame, "Thank you for your purchase!", "Purchase Successful",
                  JOptionPane.INFORMATION_MESSAGE);
              // remove the cart panne
              cartPanel.removeAll();
              cartPanel.revalidate();
              cartPanel.repaint();
              // repaint cart pannel
              cartPanel.add(Box.createVerticalGlue());
              cartPanel.add(totalLabel);
              cartPanel.add(checkoutButton);

              Vector saleHistoryItemList = new Vector();
              for (int i = 0; i < cartItemList.size(); i++) {
                saleHistoryItemList.addElement(cartItemList.elementAt(i));
              }
              String orderKey = "Order " + orderNumber + " - " + totalLabel.getText();
              saleHistory.put(orderKey, saleHistoryItemList);
              orderNumber++;

              // redraw the history panel
              // historyPanel.removeAll();
              historyPanel.add(historyLabel);
              historyPanel.removeAll();
              historyPanel.add(historyLabel);

              for (Enumeration keys = saleHistory.keys(); keys.hasMoreElements();) {
                Object key = keys.nextElement();
                Vector items = (Vector) saleHistory.get(key);

                StringBuffer sb = new StringBuffer();
                sb.append(String.valueOf(key)).append("\n");

                for (int i = 0; i < items.size(); i++) {
                  sb.append(" • ").append(items.elementAt(i).toString()).append("\n");
                }

                JTextArea area = new JTextArea(sb.toString());
                area.setEditable(false);
                area.setOpaque(false);
                area.setFont(new Font("Serif", Font.PLAIN, 12));

                historyPanel.add(area);
              }
              // if purchasenumber = 0;
              historyPanel.revalidate();
              historyPanel.repaint();
              historyPanel.add(historyLabel);
              historyLabel.setFont(new Font("Serif", Font.BOLD, 15));

              // reset the cart
              cartItemList.removeAllElements();
              totalLabel.setText("Total: $0.00");
              itemCount = 0;
              cartLabel.setText("Shopping Cart(0)");

              itemCount = 0;
              // reset the item count
            }
          }

        });

        // add buy now button to the bottom of the checkout panel
        checkoutPanel.add(Box.createVerticalGlue());
        checkoutPanel.add(buyButton);

        // add the panels to the frame
        checkoutFrame.getContentPane().add(checkoutHeaderPanel, BorderLayout.NORTH);
        checkoutFrame.getContentPane().add(checkoutPanel, BorderLayout.CENTER);
        historyPanel.removeAll();
        historyPanel.add(historyLabel);
        historyPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        // add all existing orders
        for (Enumeration keys = saleHistory.keys(); keys.hasMoreElements();) {
          Object key = keys.nextElement();
          Vector items = (Vector) saleHistory.get(key);
          StringBuffer sb = new StringBuffer();
          sb.append(String.valueOf(key)).append("\n");
          for (int i = 0; i < items.size(); i++) {
            sb.append(" • ").append(items.elementAt(i).toString()).append("\n");
          }
          JTextArea area = new JTextArea(sb.toString());
          area.setEditable(false);
          area.setOpaque(false);
          area.setFont(new Font("Serif", Font.PLAIN, 12));
          historyPanel.add(area);
          historyPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        historyPanel.revalidate();
        historyPanel.repaint();
        checkoutFrame.getContentPane().add(historyPanel, BorderLayout.EAST);

        // historyPanel.setVisible(false);
        /*
         * // centre the frame on the screen
         * { java.awt.Dimension scr =
         * java.awt.Toolkit.getDefaultToolkit().getScreenSize();
         * checkoutFrame.setLocation((scr.width - checkoutFrame.getWidth())/2,
         * (scr.height - checkoutFrame.getHeight())/2);
         * }
         */
        checkoutFrame.setResizable(false);
        checkoutFrame.setVisible(true);
      }
    });

    frame.getContentPane().add(cartPanel, BorderLayout.EAST);

    // making the frame visible
    frame.setVisible(true);

  }

  // for calculating the total price of product
  private static double calculatetotal() {
    double sum = 0.0;
    for (int i = 0; i < cartItemList.size();) {
      CartItem c = (CartItem) cartItemList.elementAt(i);
      sum += c.getTotalPrice();
      i = i + 1;
    }
    return sum;
  }

  public static void addToCart(String name, double price, int quantity) {
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

class CartItem extends ProductItem {
  int quantity; // quantity of the item in the cart
  // to represent an item in the shopping cart
  // will have method to get item details

  // constructor for CartItem
  // calls the superclass constructor (ProductItem)
  public CartItem(String name, double price, int quantity) {
    // call the superclass constructor
    super(0, name, (float) price, 0, "");
    this.quantity = quantity;
  }

  /* method to get the total price of an item */
  public double getTotalPrice() {
    return price * quantity;
  }

  @Override
  public String toString() {
    return name + " - $" + price + " x " + quantity;
  }
}

/* ProductItem class */
class ProductItem {
  // to represent the products we're selling
  // will have name, price, inStore, description, imageName
  // method to add to cart
  // method to get product details
  protected int productID;
  protected String name;
  protected float price;
  protected int inStore;
  protected String imageName;

  // constructor
  public ProductItem(int productID, String name, float price, int inStore, String imageName) {
    this.productID = productID;
    this.name = name;
    this.price = price;
    this.inStore = inStore;
    this.imageName = imageName;
  }

  /* method to get product details */
  public String getDetails() {
    return name + " - $" + price + " x " + inStore;
  }

  /* method to add to cart */
  public void addToCart(int quantity) {
    // create a new CartItem and add it to the cart
    CartItem newItem = new CartItem(name, price, quantity);
    Application1.cartItemList.addElement(newItem);
    // change the quantity of the product
    this.inStore -= quantity;
  }

  /* method to create product panel to display */
  public JPanel createProductPanel(ProductItem product) {
    // create a new JPanel
    JPanel subItemPanel = new JPanel();
    subItemPanel.setLayout(new BoxLayout(subItemPanel, BoxLayout.Y_AXIS));
    subItemPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 5));

    // add the product image
    ImageIcon icon = new ImageIcon("images/" + product.imageName + "");
    JLabel imageLabel = new JLabel(icon);
    imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    subItemPanel.add(imageLabel);

    // add a label for the product name
    JLabel nameLabel = new JLabel(product.name);
    nameLabel.setFont(new Font("Serif", Font.BOLD, 14));
    nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    // text
    JLabel textLabel = new JLabel(name + " - $" + price);
    textLabel.setFont(new Font("Serif", Font.PLAIN, 16));

    // scale cart to 10% of the product
    // int cartWidth = icon.getIconWidth() / 10;
    // int cartHeight = icon.getIconHeight() / 10;
    // infoPanel.add(cartLabel);
    ImageIcon cartIcon = new ImageIcon("images/cart.jpg");
    Image original = cartIcon.getImage();
    Image scaled = original.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    ImageIcon smallCartIcon = new ImageIcon(scaled);
    JButton cartButtton = new JButton(smallCartIcon);

    // info panel
    JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
    infoPanel.setOpaque(false);
    infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

    infoPanel.add(textLabel);
    infoPanel.add(cartButtton);

    subItemPanel.add(infoPanel);

    cartButtton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Application1.addToCart(name, price, 1);
      }
    });

    return subItemPanel;
  }
}
