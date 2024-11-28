package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract public class DatabaseConnection {
    private static final String url = "jdbc:mysql://localhost:3306/ISSPa"; // Link to database.
    private static final String username = "root"; // Replace with your MySQL username.
    private static final String password = "12345"; // Replace with your MySQL password.

    // Establish a database connection.
    protected static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    // Define in subclasses to execute queries.
    public abstract void createQuery();
}
