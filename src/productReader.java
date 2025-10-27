
import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;

public class productReader {

  public static Vector readProductsFromFile(String filename) {
    Vector products = new Vector();

    try {
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      String line;

      // Skip header line
      reader.readLine();

      while ((line = reader.readLine()) != null) {
        //String[] fields = line.split(",");
        StringTokenizer token = new StringTokenizer(line, ",");
        String[] fields = new String[5];
        int i = 0;
        while (token.hasMoreTokens() && i<5) {
          fields[i]=token.nextToken().trim();
          i = i + 1;
        }

        if (fields.length == 5) {
          int id = Integer.parseInt(fields[0]);
          String name = fields[1];
          float price = Float.valueOf(fields[2]).floatValue();
          int quantity = Integer.parseInt(fields[3]);
          String imageFile = fields[4];

          ProductItem product = new concreteProductItem(id, name, price, quantity, imageFile);
          products.addElement(product);
        } else {
          System.out.println("Invalid line format: " + line);
        }
      }

      reader.close();
    } catch (IOException e) {
      System.out.println("File error: " + e.getMessage());
    } catch (NumberFormatException e) {
      System.out.println("Parsing error: " + e.getMessage());
    }

    return products;
  }

}
