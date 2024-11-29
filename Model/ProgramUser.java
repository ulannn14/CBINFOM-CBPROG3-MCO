package Model;

import DatabaseConnection.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;

abstract public class ProgramUser extends DatabaseConnection {
    protected String username;
    protected String accountPassword;
    protected String securityQuestion;
    protected String securityPassword;
    protected int userType;

    // SETTERS

    public void setAccountPassword(String accountPassword){
        this.accountPassword = accountPassword;

        String updateAccPasswordQuery = "UPDATE ProgramUser SET accountPassword = ? WHERE username = ?";

        try (Connection connection = createConnection();
             PreparedStatement accPasswordStatement = connection.prepareStatement(updateAccPasswordQuery);) {
                accPasswordStatement.setString(1, accountPassword);
                accPasswordStatement.setString(2, username);

                accPasswordStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }

    public void setSecurityQuestion(String securityQuestion){
        this.securityQuestion = securityQuestion;
        
        String updateSecQuestionQuery = "UPDATE ProgramUser SET securityPassword = ? WHERE username = ?";

        try (Connection connection = createConnection();
             PreparedStatement secQuestionStatement = connection.prepareStatement(updateSecQuestionQuery);) {
                secQuestionStatement.setString(1, securityQuestion);
                secQuestionStatement.setString(2, username);

                secQuestionStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }

    public void setSecurityPassword(String securityPassword){
        this.securityPassword = securityPassword;
        
        String updateSecPasswordQuery = "UPDATE ProgramUser SET securityPassword = ? WHERE username = ?";

        try (Connection connection = createConnection();
             PreparedStatement secPasswordStatement = connection.prepareStatement(updateSecPasswordQuery);) {
                secPasswordStatement.setString(1, securityPassword);
                secPasswordStatement.setString(2, username);

                secPasswordStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }

    public void setUserType(int userType){
        this.userType = userType;
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

    public abstract <User extends ProgramUser> User fetchUser(String username, String password);

    // static since we cannot create an instance of 
    public static int changePassword(String username, String newPassword, String securityQuestion, String securityPassword) {
        /*
                 static changePassword that has 4 parameters, 
                 1. check the database if a user exists and tama yung 2 securty credentials
                 2. if yes, update the password in the database
            --- return the following (1. Successful - match lahat, 2. No username exists, 3. Mismatch security question, 4. Wrong Security Password, in that hierarchy)
         */
        return -1;
    }

    public static boolean checkUsernameValid(String username) {
        /* 
            static checkUsernameValid (boolean)
            --- false if already exist
        */
        return false;
    }

}