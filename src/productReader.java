
import java.io.*;
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
        String[] fields = line.split(",");

        if (fields.length == 5) {
          int id = Integer.parseInt(fields[0].trim());
          String name = fields[1].trim();
          float price = Float.parseFloat(fields[2].trim());
          int quantity = Integer.parseInt(fields[3].trim());
          String imageFile = fields[4].trim();

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
