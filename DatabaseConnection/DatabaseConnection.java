package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ISSPa"; // Link to database.
    private static final String DB_USERNAME = "root"; // Replace with your MySQL username.
    private static final String DB_PASSWORD = "12345"; // Replace with your MySQL password.

    // Establish a database connection.
    protected static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    public static String getUrl() {
        return DB_URL;
    }

    // Define in subclasses to execute queries.
    public abstract void createQuery();
}
