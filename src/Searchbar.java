import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Searchbar extends JPanel {
    private final JTextField searchField;
    private final JButton goButton;
    // the search bar that display in top
    public Searchbar() {
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        //the search field that allow user to enter stuff
        searchField = new JTextField("Search", 18);
        //button for entering the search 
        goButton = new JButton("Enter");
        //open the search page when button is clicked.
        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            }
        });

        add(searchField);
        add(goButton);
    }
}