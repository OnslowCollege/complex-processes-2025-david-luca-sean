//package Checkout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import java.io.*;

public class TransactionPage {
    JFrame transactionFrame;
    JTable table;
    DefaultTableModel model;

    //data
    Vector allRows = new Vector();

    //filters
    JTextField startDateField, endDateField, itemFilterField;

    //summary labels
    JLabel totalQtyLabel, totalMoneyLabel;

    final SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd");
    final SimpleDateFormat fmtDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    final String defaultCsvPath = "data/transactions.txt";

    public TransactionPage() {
        transactionFrame = new JFrame("Transactions");
        transactionFrame.getContentPane().setLayout(new BorderLayout());
        //header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.gray);
        //filter controls
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 6));
        filterPanel.setOpaque(false);

        filterPanel.add(new JLabel("Start :"));
        startDateField = new JTextField(9);
        filterPanel.add(startDateField);

        filterPanel.add(new JLabel("End :"));
        endDateField = new JTextField(9);
        filterPanel.add(endDateField);

        filterPanel.add(new JLabel("Item contains:"));
        itemFilterField = new JTextField(10);
        filterPanel.add(itemFilterField);

        JButton filterBtn = new JButton("Filter");
        JButton clearBtn = new JButton("Clear");
        filterPanel.add(filterBtn);
        filterPanel.add(clearBtn);

        headerPanel.add(filterPanel, BorderLayout.EAST);
        transactionFrame.getContentPane().add(headerPanel, BorderLayout.NORTH);
        //table 
        String[] cols = {"Order #","Item","Qty","Total","When"};
        model = new DefaultTableModel(cols,0){ public boolean isCellEditable(int r,int c){return false;} };
        table = new JTable(model);
        transactionFrame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
        //sumary pannel with quantity and te total

        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        summaryPanel.setBackground(Color.lightGray);

        totalQtyLabel = new JLabel("Total Qty: 0");
        totalMoneyLabel = new JLabel("Total $: $0.00");

        totalQtyLabel.setFont(new Font("Serif", Font.BOLD, 14));
        totalMoneyLabel.setFont(new Font("Serif", Font.BOLD, 14));
        //add to pannel the total money and the quantity
        summaryPanel.add(totalQtyLabel);
        summaryPanel.add(totalMoneyLabel);
        transactionFrame.getContentPane().add(summaryPanel, BorderLayout.SOUTH);
        //button when click 
        filterBtn.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e){applyFilters();}});
        clearBtn.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e){
            startDateField.setText(""); endDateField.setText(""); itemFilterField.setText("");
            repopulateAll();
            updateTotals();
        }});
        //initalizing the frame.
        transactionFrame.setSize(860,460);
        Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
        transactionFrame.setLocation((scr.width-860)/2,(scr.height-460)/2);
        transactionFrame.addWindowListener(new java.awt.event.WindowAdapter(){
            public void windowClosing(java.awt.event.WindowEvent e){
                try{saveToTxt(defaultCsvPath);}catch(Exception ex){}
                transactionFrame.dispose();
            }});
        transactionFrame.setVisible(false);

        loadFromTxt(defaultCsvPath);
        updateTotals();
    }

    public void show(){ transactionFrame.setVisible(true); }

    // transaction history
    public void addTransaction(Transaction t){
        String orderNo = String.valueOf(t.getId());
        String whenStr = fmtDateTime.format(t.getWhen());

        Enumeration en = t.getItems();
        while(en.hasMoreElements()){
            CartItem ci = (CartItem)en.nextElement();
            String itemName = ci.name;
            Integer qty = new Integer(ci.quantity);
            String totalStr = formatMoney(ci.getTotalPrice());
            Object[] row = new Object[]{orderNo,itemName,qty,totalStr,whenStr};
            allRows.addElement(row);
            model.addRow(row);
        }
        updateTotals();
        try{saveToTxt(defaultCsvPath);}catch(Exception ex){}
    }

    //filters
    private void applyFilters(){
        String s=startDateField.getText().trim(), e=endDateField.getText().trim(), f=itemFilterField.getText().trim();
        long start=Long.MIN_VALUE,end=Long.MAX_VALUE;
        try{ if(s.length()>0) start=fmtDate.parse(s).getTime(); }catch(ParseException ex){ showParseError("Start date");return; }
        try{ if(e.length()>0){ long t=fmtDate.parse(e).getTime(); end=t+24L*60L*60L*1000L-1L; } }catch(ParseException ex){ showParseError("End date");return; }
        String needle=f.toLowerCase();
        //clear the table
        clearTable();
        for(int i=0;i<allRows.size();i++){
            Object[] row=(Object[])allRows.elementAt(i);
            long t; try{t=fmtDateTime.parse((String)row[4]).getTime();}catch(ParseException ex){continue;}
            if(t<start||t>end)continue;
            if(needle.length()>0){
                String hay=String.valueOf(row[1]).toLowerCase();
                if(hay.indexOf(needle)<0)continue;
            }
            model.addRow(row);
        }
        updateTotals();
    }

//save all transactions to a txt file using ';' as delimiter
    public void saveToTxt(String path) throws IOException {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(path)));

            //write header
            out.println("Order#;Item;Qty;Total;When");

            //each transaction row
            for (int i = 0; i < allRows.size(); i++) {
                Object[] r = (Object[]) allRows.elementAt(i);
                String order = String.valueOf(r[0]);
                String item  = String.valueOf(r[1]);
                String qty   = String.valueOf(((Integer) r[2]).intValue());
                String total = String.valueOf(r[3]);
                String when  = String.valueOf(r[4]);
                out.println(order + ";" + item + ";" + qty + ";" + total + ";" + when);
            }
        } finally {
            if (out != null) out.close();
        }
    }

// Load all transactions from TXT file (semicolon-separated)
    public void loadFromTxt(String path) {
        File f = new File(path);
        if (!f.exists() || !f.isFile()) return;

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(f));
            String line;
            boolean first = true;

            allRows.removeAllElements();
            clearTable();

            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }

                // Split by semicolon
                String[] parts = splitSemicolonLine(line);
                if (parts == null || parts.length < 5) continue;

                // Validate date
                try { fmtDateTime.parse(parts[4]); } catch (ParseException pe) { continue; }

                Object[] row = new Object[] {
                        parts[0],
                        parts[1],
                        new Integer(Integer.parseInt(parts[2])),
                        parts[3],
                        parts[4]
                };
                allRows.addElement(row);
                model.addRow(row);
            }
        } catch (IOException io) {
        } finally {
            try { if (br != null) br.close(); } catch (IOException ignore) {}
        }
    }


    //splits a line using ';' in the txx
    private String[] splitSemicolonLine(String line) {
        if (line == null) return null;

        Vector parts = new Vector();
        StringBuffer cur = new StringBuffer();
        //
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == ';') {
                parts.addElement(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        parts.addElement(cur.toString());

        String[] arr = new String[parts.size()];
        for (int i = 0; i < parts.size(); i++)
            arr[i] = (String) parts.elementAt(i);

        return arr;
    }


    //helper method
    private void repopulateAll(){ clearTable(); for(int i=0;i<allRows.size();i++) model.addRow((Object[])allRows.elementAt(i)); updateTotals(); }
    private void clearTable(){ for(int i=model.getRowCount()-1;i>=0;i--)model.removeRow(i); }
    // an error message if the date messed up
    private void showParseError(String w){ JOptionPane.showMessageDialog(transactionFrame,w+" is not valid date (yyyy-MM-dd)","Invalid Date",JOptionPane.WARNING_MESSAGE); }
    private String formatMoney(double v){ java.text.DecimalFormat df=new java.text.DecimalFormat("$#,##0.00"); return df.format(new Double(v)); }


    //Sum up total price
    private void updateTotals(){
        int totalQty=0;
        double totalMoney=0.0;
        for(int i=0;i<model.getRowCount();i++){
            Object qObj=model.getValueAt(i,2);
            Object tObj=model.getValueAt(i,3);
            int qty=0;
            try{qty=((Integer)qObj).intValue();}catch(Exception ex){}
            //add the total quanity with one item
            totalQty+=qty;
            try{
                String s=String.valueOf(tObj).replace("$","").replace(",","");
                totalMoney+=Double.valueOf(s).doubleValue();
            }catch(Exception ex){}
        }
        //print out the total on pannel
        totalQtyLabel.setText("Total Qty: "+totalQty);
        java.text.DecimalFormat df=new java.text.DecimalFormat("$#,##0.00");
        totalMoneyLabel.setText("Total $: "+df.format(new Double(totalMoney)));
    }
}
