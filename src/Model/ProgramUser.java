package Model;

import DatabaseConnection.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

abstract public class ProgramUser extends DatabaseConnection {
    protected String username;
    protected String accountPassword;
    protected String securityQuestion;
    protected String securityPassword;
    protected int userID;
    protected int userType;
    protected boolean notAnUpdate = true;

    // SETTERS

    public void setAccountPassword(String accountPassword){
        this.accountPassword = accountPassword;

        String updateAccPasswordQuery = "UPDATE ProgramUser SET accountPassword = ? WHERE username = ?";

        try (Connection connection = createConnection();
             PreparedStatement accPasswordStatement = connection.prepareStatement(updateAccPasswordQuery);) {
                accPasswordStatement.setString(1, accountPassword);
                accPasswordStatement.setString(2, username);

                accPasswordStatement.executeUpdate();

                connection.close();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }

    public void setAccountPassword(String accountPassword, boolean notAnUpdate){
        this.accountPassword = accountPassword;
    }

    public void setSecurityQuestion(String securityQuestion){
        this.securityQuestion = securityQuestion;
        
        String updateSecQuestionQuery = "UPDATE ProgramUser SET securityPassword = ? WHERE username = ?";

        try (Connection connection = createConnection();
             PreparedStatement secQuestionStatement = connection.prepareStatement(updateSecQuestionQuery);) {
                secQuestionStatement.setString(1, securityQuestion);
                secQuestionStatement.setString(2, username);

                secQuestionStatement.executeUpdate();

                connection.close();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }

    public void setSecurityQuestion(String securityQuestion, boolean notAnUpdate){
        this.securityQuestion = securityQuestion;
    }


    public void setSecurityPassword(String securityPassword){
        this.securityPassword = securityPassword;
        
        String updateSecPasswordQuery = "UPDATE ProgramUser SET securityPassword = ? WHERE username = ?";

        try (Connection connection = createConnection();
             PreparedStatement secPasswordStatement = connection.prepareStatement(updateSecPasswordQuery);) {
                secPasswordStatement.setString(1, securityPassword);
                secPasswordStatement.setString(2, username);

                secPasswordStatement.executeUpdate();

                connection.close();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }

    public void setSecurityPassword(String securityPassword, boolean notAnUpdate){
        this.securityPassword = securityPassword;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setUserType(int userType){
        this.userType = userType;
    }

    public void setUserID(int x){
        userID = x;
    }

    public int getUserID(){
        return userID;
    }

    // GETTERS
    public String getUsername(){
        return username;
    }

    public String getAccountPassword(){
        return accountPassword;
    }

    public String getSecurityQuestion(){
        return securityQuestion;
    }

    public String getSecurityPassword(){
        return securityPassword;
    }

    public int getUserType(){
        return userType;
    }

    //public abstract <User extends ProgramUser> User fetchUser(String username, String password);

    // static since we cannot create an instance of 
    public static int changePassword(String username, String newPassword, String securityQuestion, String securityPassword) {
        int indicator = 2; // Default: Username does not exist.

        String selectQuery = "SELECT * FROM ProgramUser WHERE username = ? AND securityQuestion = ? AND securityPassword = ?";
        String updateQuery = "UPDATE ProgramUser SET accountPassword = ? WHERE username = ?";

        try (Connection connection = createConnection();
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

            // Set parameters for SELECT query
            selectStatement.setString(1, username);
            selectStatement.setString(2, securityQuestion);
            selectStatement.setString(3, securityPassword);

            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Match found, update password
                    indicator = 1; // Success
                    updateStatement.setString(1, newPassword);
                    updateStatement.setString(2, username);
                    updateStatement.executeUpdate();
                }
            }

            connection.close();
        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
        }

        return indicator;
    }

    public static boolean checkUsernameValid(String username) {
        boolean flag = true;
        String tempUsername = "";

        try (Connection connection = createConnection();
             Statement statement = connection.createStatement();) {
        
            String query = "SELECT * FROM ProgramUser " + 
                            "WHERE username = \"" + username + "\"";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                tempUsername = resultSet.getString("username");
                
            }

            connection.close();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }

        if(!tempUsername.isEmpty())
            flag = false;

        return flag;
    }

}