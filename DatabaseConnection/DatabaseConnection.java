package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract class DatabaseConnection {
    private static final String url = "jdbc:mysql://localhost:3306/ISSPa"; // Link to database.
    private static final String username = "root"; // Replace with your MySQL username.
    private static final String password = "12345"; // Replace with your MySQL password.

    public static String getUrl() { return url; }
    public static String getUsername() { return username; }
    public static String getPassword() { return password; }

    // Establish a database connection.
    protected Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    // Define in subclasses to execute queries.
    public abstract void createQuery();
}
