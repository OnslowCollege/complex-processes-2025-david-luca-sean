import java.util.Date;
import java.util.Vector;
import java.util.Enumeration;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

public class Transaction {
    private int id;
    private Date when;
    private Vector items;

    public Enumeration getItems() {
        return items.elements();
    }

    private static final  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
    final DecimalFormat Money = new DecimalFormat("");

    //constructors
    public Transaction(int id){
    this.id = id;
    this.when = new Date();
    this.items = new Vector ();
    }
    //put the cart item in items
    public void addItem(CartItem cartiem){
        if(cartiem == null){
            return;
        }
        items.addElement(cartiem);
    }
    //get id time when 
    //didn't give way to set private varaible so it read only
    //return the id
    public int getId(){
        return id;
    }
    public Date getWhen()
    {return when;}
    public int getItemCount(){return items.size();}
    //calculate sum of the total price base on quantities
    public double getTotal(){
        double sum = 0.0;
        int i = 0;
        for(;i < items.size();i++){
            CartItem cartiem = (CartItem) items.elementAt(i);
            sum += (cartiem.price * cartiem.quantity);
        }
        return sum;
    }
}
