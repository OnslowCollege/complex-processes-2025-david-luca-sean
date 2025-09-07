//mac os 9
//package Checkout

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

public class Application1 {

  // ArrayList to hold cart items
  public static ArrayList cartItemList = new ArrayList();
  private static int itemCount = cartItemList.size(); // item counter for shopping cart

  public static void main(String[] args) {
    // Hashmap to store sale history
    Map saleHistory = new HashMap();

    /*
     * SOURCE JFRAME
     * The main window of sigmabuster video
     * Will contain a header, shopping cart and product display area
     */
    // Step 1: Create a new JFrame (window)
    JFrame frame = new JFrame("SIGMABUSTER VIDEO .inc");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(640, 480);
    // frame is divded into NORTH, SOUTH, EAST, WEST and CENTER
    frame.getContentPane().setLayout(new BorderLayout());
    // centre the frame on the screen
    frame.setLocationRelativeTo(null);
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
    //headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    headerPanel.add(headerLabel);

    // add the panel to the frame
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
      //headerPanel.add(new Searchbar());

      //headerPanel.add(headerLabel, BorderLayout.WEST);
      //headerPanel.add(new Searchbar(), BorderLayout.EAST);


      //product 1 (apple)
      JPanel applePanel = new JPanel();
      applePanel.setLayout(new BoxLayout(applePanel, BoxLayout.Y_AXIS));
      Product.addToPanelWith("apple.jpg", "Apple - $1.50", applePanel, 1);

      //headerPanel.add(new Searchbar());


      productPanel.add(applePanel);
    // Step 3: Create a JPanel for the shopping cart
    JPanel cartPanel = new JPanel();
    cartPanel.setBackground(Color.LIGHT_GRAY);
    // create a border around the panel
    cartPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 8));
    // set preferred size for the shopping cart panel
    cartPanel.setPreferredSize(new Dimension(200, 250));

    // setting the layout
    cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
    // Create a label for the shopping cart
    JLabel cartLabel = new JLabel("Shopping Cart(" + itemCount + ")");
    cartLabel.setFont(new Font("Serif", Font.BOLD, 15));
    // set the label to be centered
    cartLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    cartPanel.add(cartLabel);

    // test items in the cart
    CartItem item1 = new CartItem("placeholder 1", 9.99, 1);
    CartItem item2 = new CartItem("placeholder 2", 19.99, 2);

    // add items to the cart panel

    // this is hardcoded for now, until I add the products
    JLabel itemLabel1 = new JLabel(item1.getDetails());
    itemLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
    itemLabel1.setFont(new Font("Serif", Font.PLAIN, 15));
    JLabel itemLabel2 = new JLabel(item2.getDetails());
    itemLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
    itemLabel2.setFont(new Font("Serif", Font.PLAIN, 15));

    cartItemList.add(item1);
    cartItemList.add(item2);

    // adding items into the cart panel
    // for every item in the cart, add it to the cart panel
    for (int i = 0; i < cartItemList.size(); i++) {
      CartItem item = (CartItem) cartItemList.get(i);
      final CartItem currentItem = item; // final variable for the action listener

      JPanel itemPanel = new JPanel();
      itemPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
      itemPanel.setBackground(Color.lightGray);
      itemPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
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
          cartItemList.remove(currentItem);
          cartPanel.remove(itemPanel);
          cartPanel.revalidate();
          cartPanel.repaint();
          itemCount--; // decrement the item count
        }
      });

    }

    // create a label to show total price
    JLabel totalLabel = new JLabel("Total: $" + (item1.getTotalPrice() + item2.getTotalPrice()));
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
        JFrame checkoutFrame = new JFrame("Checkout");
        checkoutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        checkoutFrame.setSize(560, 400);
        checkoutFrame.getContentPane().setLayout(new BorderLayout());

        /* CHECKOUT HEADER */
        JPanel checkoutHeaderPanel = new JPanel();
        checkoutHeaderPanel.setBackground(Color.blue);
        // Create a label for the header
        JLabel checkoutHeaderLabel = new JLabel(" SIGMABUSTER CHECKOUT");
        checkoutHeaderLabel.setForeground(Color.yellow); // set text color to yellow
        checkoutHeaderLabel.setFont(new Font("Serif", Font.BOLD, 27));
        // Add the label to the header panel
        // align the text to the left
        headerPanel.setLayout(new BorderLayout());
        headerPanel.add(headerLabel, BorderLayout.WEST);   
        headerPanel.add(new Searchbar(), BorderLayout.EAST);


        // add a history of products panel
        JPanel historyPanel = new JPanel();
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        historyPanel.setBackground(Color.LIGHT_GRAY);
        historyPanel.setPreferredSize(new Dimension(200, 400));
        historyPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 8));
        JLabel historyLabel = new JLabel("Purchase History");
        historyPanel.add(historyLabel);
        historyLabel.setFont(new Font("Serif", Font.BOLD, 15));
        historyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // iterate through the sale history and add it to the history panel
        Set entrySet = saleHistory.entrySet();
        Iterator iterator = entrySet.iterator();

        while (iterator.hasNext()) {
          Map.Entry entry = (Map.Entry) iterator.next();
          Object key = entry.getKey(); // total price
          Object value = entry.getValue(); // cart item list
          System.out.println(value);
          JLabel historyItemLabel = new JLabel(key + value.toString());
          historyItemLabel.setFont(new Font("Serif", Font.PLAIN, 12));
          historyItemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

          historyPanel.add(historyItemLabel);
        }

        // create a checkout panel
        JPanel checkoutPanel = new JPanel();
        checkoutPanel.setLayout(new BoxLayout(checkoutPanel, BoxLayout.Y_AXIS));
        checkoutPanel.setBackground(Color.WHITE);
        // create a border around the panel
        checkoutPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 10));
        // Create a label for the checkout
        JLabel checkoutLabel = new JLabel("YOUR CART");
        checkoutLabel.setFont(new Font("Serif", Font.BOLD, 15));
        checkoutLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkoutPanel.add(checkoutLabel);

        // add items in the cart to the checkout panel
        for (int i = 0; i < cartItemList.size(); i++) {
          CartItem item = (CartItem) cartItemList.get(i);
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
              // create a new ArrayList to hold the sale history items
              // copy the cart items to the sale history because the cartItemList is cleared
              // later
              ArrayList saleHistoryItemList = new ArrayList(cartItemList);
              // add the cart items and total price to the sale history
              saleHistory.put(totalLabel.getText(), saleHistoryItemList);
              checkoutFrame.dispose(); // close the checkout window
              // reset the cart
              cartItemList.clear();
              totalLabel.setText("Total: $0.00");
              itemCount = 0; // reset the item count
            }
          }

        });

        // add buy now button to the bottom of the checkout panel
        checkoutPanel.add(Box.createVerticalGlue());
        checkoutPanel.add(buyButton);

        // add the panels to the frame
        checkoutFrame.getContentPane().add(checkoutHeaderPanel, BorderLayout.NORTH);
        checkoutFrame.getContentPane().add(checkoutPanel, BorderLayout.CENTER);
        checkoutFrame.getContentPane().add(historyPanel, BorderLayout.EAST);
        // centre the frame on the screen
        checkoutFrame.setLocationRelativeTo(null);
        checkoutFrame.setResizable(false);
        checkoutFrame.setVisible(true);
      }
    });

    frame.getContentPane().add(cartPanel, BorderLayout.EAST);

    // making the frame visible
    frame.setVisible(true);

  }

}

class CartItem extends ProductItem {
  int quantity; // quantity of the item in the cart
  // to represent an item in the shopping cart
  // will have method to get item details

  // constructor for CartItem
  // calls the superclass constructor (ProductItem)
  public CartItem(String name, double price, int quantity) {
    super(name, price, ""); // call the superclass constructor
    this.quantity = quantity;
  }

  /* method to get the total price of an item */
  public double getTotalPrice() {
    return price * quantity;
  }
}

/* ProductItem class */
abstract class ProductItem {
  // to represent the products we're selling
  // will have name, price, inStore, description, imageName
  // method to add to cart
  // method to get product details
  protected String name;
  protected double price;
  protected int inStore;
  protected String description;
  protected String imageName;

  public ProductItem(String name, double price, String description) {
    this.name = name;
    this.price = price;
    this.inStore = 1;
    this.description = description;
    this.imageName = "image/cookie.jpg"; // cookie image as default
  }

  /* method to get product details */
  public String getDetails() {
    return name + " - $" + price + " x " + inStore;
  }

  /* method to add to cart */
  public void addToCart(int quantity) {
    // create a new CartItem and add it to the cart
    CartItem newItem = new CartItem(name, price, quantity);
    Application1.cartItemList.add(newItem);
    // change the quantity of the product
    this.inStore -= quantity;

  }
}
