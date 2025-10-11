import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnect {
  public static void main(String[] args) {
    String url = "jdbc:postgresql://localhost:5432/sigmabustersincz";
    String user = "postgres"; 
    String password = "Very$trongP@ssw0rd";

    Connection conn = null;
    try {
      // Load the PostgreSQL JDBC driver manually (required for Java 1.2)
      Class.forName("org.postgresql.Driver");

      conn = DriverManager.getConnection(url, user, password);
      System.out.println("Connected to the PostgreSQL server successfully.");
    } catch (SQLException e) {
      System.out.println("Connection failure.");
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      System.out.println("Driver class not found.");
      e.printStackTrace();
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
      }
    }
  }
}