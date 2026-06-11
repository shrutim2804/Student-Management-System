import database.DatabaseConnection;
import java.sql.*;

public class TestDB {
    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                System.out.println("✅ Connection successful!");
                
                // Test query
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM admin");
                
                if (rs.next()) {
                    System.out.println("✅ Found admin: " + rs.getString("username"));
                }
                
                rs.close();
                stmt.close();
            } else {
                System.out.println("❌ Connection failed - got null connection");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}