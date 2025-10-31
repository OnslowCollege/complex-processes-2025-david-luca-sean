// mac os 9
//package Checkout;

import java.io.*;

public class productWriter {
  public static void changeQuantity(int productID, int newQuantity) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader("data/products.txt"));
      StringBuilder buffer = new StringBuilder();
      String line;

      // Read header first
      String header = reader.readLine();
      buffer.append(header).append(System.lineSeparator());

      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length == 5 && Integer.parseInt(parts[0]) == productID) {
          // Update the inStore quantity
          parts[3] = String.valueOf(newQuantity);
          StringBuffer sb = new StringBuffer();
          for (int i = 0; i < parts.length; i++) {
            sb.append(parts[i]);
            if (i < parts.length - 1) {
              sb.append(",");
            }
          }
          line = sb.toString();
        }
        buffer.append(line).append(System.lineSeparator());
      }
      reader.close();

      BufferedWriter writer = new BufferedWriter(new FileWriter("data/products.txt"));
      writer.write(buffer.toString());
      writer.close();

      System.out.println("File updated successfully.");
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
  }
}
