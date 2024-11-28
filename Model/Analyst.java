package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Analyst extends ProgramUser {
    private DateClass dateJoined; 

    public DateClass getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(DateClass date) {
        dateJoined = date;
    }



    public Analyst() {

    }

    public Analyst(String username, String accountPassword, String securityQuestion, String securityPassword, int userType) {
        this.username = username;
        this.accountPassword = accountPassword;
        this.securityQuestion = securityQuestion;
        this.securityPassword = securityPassword;
        this.userType = userType;

        createQuery();
    }

    @Override
    public void createQuery() {        
        String insertProgramUserQuery = "INSERT INTO `ProgramUser` (`username`, `accountPassword`, `securityQuestion`, `securityPassword`, `userType`) "
                + "VALUES (?, ?, ?, ?, ?)";
        String insertAnalystQuery = "INSERT INTO `Analyst` (`userID`, `loginStatus`) "
                + "VALUES (?, ?)";

        try (Connection connection = createConnection();
             PreparedStatement programUserStatement = connection.prepareStatement(insertProgramUserQuery);
             PreparedStatement analystStatement = connection.prepareStatement(insertAnalystQuery);
             Statement statement = connection.createStatement();) {

            String query = "SELECT * FROM Date";
            ResultSet resultSet = statement.executeQuery(query);

            // Set values for ProgramUser table
            programUserStatement.setString(1, username);
            programUserStatement.setString(2, accountPassword);
            programUserStatement.setString(3, securityQuestion);
            programUserStatement.setString(4, securityPassword);
            programUserStatement.setInt(5, userType);

            programUserStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }


    public static Analyst fetchUser(String username, String password) {
        
    }

    public static ArrayList<Analyst> fetchallAnalysts() {
        // database, return all analyst
    }    

}