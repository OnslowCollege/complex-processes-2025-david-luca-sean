import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Vector;
import java.util.Enumeration;

public class TransactionPage {
    JFrame transactionFrame;
    JTable table;
    DefaultTableModel model;
    Vector allRows = new Vector();

    // Header controls
    JTextField startDateField;
    JTextField endDateField;   
    JTextField itemFilterField;

    //date format
    final SimpleDateFormat fmtDate     = new SimpleDateFormat("yyyy-MM-dd");       // input
    final SimpleDateFormat fmtDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // table "When"

    public TransactionPage() {
        transactionFrame = new JFrame("Transactions");
        transactionFrame.getContentPane().setLayout(new BorderLayout());
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.lightGray);


        //filters on the right
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 6));
        filterPanel.setOpaque(false);

        filterPanel.add(new JLabel("Start:"));
        startDateField = new JTextField(10);
        filterPanel.add(startDateField);

        filterPanel.add(new JLabel("End:"));
        endDateField = new JTextField(10);
        filterPanel.add(endDateField);

        filterPanel.add(new JLabel("Item contains:"));
        itemFilterField = new JTextField(12);
        filterPanel.add(itemFilterField);

        JButton filterBtn = new JButton("Filter");
        JButton clearBtn  = new JButton("Clear");
        filterPanel.add(filterBtn);
        filterPanel.add(clearBtn);

        headerPanel.add(filterPanel, BorderLayout.EAST);
        transactionFrame.getContentPane().add(headerPanel, BorderLayout.NORTH);

        //model fo rthe table
        String[] cols = { "Order #", "Item", "Qty", "Total", "When" };
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        //tables
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(table);
        transactionFrame.getContentPane().add(scroll, BorderLayout.CENTER);

        //events
        filterBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { applyFilters(); }
        });
        clearBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startDateField.setText("");
                endDateField.setText("");
                itemFilterField.setText("");
                repopulateAll();
            }
        });

        //frame
        transactionFrame.setSize(840, 440);
        Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
        transactionFrame.setLocation((scr.width - 840) / 2, (scr.height - 440) / 2);
        transactionFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) { transactionFrame.dispose(); }
        });
        transactionFrame.setVisible(false);
    }

    public void show() {
        transactionFrame.setVisible(true);
    }

    //add a whole Transaction
    public void addTransaction(Transaction t) {
        String orderNo = String.valueOf(t.getId());
        String whenStr = fmtDateTime.format(t.getWhen());

        Enumeration en = t.getItems();
        while (en.hasMoreElements()) {
            CartItem ci = (CartItem) en.nextElement();

            String itemName = ci.name; 
            Integer qty = new Integer(ci.quantity);
            String totalStr = formatMoney(ci.getTotalPrice());

            Object[] row = new Object[] { orderNo, itemName, qty, totalStr, whenStr };
            allRows.addElement(row);
            model.addRow(row);
        }
    }

    //fileter the the thing base the text field
    private void applyFilters() {
        String startTxt = startDateField.getText().trim();
        String endTxt   = endDateField.getText().trim();
        String itemTxt  = itemFilterField.getText().trim();

        long startMillis = Long.MIN_VALUE;
        long endMillis   = Long.MAX_VALUE;

        if (startTxt.length() > 0) {
            try {
                Date d = fmtDate.parse(startTxt);
                startMillis = d.getTime();
            } catch (ParseException pe) {
                showParseError("Start date");
                return;
            }
        }

        if (endTxt.length() > 0) {
            try {
                Date d = fmtDate.parse(endTxt);
                long day = 24L * 60L * 60L * 1000L;
                endMillis = d.getTime() + day - 1L;
            } catch (ParseException pe) {
                showParseError("End date");
                return;
            }
        }
        //make it also work for capital letter
        String needle = itemTxt.toLowerCase();
        clearTable();
        for (int i = 0; i < allRows.size(); i++) {
            Object[] row = (Object[]) allRows.elementAt(i);

            //date check
            String whenStr = (String) row[4];
            long t;
            try {
                t = fmtDateTime.parse(whenStr).getTime();
            } catch (ParseException pe) {
                continue;
            }
            if (t < startMillis || t > endMillis) continue;

            // Item name "contains" check (if user provided a filter)
            if (needle.length() > 0) {
                String hay = String.valueOf(row[1]).toLowerCase();
                if (hay.indexOf(needle) < 0) continue;
            }

            model.addRow(row);
        }
    }

    private void repopulateAll() {
        clearTable();
        for (int i = 0; i < allRows.size(); i++) {
            model.addRow((Object[]) allRows.elementAt(i));
        }
    }

    private void clearTable() {
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }

    private void showParseError(String which) {
        JOptionPane.showMessageDialog(
                transactionFrame,
                which + " is not a valid date. Use yyyy-MM-dd (e.g., 2025-10-31).",
                "Invalid Date",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private String formatMoney(double v) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("$#,##0.00");
        return df.format(new Double(v));
    }
}
