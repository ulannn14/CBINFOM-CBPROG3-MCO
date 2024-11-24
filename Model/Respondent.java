package Model;

import java.sql.PreparedStatement;
import java.sql.Date;
import java.time.LocalDate;

public class Respondent extends ProgramUser{
    private String name;
    private Date birthdate = new Date();
    private String phoneNumber;
    private Survey[] surveyHistory = new Survey[100];
    private int numSurveyHistory = 0;

    public Respondent(String username, String accountPassword, String securityQuestion, String securityPassword, int userType, String name, Date birthdate, String emailAddress){
        this.username = username;
        this.accountPassword = accountPassword;
        this.securityQuestion = securityQuestion;
        this.securityPassword = securityPassword;
        this.userType = userType;
        this.name = name;
        this.birthdate = birthdate;
        this.emailAddress = emailAddress;
        
        void createQuery(){
                // SQL queries for both tables
            String insertRespondentQuery = "INSERT INTO `Respondent` (`name`, `emailAddress`, `birthDate`, `dateJoined`, `surveyHistoryCtr`) "
                    + "VALUES (?, ?, ?, ?, ?)";
            
            String insertProgramUserQuery = "`ProgramUser` (`username`, `accountPassword`, `securityQuestion`, `securityPassword`, `userType`) "
                    + "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement respondentStatement = connection.prepareStatement(insertRespondentQuery);
                 PreparedStatement programUserStatement = connection.prepareStatement(insertProgramUserQuery)) {

                // Set values for Respondent table
                respondentStatement.setString(1, name);
                respondentStatement.setString(2, emailAddress);
                respondentStatement.setDate(3, birthdate);
                respondentStatement.setDate(4, new Date(System.currentTimeMillis()));  // Set current date for 'dateJoined'
                respondentStatement.setInt(5, 0);  // Assuming default surveyHistoryCtr is 0

                // Set values for ProgramUser table
                programUserStatement.setString(1, username);
                programUserStatement.setString(2, accountPassword);
                programUserStatement.setString(3, securityQuestion);
                programUserStatement.setString(4, securityPassword);
                programUserStatement.setInt(5, userType);

                // Execute both queries
                int respondentRowsInserted = respondentStatement.executeUpdate();
                int programUserRowsInserted = programUserStatement.executeUpdate();

                // If both insertions are successful, commit the transaction
                if (respondentRowsInserted > 0 && programUserRowsInserted > 0) {
                    connection.commit();  // Commit transaction
                    System.out.println("A new respondent and program user were inserted successfully!");
                } else {
                    connection.rollback();  // Rollback transaction if anything fails
                    System.out.println("Error: Transaction rolled back.");
                }
            } catch (SQLException e) {
                connection.rollback();  // Rollback in case of error
                System.err.println("Error inserting data: " + e.getMessage());
            }
        } 
        }
    }

    // SETTERS
    public void setName(String name){
        this.name = name;
    }

    public void setBirthdate(Date birthdate){
        this.birthdate = birthdate;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void addSurvey(Survey survey){
        surveyHistory[numSurveyHistory] = new Survey();
        surveyHistory[numSurveyHistory] = survey;
        numSurveyHistory++;
    }

    // GETTERS
    public String getName(){
        return name;
    }

    public Date getBirthdate(){
        return birthdate;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public Date getAge(){
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        int age = year - birthdate.getYear();
        boolean flag;

        if (year >= birthdate.getYear()){
            if (year == birthdate.getYear()){
                if (month >= birthdate.getMonth()){
                    if (month == birthdate.getMonth()){
                        if (day >= birthdate.getDay()) flag = true;
                        else flag = false;
                    } else flag = true;
                } else flag = false;
            } else flag = true
        } else flag = false;
        
        if (!flag) age -= 1;
        return age;
    }

    public Survey getHistory(int historyIdx){
        return surveyHistory[historyIdx];
    }

    public void takeSurvey(){
        
    }
}