// mac os 9
//package Checkout;

import java.io.*;
import java.util.StringTokenizer;

public class productWriter {
  public static void changeQuantity(int productID, int newQuantity) {
    BufferedReader reader = null;
    BufferedWriter writer = null;
    try {
      reader = new BufferedReader(new FileReader("data/products.txt"));
      StringBuffer buffer = new StringBuffer();
      String line;
      String nl = System.getProperty("line.separator");
      if (nl == null) nl = "\n";  // fallback

      // Read header (keep as-is)
      String header = reader.readLine();
      if (header != null) {
        buffer.append(header).append(nl);
      }

      while ((line = reader.readLine()) != null) {
        // Parse CSV without regex (Java 1.1): ID,Name,Price,InStore,Image
        String[] parts = tokenizeLine(line, ',');
        if (parts != null && parts.length == 5) {
          try {
            int id = Integer.parseInt(parts[0]);
            if (id == productID) {
              // Update the inStore quantity (index 3)
              parts[3] = String.valueOf(newQuantity);
              line = joinParts(parts, ',');
            }
          } catch (NumberFormatException nfe) {
            // leave the line unchanged if ID isn't a number
          }
        }
        buffer.append(line).append(nl);
      }
      reader.close(); reader = null;

      writer = new BufferedWriter(new FileWriter("data/products.txt"));
      writer.write(buffer.toString());
      writer.flush();
      writer.close(); writer = null;

      System.out.println("File updated successfully.");
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try { if (reader != null) reader.close(); } catch (IOException ignore) {}
      try { if (writer != null) writer.close(); } catch (IOException ignore) {}
    }
  }

  // Split a line by a single-character delimiter using StringTokenizer
  private static String[] tokenizeLine(String line, char delim) {
    if (line == null) return null;
    StringTokenizer tok = new StringTokenizer(line, String.valueOf(delim), false);
    String[] parts = new String[tok.countTokens()];
    int i = 0;
    while (tok.hasMoreTokens()) {
      parts[i++] = tok.nextToken();
    }
    return parts;
  }

  // Join parts back into a delimited line
  private static String joinParts(String[] parts, char delim) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < parts.length; i++) {
      if (i > 0) sb.append(delim);
      if (parts[i] != null) sb.append(parts[i]);
    }
    return sb.toString();
  }
}
