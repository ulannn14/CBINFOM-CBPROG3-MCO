import java.sql.DriverManager;
import java.sql.Statement;

abstract class DatabaseConnection {
    public abstract void createQuery();

    public static void CreateConnection() {
        // Database URL, Username, and Password
        String url = "jdbc:mysql://localhost:3306/ISSPa"; // Replace `example_db` with your DB name
        String username = "root"; // Replace with your MySQL username
        String password = "12345";    // Replace with your MySQL password

        try {
            // Establish Connection
            Connection connection = DriverManager.getConnection(url, username, password);
            
            System.out.println("Connected to the database!");

            // Execute a Query
            createQuery();

            // Close the connection
            // connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
