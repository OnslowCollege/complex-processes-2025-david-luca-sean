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
            public void actionPerformed(ActionEvent a) {
                searchPage();
            }
        });

        add(searchField);
        add(goButton);
    }
    private void searchPage(){
        //initiallizing the page
        final JFrame page = new JFrame("Search");
        page.setSize(560, 400);
        page.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent a) { page.dispose(); }
        });

        // 
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.white);

        // Top bar with an input and a Search button
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        top.setBackground(Color.blue);
        JLabel qLbl = new JLabel("Search:");
        final JTextField qField = new JTextField(24);
        JButton doSearch = new JButton("Search");
        doSearch.setBackground(Color.white);
        top.add(qLbl);
        top.add(qField);
        top.add(doSearch);        

        final JTextArea results = new JTextArea();
        results.setEditable(false);
        
        content.add(top, BorderLayout.NORTH);
        //an text of found product(just for ui purpose now)
        page.getContentPane().add(content, BorderLayout.CENTER);     
        JLabel resultsTitle = new JLabel("Found product:", SwingConstants.CENTER);
        resultsTitle.setFont(new Font("Serif", Font.BOLD, 16)); 
        resultsTitle.setForeground(Color.black);
        page.setVisible(true);
        //


    }
}