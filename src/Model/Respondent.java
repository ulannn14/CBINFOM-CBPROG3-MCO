package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class Respondent extends ProgramUser {
    private String name;
    private DateClass birthdate = new DateClass(0,1,1);
    private String emailAddress;
    private DateClass dateJoined; 

    public DateClass getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(DateClass date) {
        dateJoined = date;
    }

    public Respondent() {
        // Default constructor
    }

    public Respondent(String username, String accountPassword, String securityQuestion, String securityPassword, 
                      int userType, String name, DateClass birthdate, String emailAddress, DateClass dateJoined) {
        this.username = username;
        this.accountPassword = accountPassword;
        this.securityQuestion = securityQuestion;
        this.securityPassword = securityPassword;
        this.userType = userType;
        this.name = name;
        this.birthdate = birthdate;
        this.emailAddress = emailAddress;
        this.dateJoined = dateJoined;
    }

    @Override
    public void createQuery() {
        String insertRespondentQuery = "INSERT INTO `Respondent` (`userID`, `name`, `emailAddress`, `birthDateID`, `dateJoinedID`) "
                + "VALUES (?, ?, ?, ?, ?)";
        
        String insertProgramUserQuery = "INSERT INTO `ProgramUser` (`username`, `accountPassword`, `securityQuestion`, `securityPassword`, `userType`) "
                + "VALUES (?, ?, ?, ?, ?)";

        String insertDateQuery = "INSERT INTO `Date` (`Year`, `Month`, `Day`) " + "VALUES (?, ?, ?);";

        try (Connection connection = createConnection();
             PreparedStatement respondentStatement = connection.prepareStatement(insertRespondentQuery);
             PreparedStatement programUserStatement = connection.prepareStatement(insertProgramUserQuery);
             PreparedStatement dateStatement = connection.prepareStatement(insertDateQuery);
             Statement statement = connection.createStatement();) {

            // Execute a Query
            String query = "SELECT * FROM Date";
            ResultSet resultSet = statement.executeQuery(query);

            boolean cFlag = false, bFlag = false;
            // 0 - birthdate, 1 - current date
            int[] day = new int[2], month = new int[2], year = new int[2];
            int bDateID = 0, cDateID = 0;
            LocalDate date = LocalDate.now();

            while (resultSet.next() && !bFlag && !cFlag) {
                if(!bFlag) {
                    bDateID = resultSet.getInt("dateID");
                    day[0] = resultSet.getInt("Day");
                    month[0] = resultSet.getInt("Month");
                    year[0] = resultSet.getInt("Year");
                }

                if(!cFlag) {
                    cDateID = resultSet.getInt("dateID");
                    day[1] = resultSet.getInt("Day");
                    month[1] = resultSet.getInt("Month");
                    year[1] = resultSet.getInt("Year");
                }

                if(birthdate.getDay() == day[0] && birthdate.getMonth() == month[0] && birthdate.getYear() == year[0]){
                    bFlag = true;
                }

                if(date.getDayOfMonth() == day[1] && date.getMonthValue() == month[1] && date.getYear() == year[1]){
                    cFlag = true;
                }
            }

            if(bFlag == false){
                dateStatement.setInt(1, birthdate.getDay());
                dateStatement.setInt(2, birthdate.getMonth());
                dateStatement.setInt(3, birthdate.getYear());
                dateStatement.executeUpdate();
                bDateID += 1;
            }

            if(cFlag == false){
                dateStatement.setInt(1, date.getYear());
                dateStatement.setInt(2, date.getMonthValue());
                dateStatement.setInt(3, date.getDayOfMonth());
                dateStatement.executeUpdate();
                cDateID += 1;
            }

            // Set values for ProgramUser table
            programUserStatement.setString(1, username);
            programUserStatement.setString(2, accountPassword);
            programUserStatement.setString(3, securityQuestion);
            programUserStatement.setString(4, securityPassword);
            programUserStatement.setInt(5, userType);

            String query1 = "SELECT P.userID " +
                            "FROM ProgramUser AS P " +
                            "WHERE P.username = \"" + username + "\"";
            ResultSet resultSet1 = statement.executeQuery(query1);

            int ID = 1;
            while (resultSet1.next()) {
                ID = resultSet.getInt("userID");
            }

            // Set values for Respondent table
            respondentStatement.setInt(1, ID);
            respondentStatement.setString(1, name);
            respondentStatement.setString(2, emailAddress);
            respondentStatement.setInt(3, bDateID);
            respondentStatement.setInt(4, cDateID); 

            respondentStatement.executeUpdate();
            programUserStatement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }

    public Respondent fetchUser(String username, String password) {
        Respondent tempRespondent = null;
        int tempUserID, tempBirthID, tempJoinedID;
        String tempsecurityQuestion, tempsecurityPassword, tempName, tempEmail;
        boolean notAnUpdate = true;
        DateClass birthDate = new DateClass(), dateJoined = new DateClass();

        try (Connection connection = createConnection();
             Statement statement = connection.createStatement();
             Statement statement2 = connection.createStatement();
             Statement statement3 = connection.createStatement();) {

             // Execute a Query
            String query = "SELECT * " + 
                            "FROM ProgramUser P " +
                            "JOIN Respondent R ON P.userID = R.userID " + 
                            "WHERE userType = 3 AND P.username = \"" + username + "\" AND P.accountPassword = \"" + password + "\"";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                tempRespondent = new Respondent();
                tempUserID = resultSet.getInt("userID") - 1;
                tempsecurityQuestion = resultSet.getString("securityQuestion");
                tempsecurityPassword = resultSet.getString("securityPassword");
                tempName = resultSet.getString("name");
                tempEmail = resultSet.getString("emailAddress");
                tempBirthID = resultSet.getInt("birthDateID");
                tempJoinedID = resultSet.getInt("dateJoinedID");

                tempRespondent.setAccountPassword(password, notAnUpdate);
                tempRespondent.setSecurityQuestion(tempsecurityQuestion, notAnUpdate);
                tempRespondent.setSecurityPassword(tempsecurityPassword, notAnUpdate);
                tempRespondent.setUsername(username);
                tempRespondent.setUserID(tempUserID);
                tempRespondent.setUserType(3);
                tempRespondent.setName(tempName);
                tempRespondent.setEmailAddress(tempEmail);
                tempRespondent.setEmailAddress(tempEmail);

                String query2 = "SELECT * " +
                            "FROM Date " +
                            "WHERE dateID = " + tempBirthID;

                ResultSet resultSet2 = statement2.executeQuery(query2);

                while(resultSet2.next()) {
                    birthDate.setYear(resultSet2.getInt("Year"));
                    birthDate.setMonth(resultSet2.getInt("Month"));
                    birthDate.setDay(resultSet2.getInt("Day"));
                }

                String query3 = "SELECT * " +
                            "FROM Date " +
                            "WHERE dateID = " + tempJoinedID;

                ResultSet resultSet3 = statement3.executeQuery(query3);

                while(resultSet3.next()) {
                    dateJoined.setYear(resultSet3.getInt("Year"));
                    dateJoined.setMonth(resultSet3.getInt("Month"));
                    dateJoined.setDay(resultSet3.getInt("Day"));
                }

                tempRespondent.setBirthdate(birthDate);
                tempRespondent.setDateJoined(dateJoined);
            }

            connection.close();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }

        return tempRespondent;
    }

    public static ArrayList<Respondent> fetchAllRespondents() {
        ArrayList<Respondent> allRespondent = new ArrayList();
        Respondent tempRespondent = new Respondent();
        int tempUserID, tempBirthID, tempJoinedID;
        String tempUsername, tempaccountPassword, tempsecurityQuestion, tempsecurityPassword, tempName, tempEmail;
        boolean notAnUpdate = true;
        DateClass birthDate = new DateClass(), dateJoined = new DateClass();

        try (Connection connection = createConnection();
             Statement statement = connection.createStatement();) {

             // Execute a Query
            String query = "SELECT * " +
                            "FROM ProgramUser P " +
                            "JOIN Respondent R ON P.userID = R.userID " +
                            "WHERE userType = 3 ";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                tempRespondent = new Respondent();
                tempUserID = resultSet.getInt("userID");
                tempUsername = resultSet.getString("username");
                tempaccountPassword = resultSet.getString("accountPassword");
                tempsecurityQuestion = resultSet.getString("securityQuestion");
                tempsecurityPassword = resultSet.getString("securityPassword");
                tempName = resultSet.getString("name");
                tempEmail = resultSet.getString("emailAddress");
                tempBirthID = resultSet.getInt("birthDateID");
                tempJoinedID = resultSet.getInt("dateJoinedID");

                tempRespondent.setAccountPassword(tempaccountPassword, notAnUpdate);
                tempRespondent.setSecurityQuestion(tempsecurityQuestion, notAnUpdate);
                tempRespondent.setSecurityPassword(tempsecurityPassword, notAnUpdate);
                tempRespondent.setUsername(tempUsername);
                tempRespondent.setUserID(tempUserID);
                tempRespondent.setUserType(3);
                tempRespondent.setName(tempName);
                tempRespondent.setEmailAddress(tempEmail);
                tempRespondent.setEmailAddress(tempEmail);

                String query2 = "SELECT * " +
                            "FROM Date " +
                            "WHERE dateID = " + tempBirthID;

                ResultSet resultSet2 = statement.executeQuery(query2);

                while(resultSet2.next()) {
                    birthDate.setYear(resultSet2.getInt("Year"));
                    birthDate.setMonth(resultSet2.getInt("Month"));
                    birthDate.setDay(resultSet2.getInt("Day"));
                }

                String query3 = "SELECT * " +
                            "FROM Date " +
                            "WHERE dateID = " + tempJoinedID;

                ResultSet resultSet3 = statement.executeQuery(query3);

                while(resultSet3.next()) {
                    dateJoined.setYear(resultSet3.getInt("Year"));
                    dateJoined.setMonth(resultSet3.getInt("Month"));
                    dateJoined.setDay(resultSet3.getInt("Day"));
                }

                tempRespondent.setBirthdate(birthDate);
                tempRespondent.setDateJoined(dateJoined);
                allRespondent.add(tempRespondent);
            }

            connection.close();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }

        return allRespondent;
    }

    // SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setBirthdate(DateClass birthdate) {
        this.birthdate = birthdate;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    // GETTERS
    public String getName() {
        return name;
    }

    public DateClass getBirthdate() {
        return birthdate;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public int getAge() {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        int age = year - birthdate.getYear();

        boolean flag;

        if (year >= birthdate.getYear()) {
            if (year == birthdate.getYear()) {
                if (month >= birthdate.getMonth()) {
                    if (month == birthdate.getMonth()) {
                        if (day >= birthdate.getDay()) flag = true;
                        else flag = false;
                    } else flag = true;
                } else flag = false;
            } else flag = true;
        } else flag = false;

        if (!flag) age -= 1;
        return age;
    }
}
