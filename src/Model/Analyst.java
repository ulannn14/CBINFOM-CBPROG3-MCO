package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

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

    public Analyst(String username, String accountPassword) {
        this.username = username;
        this.accountPassword = accountPassword;
        this.securityQuestion = "What is your mother's maiden name?";
        this.securityPassword = "admin1234";
        this.userType = 3;
    }

    @Override
    public void createQuery() {        
        String insertProgramUserQuery = "INSERT INTO `ProgramUser` (`username`, `accountPassword`, `securityQuestion`, `securityPassword`, `userType`) "
                + "VALUES (?, ?, ?, ?, ?)";
        String insertAnalystQuery = "INSERT INTO `Analyst` (`userID`, `dateJoinedID`) "
                + "VALUES (?, ?)";
        String insertDateQuery = "INSERT INTO `Date` (`Year`, `Month`, `Day`) " + "VALUES (?, ?, ?)";

        try (Connection connection = createConnection();
             PreparedStatement programUserStatement = connection.prepareStatement(insertProgramUserQuery);
             PreparedStatement analystStatement = connection.prepareStatement(insertAnalystQuery);
             PreparedStatement dateStatement = connection.prepareStatement(insertDateQuery);
             Statement statement = connection.createStatement();) {

            // Set values for ProgramUser table
            programUserStatement.setString(1, username);
            programUserStatement.setString(2, accountPassword);
            programUserStatement.setString(3, securityQuestion);
            programUserStatement.setString(4, securityPassword);
            programUserStatement.setInt(5, userType);

            programUserStatement.executeUpdate();

            String query = "SELECT P.userID " +
                            "FROM ProgramUser AS P " +
                            "WHERE P.username = \"" + username + "\"";
            ResultSet resultSet = statement.executeQuery(query);

            int userID = 1;
            while (resultSet.next()) {
                userID = resultSet.getInt("userID");
            }

            analystStatement.setInt(1, userID);

            String query2 = "SELECT * FROM Date";
            ResultSet resultSet2 = statement.executeQuery(query2);

            boolean cFlag = false;

            int day, month, year, cDateID = 0;
            LocalDate date = LocalDate.now();

            while (resultSet2.next() && !cFlag) {
                cDateID = resultSet2.getInt("dateID");
                day = resultSet2.getInt("Day");
                month = resultSet2.getInt("Month");
                year = resultSet2.getInt("Year");

                if(date.getDayOfMonth() == day && date.getMonthValue() == month && date.getYear() == year){
                    cFlag = true;
                }
            }

            if(cFlag == false){
                dateStatement.setInt(1, date.getYear());
                dateStatement.setInt(2, date.getMonthValue());
                dateStatement.setInt(3, date.getDayOfMonth());
                dateStatement.executeUpdate();
                cDateID += 1;
            }

            analystStatement.setInt(2, cDateID);
            analystStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }


    public Analyst fetchUser(String username, String password) {
        Analyst tempAnalyst = null;
        int tempUserID, tempJoinedID;
        String tempsecurityQuestion, tempsecurityPassword;
        DateClass dateJoined = new DateClass();
        boolean notAnUpdate = true;

        try (Connection connection = createConnection();
             Statement statement = connection.createStatement();) {

             // Execute a Query
            String query = "SELECT * " +
                            "FROM ProgramUser P " +
                            "JOIN Analyst A ON P.userID = A.userID " +
                            "WHERE userType = 2 AND username = \"" + username + "\" AND accountPassword = \"" + password + "\"";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                tempAnalyst = new Analyst();
                tempUserID = resultSet.getInt("userID");
                tempJoinedID = resultSet.getInt("dateJoinedID");
                tempsecurityQuestion = resultSet.getString("securityQuestion");
                tempsecurityPassword = resultSet.getString("securityPassword");

                tempAnalyst.setAccountPassword(password, notAnUpdate);
                tempAnalyst.setSecurityQuestion(tempsecurityQuestion, notAnUpdate);
                tempAnalyst.setSecurityPassword(tempsecurityPassword, notAnUpdate);
                tempAnalyst.setUsername(username);
                tempAnalyst.setUserID(tempUserID);
                tempAnalyst.setUserType(2);

                String query2 = "SELECT * " +
                            "FROM Date " +
                            "WHERE dateID = " + tempJoinedID;

                ResultSet resultSet3 = statement.executeQuery(query2);

                while(resultSet3.next()) {
                    dateJoined.setYear(resultSet3.getInt("Year"));
                    dateJoined.setMonth(resultSet3.getInt("Month"));
                    dateJoined.setDay(resultSet3.getInt("Day"));
                }

                tempAnalyst.setDateJoined(dateJoined);
            }
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }

        return tempAnalyst;
    }

    public static ArrayList<Analyst> fetchallAnalysts() {
        ArrayList<Analyst> allAnalyst = new ArrayList();
        Analyst tempAnalyst = new Analyst();
        int tempUserID, tempJoinedID;
        String tempUsername, tempaccountPassword, tempsecurityQuestion, tempsecurityPassword;
        DateClass dateJoined = new DateClass();
        boolean notAnUpdate = true;

        try (Connection connection = createConnection();
             Statement statement = connection.createStatement();) {
             // Execute a Query
            String query = "SELECT * " +
                            "FROM ProgramUser P " +
                            "JOIN Analyst A ON P.userID = A.userID " +
                            "WHERE userType = 2";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                tempUsername = resultSet.getString("username");
                tempUserID = resultSet.getInt("userID");
                tempJoinedID = resultSet.getInt("dateJoinedID");
                tempaccountPassword = resultSet.getString("accountPassword");
                tempsecurityQuestion = resultSet.getString("securityQuestion");
                tempsecurityPassword = resultSet.getString("securityPassword");

                tempAnalyst.setAccountPassword(tempaccountPassword, notAnUpdate);
                tempAnalyst.setSecurityQuestion(tempsecurityQuestion, notAnUpdate);
                tempAnalyst.setSecurityPassword(tempsecurityPassword, notAnUpdate);
                tempAnalyst.setUsername(tempUsername);
                tempAnalyst.setUserID(tempUserID);
                tempAnalyst.setUserType(2);

                String query2 = "SELECT * " +
                            "FROM Date " +
                            "WHERE dateID = " + tempJoinedID;

                ResultSet resultSet3 = statement.executeQuery(query2);

                while(resultSet3.next()) {
                    dateJoined.setYear(resultSet3.getInt("Year"));
                    dateJoined.setMonth(resultSet3.getInt("Month"));
                    dateJoined.setDay(resultSet3.getInt("Day"));
                }

                tempAnalyst.setDateJoined(dateJoined);

                allAnalyst.add(tempAnalyst);
            }
            
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }

        return allAnalyst;
    }    

}