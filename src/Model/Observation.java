package Model;

import DatabaseConnection.*;

import java.time.LocalDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.time.LocalDate;

public class Observation extends DatabaseConnection{
    private String username;
    private int CSorTagIdentifier; // 1 - tag, 2 - cs
    private int CSorTagID;
    private int instanceID;
    private String CSorTag;
    private DateClass dateAdded;
    
    public void setInstanceID(int x) {
        instanceID = x;
    }

    public int getInstanceID() {
        return instanceID;
    }

    public int setCSorTagIdentifier() {
        return CSorTagIdentifier;
    }

    public void setCSorTagIdentifier(int x) {
        CSorTagIdentifier = x;
    }

    public DateClass getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(DateClass x) {
        dateAdded = x;
    }


    public Observation(String username, String CSorTag, int CSorTagIdentifier, int instanceID) {
        this.username = username;
        this.instanceID = instanceID;
        this.CSorTag = CSorTag;
        this.CSorTagIdentifier = CSorTagIdentifier;
        LocalDate today = LocalDate.now();
        dateAdded = new DateClass(today.getMonthValue(), today.getDayOfMonth(), today.getYear()); 
        
        createQuery();
    }

    @Override
    public void createQuery() {
        LocalDate today = LocalDate.now();
        dateAdded = new DateClass(today.getMonthValue(), today.getDayOfMonth(), today.getYear()); 

        String insertDateQuery = "INSERT INTO `Date` (`Year`, `Month`, `Day`) " + "VALUES (?, ?, ?)";
        String insertTagQuery = "INSERT INTO `tags` (`instanceID`, `analystID`, `dateAddedID`, `tagName`) " + "VALUES (?, ?, ?, ?)";
        String insertCSQuery = "INSERT INTO `commentSummary` (`instanceID`, `analystID`, `dateAddedID`, `summary`) " + "VALUES (?, ?, ?, ?)";
        
        try (Connection connection = createConnection();
             Statement statement = connection.createStatement();
             PreparedStatement dateStatement = connection.prepareStatement(insertDateQuery);
             PreparedStatement tagStatement = connection.prepareStatement(insertTagQuery);
             PreparedStatement CSStatement = connection.prepareStatement(insertCSQuery);) {
            
            String query1 = "SELECT * FROM Date";
            ResultSet resultSet1 = statement.executeQuery(query1);

            String query2 = "SELECT * FROM ProgramUser WHERE userType = 2" + 
                            "AND username = \"" + username + "\"";
            ResultSet resultSet2 = statement.executeQuery(query2);

            int day, month, year, cDateID = 0, analystID = 1;
            boolean cFlag = false;
            LocalDate date = LocalDate.now();

            while (resultSet1.next() && !cFlag) {
                cDateID = resultSet1.getInt("dateID");
                day = resultSet1.getInt("Day");
                month = resultSet1.getInt("Month");
                year = resultSet1.getInt("Year");

                if(date.getDayOfMonth() == day && date.getMonthValue() == month && date.getYear() == year){
                    cFlag = true;
                }
            }

            while (resultSet2.next()) {
                analystID = resultSet2.getInt("analystID");
            }

            if(cFlag == false){
                dateStatement.setInt(1, date.getYear());
                dateStatement.setInt(2, date.getMonthValue());
                dateStatement.setInt(3, date.getDayOfMonth());
                dateStatement.executeUpdate();
                cDateID += 1;
            }

            if(CSorTagIdentifier == 1) {
                tagStatement.setInt(1, instanceID);
                tagStatement.setInt(2, analystID);
                tagStatement.setInt(3, cDateID);
                tagStatement.setString(4, CSorTag);
                tagStatement.executeUpdate();
            }
            else { 
                CSStatement.setInt(1, instanceID);
                CSStatement.setInt(2, analystID);
                CSStatement.setInt(3, cDateID);
                CSStatement.setString(4, CSorTag);
                CSStatement.executeUpdate();
            }

            connection.close();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }

    public void setUsername(String username) { this.username = username; }
    public void setCommentSummaryOrTag(String cs) { this.CSorTag = cs; } 
    public void CSorTagID(int CSorTagID) { this.CSorTagID = CSorTagID; } 
    
    public String getUsername() { return username; }
    public String getCSorTag() { return CSorTag; }
    public int getCSorTagID() { return CSorTagID; }
}