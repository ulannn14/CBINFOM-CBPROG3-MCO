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
                      int userType, String name, DateClass birthdate, String emailAddress) {
        this.username = username;
        this.accountPassword = accountPassword;
        this.securityQuestion = securityQuestion;
        this.securityPassword = securityPassword;
        this.userType = userType;
        this.name = name;
        this.birthdate = birthdate;
        this.emailAddress = emailAddress;

        createQuery();
    }

    @Override
    public void createQuery() {
        String insertRespondentQuery = "INSERT INTO `Respondent` (`name`, `emailAddress`, `birthDateID`, `dateJoinedID`, `surveyHistoryCtr`) "
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

            // Set values for Respondent table
            respondentStatement.setString(1, name);
            respondentStatement.setString(2, emailAddress);
            respondentStatement.setInt(3, bDateID);
            respondentStatement.setInt(4, cDateID); 
            respondentStatement.setInt(5, 0);

            // Set values for ProgramUser table
            programUserStatement.setString(1, username);
            programUserStatement.setString(2, accountPassword);
            programUserStatement.setString(3, securityQuestion);
            programUserStatement.setString(4, securityPassword);
            programUserStatement.setInt(5, userType);

            respondentStatement.executeUpdate();
            programUserStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }

    public Respondent fetchUser(String username, String password) {
        // find the matched username and password of userType = 3 in the database
        // return null if no match,
        // return the whole user if a match is found (populate an instance of the user then return it)
        return null;
    }

    public static ArrayList<Respondent> fetchAllRespondents() {
        // database, return all respondents
        return new ArrayList<>();
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

    public void takeSurvey() {
        // Implement take survey logic here
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
