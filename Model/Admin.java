package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class Admin extends ProgramUser {

    public Admin() {

    }

    public Admin(String username, String accountPassword, String securityQuestion, String securityPassword, int userType) {
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

        try (Connection connection = createConnection();
             PreparedStatement programUserStatement = connection.prepareStatement(insertProgramUserQuery);) {

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

    @Override
    public static Admin fetchUser(String username, String password) {
        Admin admin = new Admin();
        boolean flag = false;

        try (Connection connection = createConnection();
             Statement statement = connection.createStatement();) {

            // Execute a Query
            String query = "SELECT * FROM ProgramUser";
            ResultSet resultSet = statement.executeQuery(query);

            int tempUserType;
            String tempUsername, tempPassword, securityQuestion, securityPassword;

            while (resultSet.next() && !flag) {
                tempUserType = resultSet.getInt("userType");
                tempUsername = resultSet.getString("username");
                tempPassword = resultSet.getString("accountPassword");

                if(tempUserType == 1){
                    if(tempUsername.equals(username) && tempPassword.equals(password)) {
                        securityQuestion = resultSet.getString("securityQuestion");
                        securityPassword = resultSet.getString("securityPassword");
                        admin = new Admin(username, password, securityQuestion, securityPassword, 1);
                        flag = true;
                    }
                }
            }
        } catch (SQLException e) {
                System.err.println("Error inserting data: " + e.getMessage());
        }

        if(flag)
            return admin;
        else
            return null;
    }
}