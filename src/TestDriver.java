import java.sql.*;   // JDBC 1.0 compatible

public class TestDriver {

  public static void main(String[] args) {

    String driver = "org.postgresql.Driver";
    String url    = "jdbc:postgresql://127.0.0.1:5432/fakedb";  // change later
    String user   = "user";
    String pass   = "password";

    try {
      System.out.println("--------------------------------------------------");
      System.out.println("Loading driver: " + driver);
      Class.forName(driver);
      System.out.println("✅ Driver loaded successfully.");

      // --------------------------------------------------
      // List all registered JDBC drivers (sanity check)
      System.out.println("Registered drivers:");
      java.util.Enumeration e = DriverManager.getDrivers();
      while (e.hasMoreElements()) {
        Object d = e.nextElement();
        System.out.println("  -> " + d.getClass().getName());
      }

      // --------------------------------------------------
      // Try a dummy connection (expected to fail if no DB)
      System.out.println("Attempting connection to: " + url);
      Connection conn = DriverManager.getConnection(url, user, pass);
      System.out.println("🎉 Connection established!");
      conn.close();
      System.out.println("Connection closed cleanly.");

    } catch (SQLException sqle) {
      System.out.println("⚠️ Expected SQL exception (no DB or network):");
      System.out.println("  SQLState: " + sqle.getSQLState());
      System.out.println("  Message : " + sqle.getMessage());
      System.out.println("  ErrorCode: " + sqle.getErrorCode());
    } catch (ClassNotFoundException cnfe) {
      System.out.println("❌ Driver class not found — check classpath.");
      cnfe.printStackTrace();
    } catch (Throwable t) {
      System.out.println("❌ Unexpected error:");
      t.printStackTrace();
    }

    System.out.println("--------------------------------------------------");
    System.out.println("Test complete.");
  }
}
