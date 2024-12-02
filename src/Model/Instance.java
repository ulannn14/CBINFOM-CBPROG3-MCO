package Model;

import ServiceClassPackage.Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Instance extends TimeCategory {
    private int instanceID;
    
    private double zScore;
    private double sampleMean;
    private int sampleSize; // serves as the counter for survey as well

    private Place place;
    private Day day;
    private TimeCategory time;

    private ArrayList<Survey> surveys = new ArrayList<>();
    private ArrayList<Observation> tags = new ArrayList<>();
    private ArrayList<Observation> summaries = new ArrayList<>();
    private ArrayList<IncidentReport> incidentReports = new ArrayList<>();

    private boolean updated;

    public boolean getUpdated() {
        return updated;
    }

    public void setUpdated(Boolean flag) {
        updated = flag;
    }

    public Instance(Place place, Day day, TimeCategory time) {
        this.place = place;
        this.day = day;
        this.time = time;
        this.surveys = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.summaries = new ArrayList<>();
        this.incidentReports = new ArrayList<>();
    }

    public Instance() {

    }

    public ArrayList<Survey> getSurveys() {
        if (surveys.isEmpty()) {
            return new ArrayList<>();  // Return an empty ArrayList if surveys is null
        }
        return surveys;  // Return the surveys if not null
    }

    public String getInstancePlaceName() { return place.getPlaceName(); }
    public String getInstanceDayName() { return day.getDayName(); }
    public String getInstanceTimeName() { return time.getTimeName(); }

    public int getInstanceID() { return instanceID; }
    public void setInstanceID(int instanceID) { this.instanceID = instanceID; }

    public double getZScore() { return zScore; }
    public void setZScore(double zScore) { this.zScore = zScore; }

    public void setSampleMean(double sampleMean) { this.sampleMean = sampleMean; }

    public int getSampleSize() { return sampleSize; }
    public void setSampleSize(int sampleSize) { this.sampleSize = surveys.size(); }

    public Place getPlace() { return place; }
    public void setPlace(Place place) { this.place = place; }

    public Day getDay() { return day; }
    public void setDay(Day day) { this.day = day; }

    public TimeCategory getTime() { return time; }
    public void setTime(TimeCategory time) { this.time = time; }

    public ArrayList<Double> getSurveyMeans() {
        ArrayList<Double> newSurveyMeans = new ArrayList<>();        
        if (!surveys.isEmpty()) {
            for (Survey cSurvey : surveys) {
                newSurveyMeans.add(cSurvey.getSurveyMean());
            }
        }
        return newSurveyMeans;
    }
    
    public void setSurveys(ArrayList<Survey> surveys) { this.surveys = surveys; }

    public ArrayList<Observation> getTags() {
        if (tags.isEmpty()) {
            tags = new ArrayList<>(); // Return an empty list if it's null
        }
        return tags;
    }   
    public void setTags(ArrayList<Observation> tags) { this.tags = tags; }

    public ArrayList<Observation> getSummaries() {
        if (summaries.isEmpty()) {
            summaries = new ArrayList<>(); // Return an empty list if it's null
        }
        return summaries;
    }    
    public void setSummaries(ArrayList<Observation> summaries) { this.summaries = summaries; }


    public static Instance[] initializeInstances() {
        Instance[] instances = new Instance[Constants.MAX_INSTANCE];
        
        try (Connection connection = createConnection();
            Statement statement = connection.createStatement()) {
            System.out.println("CONNECTED TO DATABASE! ! ! ");
            statement.executeUpdate("USE ISSPa");

            String query = "SELECT * " +
                        "FROM Instance I " +
                        "JOIN Streets S ON S.streetID = I.streetID " +
                        "JOIN Day D ON I.dayID = D.dayID " +
                        "JOIN TimeCategory T ON I.timeID = T.timeCategoryID " +
                        "ORDER BY instanceID";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int tempID = (resultSet.getInt("instanceID") - 1);
                instances[tempID] = new Instance();
                instances[tempID].setInstanceID(tempID);

                double tempzScore = resultSet.getDouble("zScore");
                instances[tempID].setZScore(tempzScore);

                double tempSampleMean = resultSet.getDouble("sampleMean");
                instances[tempID].setSampleMean(tempSampleMean);

                int tempSampleSize = resultSet.getInt("sampleSize");
                instances[tempID].setSampleSize(tempSampleSize);

                int tempStreetID = (resultSet.getInt("streetID") - 1);
                String tempStreetName = resultSet.getString("streetName");
                Place place = new Place(tempStreetID, tempStreetName);

                int tempDayID = (resultSet.getInt("dayID") - 1);
                String tempDayName = resultSet.getString("dayName");
                Day day = new Day(tempDayID, tempDayName);

                int tempTimeID = (resultSet.getInt("timeID") - 1);
                String tempTimeName = resultSet.getString("timeCategoryName");
                TimeCategory time = new TimeCategory(tempTimeID, tempTimeName);

                instances[tempID].setPlace(place);
                instances[tempID].setDay(day);
                instances[tempID].setTime(time);
            }
        } catch (SQLException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }

        return instances;
    }


    public void addIncidentReport(IncidentReport incidentReport) {
        incidentReports.add(incidentReport);

        String sender = incidentReport.getSender();
        String recipient = incidentReport.getRecipient();
        String body = incidentReport.getBody();
        DateClass dateCreated = incidentReport.getDateCreated();
        
        String insertIncidentReportQuery = "INSERT INTO `IncidentReport` (`instanceID`, `dateWrittenID`, `toField`, `fromField`, `body`) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        String insertDateQuery = "INSERT INTO `Date` (`Year`, `Month`, `Day`) " + "VALUES (?, ?, ?);";

        try (Connection connection = createConnection();
             PreparedStatement incidentReportStatement = connection.prepareStatement(insertIncidentReportQuery);
             PreparedStatement dateStatement = connection.prepareStatement(insertDateQuery);
             Statement statement = connection.createStatement();) {
            
            String query = "SELECT * FROM Date";
            ResultSet resultSet = statement.executeQuery(query);

            boolean dFlag = false;
            int dateID = 0, day, month, year;

            while (resultSet.next() && !dFlag) {
                dateID = resultSet.getInt("dateID");
                day = resultSet.getInt("Day");
                month = resultSet.getInt("Month");
                year = resultSet.getInt("Year");

                if(dateCreated.getDay() == day && dateCreated.getMonth() == month && dateCreated.getYear() == year){
                    dFlag = true;
                }
            }

            if(dFlag == false){
                dateStatement.setInt(1, dateCreated.getYear());
                dateStatement.setInt(2, dateCreated.getMonth());
                dateStatement.setInt(3, dateCreated.getDay());
                dateStatement.executeUpdate();
                dateID += 1;
            }

            incidentReportStatement.setInt(1, instanceID);
            incidentReportStatement.setInt(2, dateID);
            incidentReportStatement.setString(3, recipient);
            incidentReportStatement.setString(4, sender); 
            incidentReportStatement.setString(5, body);

            incidentReportStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }


    public int getIncidentReportCount() {
        return incidentReports.size();
    } 

    public void addCommentSummary(Observation summary) {
        summary.setCSorTagIdentifier(2);
        summaries.add(summary);
        
        String username = summary.getUsername();
        int CSorTagID = summary.getCSorTagID();
        DateClass dateAdded = summary.getDateAdded(); 
        String CSorTag = summary.getCSorTag();
        
        String insertCommentSummaryQuery = "INSERT INTO `commentSummary` (`instanceID`, `analystID`, `analystID`, `summary`) "
                + "VALUES (?, ?, ?, ?)";
        String insertDateQuery = "INSERT INTO `Date` (`Year`, `Month`, `Day`) " + "VALUES (?, ?, ?);";

        try (Connection connection = createConnection();
             PreparedStatement insertCommentSummaryStatement = connection.prepareStatement(insertCommentSummaryQuery);
             PreparedStatement dateStatement = connection.prepareStatement(insertDateQuery);
             Statement statement = connection.createStatement();) {

            // Execute a Query
            String query1 = "SELECT userID " +
                            "FROM ProgramUser " +
                            "WHERE username = \"" + username + "\"";
            ResultSet resultSet1 = statement.executeQuery(query1);

            String query2 = "SELECT * FROM Date";
            ResultSet resultSet2 = statement.executeQuery(query2);
            
            int userID = -1;
            while (resultSet1.next()) {
                userID = resultSet1.getInt("userID");
            }

            boolean dFlag = false;
            int dateID = 0, d, m, y;

            while (resultSet2.next() && !dFlag) {
                dateID = resultSet2.getInt("dateID");
                d = resultSet2.getInt("Day");
                m = resultSet2.getInt("Month");
                y = resultSet2.getInt("Year");

                if(dateAdded.getDay() == d && dateAdded.getMonth() == m && dateAdded.getYear() == y){
                    dFlag = true;
                }
            }

            if(dFlag == false){
                dateStatement.setInt(1, dateAdded.getYear());
                dateStatement.setInt(2, dateAdded.getMonth());
                dateStatement.setInt(3, dateAdded.getDay());
                dateStatement.executeUpdate();
                dateID += 1;
            }

            insertCommentSummaryStatement.setInt(1, instanceID);
            insertCommentSummaryStatement.setInt(2, userID); 
            insertCommentSummaryStatement.setInt(3, dateID);    
            insertCommentSummaryStatement.setString(4, CSorTag);  
            connection.close();        
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
            
    }

    public void addTag(Observation tag) {
        tag.setCSorTagIdentifier(1);
        tags.add(tag);
        
        String username = tag.getUsername();
        int CSorTagID = tag.getCSorTagID();
        DateClass dateAdded = tag.getDateAdded(); 
        String CSorTag = tag.getCSorTag();
        
        String insertCommentSummaryQuery = "INSERT INTO `tags` (`instanceID`, `analystID`, `dateAddedID`, `tagName`) "
                + "VALUES (?, ?, ?, ?)";
        String insertDateQuery = "INSERT INTO `Date` (`Year`, `Month`, `Day`) " + "VALUES (?, ?, ?);";

        try (Connection connection = createConnection();
             PreparedStatement insertCommentSummaryStatement = connection.prepareStatement(insertCommentSummaryQuery);
             PreparedStatement dateStatement = connection.prepareStatement(insertDateQuery);
             Statement statement = connection.createStatement();) {

            // Execute a Query
            String query1 = "SELECT userID " +
                            "FROM ProgramUser " +
                            "WHERE username = \"" + username + "\"";
            ResultSet resultSet1 = statement.executeQuery(query1);

            String query2 = "SELECT * FROM Date";
            ResultSet resultSet2 = statement.executeQuery(query2);
            
            int userID = -1;
            while (resultSet1.next()) {
                userID = resultSet1.getInt("userID");
            }

            boolean dFlag = false;
            int dateID = 0, day, month, year;

            while (resultSet2.next() && !dFlag) {
                dateID = resultSet2.getInt("dateID");
                day = resultSet2.getInt("Day");
                month = resultSet2.getInt("Month");
                year = resultSet2.getInt("Year");

                if(dateAdded.getDay() == day && dateAdded.getMonth() == month && dateAdded.getYear() == year){
                    dFlag = true;
                }
            }

            if(dFlag == false){
                dateStatement.setInt(1, dateAdded.getYear());
                dateStatement.setInt(2, dateAdded.getMonth());
                dateStatement.setInt(3, dateAdded.getDay());
                dateStatement.executeUpdate();
                dateID += 1;
            }

            insertCommentSummaryStatement.setInt(1, instanceID);
            insertCommentSummaryStatement.setInt(2, userID); 
            insertCommentSummaryStatement.setInt(3, dateID);    
            insertCommentSummaryStatement.setString(4, CSorTag);    
            connection.close();      
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }

    public void deleteTags(ArrayList<Integer> tagToDelete) {
        String deleteTagQuery = "DELETE FROM tags WHERE tagID = ?";

        try (Connection connection = createConnection();
            PreparedStatement deleteTagStatement = connection.prepareStatement(deleteTagQuery);) {
                for(int i = 0; i < tagToDelete.size(); i++){
                    for(int j = 0; j < tags.size(); j++){
                        if(tagToDelete.get(i) == tags.get(j).getCSorTagID()) {
                            deleteTagStatement.setInt(1, j);
                            tags.remove(j);
                        }
                    }
                }
            deleteTagStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }

    public void deleteSummaries(ArrayList<Integer> summaryIDsToDelete) {
        String deleteSummaryQuery = "DELETE FROM commentSummary WHERE commentSummaryID = ?";

        try (Connection connection = createConnection();
            PreparedStatement deleteCommentSummaryStatement = connection.prepareStatement(deleteSummaryQuery);) {
                for(int i = 0; i < summaryIDsToDelete.size(); i++){
                    for(int j = 0; j < summaries.size(); j++){
                        if(summaryIDsToDelete.get(i) == summaries.get(j).getCSorTagID()) {
                            deleteCommentSummaryStatement.setInt(1, j);
                            summaries.remove(j);
                        }
                    }
                }
            deleteCommentSummaryStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }


    public void takeSurvey(Survey survey) {
        this.surveys.add(survey);
        int instanceID = survey.getInstanceID();
        String comment = survey.getComment();
        double surveyMean = survey.getSurveyMean();
        String username = survey.getRespondentUsername();
        DateClass dateTaken = survey.getDateTaken();

        String insertSurveyQuery = "INSERT INTO `Survey` (`respondentID`, `instanceID`, `comment`, `surveyDateTakenID`, `surveyMean`, `respondentUsername`) " 
        + "VALUES (?, ?, ?, ?, ?, ?)";
        String insertDateQuery = "INSERT INTO `Date` (`Year`, `Month`, `Day`) " + "VALUES (?, ?, ?, ?);";

        try (Connection connection = createConnection();
             PreparedStatement insertSurveyStatement = connection.prepareStatement(insertSurveyQuery);
             PreparedStatement dateStatement = connection.prepareStatement(insertDateQuery);
             Statement statement1 = connection.createStatement();
             Statement statement2 = connection.createStatement();) {

                String query2 = "SELECT * FROM Date";
                ResultSet resultSet2 = statement2.executeQuery(query2);

                boolean dFlag = false;
                int dateID = 0, day, month, year;

                while (resultSet2.next() && !dFlag) {
                    dateID = resultSet2.getInt("dateID");
                    day = resultSet2.getInt("Day");
                    month = resultSet2.getInt("Month");
                    year = resultSet2.getInt("Year");

                    if(dateTaken.getDay() == day && dateTaken.getMonth() == month && dateTaken.getYear() == year){
                        dFlag = true;
                    }
                }

            if(dFlag == false){
                dateStatement.setInt(1, dateTaken.getYear());
                dateStatement.setInt(2, dateTaken.getMonth());
                dateStatement.setInt(3, dateTaken.getDay());
                dateStatement.executeUpdate();
                dateID += 1;
            }

                String query1 = "SELECT userID " +
                            "FROM ProgramUser " +
                            "WHERE username = \"" + username + "\"";
                ResultSet resultSet1 = statement1.executeQuery(query1);

                
                int userID = -1;
                while (resultSet1.next()) {
                    userID = resultSet1.getInt("userID");
                }

                insertSurveyStatement.setInt(1, userID);        
                insertSurveyStatement.setInt(2, instanceID);
                insertSurveyStatement.setString(3, comment);
                insertSurveyStatement.setInt(4, dateID);
                insertSurveyStatement.setDouble(5, surveyMean);
                insertSurveyStatement.setString(6, username);

                connection.close();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }

    public double getSampleMean() {
        double temp = 0;  
        for (int i = 0; i < this.sampleSize; i++)
            if (surveys != null) {
                temp += this.surveys.get(i).getSurveyMean();
            }
        return (double) temp / this.sampleSize; 
    }

    public static ArrayList<Survey> fetchByRespondentUsername(String username) {
        ArrayList<Survey> surveys = new ArrayList<>();
        Survey tempSurvey = new Survey();

        try (Connection connection = createConnection();
             Statement statement = connection.createStatement();) {

            // Execute a Query
            String query1 = "SELECT * " +
                            "FROM Survey S " +
                            "JOIN ProgramUser P ON P.userID = S.respondentID " +
                            "JOIN answers A ON S.surveyID = A.surveyID " + 
                            "WHERE P.username = \"" + username + "\"";

            ResultSet resultSet1 = statement.executeQuery(query1);

            String query2 = "SELECT * " +
                            "FROM answers A ";
            
            ResultSet resultSet2 = statement.executeQuery(query2);

            
            int[] answers = new int[20];
            int answerCtr = 0, surveyID, surveyDateTakenID, instanceID;
            double surveyMean;
            String comment;
            DateClass dateTaken = new DateClass();
            String respondentUsername;
            
            while (resultSet1.next()) {
                respondentUsername = resultSet1.getString("respondentUsername");
                surveyMean = resultSet1.getDouble("surveyMean");
                comment = resultSet1.getString("comment");
                surveyDateTakenID = resultSet1.getInt("surveyDateTakenID");
                instanceID = resultSet1.getInt("instanceID");

                while(resultSet2.next() && answerCtr != 20 && (resultSet1.getInt("surveyID") == resultSet2.getInt("surveyID"))){
                    answers[answerCtr] = resultSet2.getInt("answer");
                    answerCtr++;
                }
                answerCtr = 0;

                String query3 = "SELECT * " +
                            "FROM Date " +
                            "WHERE dateID = " + surveyDateTakenID;

                ResultSet resultSet3 = statement.executeQuery(query3);

                while(resultSet3.next()) {
                    dateTaken.setYear(resultSet3.getInt("Year"));
                    dateTaken.setMonth(resultSet3.getInt("Month"));
                    dateTaken.setDay(resultSet3.getInt("Day"));
                }

                tempSurvey.setRespondentUsername(respondentUsername);
                tempSurvey.setAnswers(answers);
                tempSurvey.setComment(comment);
                tempSurvey.setDateTaken(dateTaken);
                tempSurvey.setInstanceID(instanceID);
                surveys.add(tempSurvey);
            }
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }

        if(surveys.isEmpty())
            return null;
        else
            return surveys;
    }
    
    public static ArrayList<Survey> fetchAnalystSurveyData() {
        ArrayList<Survey> surveys = new ArrayList<>();
        Survey tempSurvey = new Survey();

        try (Connection connection = createConnection();
             Statement statement = connection.createStatement();) {

            // Execute a Query
            String query1 = "SELECT * " +
                            "FROM Survey S " +
                            "JOIN ProgramUser P ON P.userID = S.respondentID " +
                            "JOIN answers A ON S.surveyID = A.surveyID ";

            ResultSet resultSet1 = statement.executeQuery(query1);

            String query2 = "SELECT * " +
                            "FROM answers A ";
            
            ResultSet resultSet2 = statement.executeQuery(query2);

            
            int[] answers = new int[20];
            int answerCtr = 0, surveyID, surveyDateTakenID, instanceID;
            double surveyMean;
            String comment;
            DateClass dateTaken = new DateClass();
            String respondentUsername;
            
            while (resultSet1.next()) {
                respondentUsername = resultSet1.getString("respondentUsername");
                surveyMean = resultSet1.getDouble("surveyMean");
                comment = resultSet1.getString("comment");
                surveyDateTakenID = resultSet1.getInt("surveyDateTakenID");
                instanceID = resultSet1.getInt("instanceID");

                while(resultSet2.next() && answerCtr != 20 && (resultSet1.getInt("surveyID") == resultSet2.getInt("surveyID"))){
                    answers[answerCtr] = resultSet2.getInt("answer");
                    answerCtr++;
                }
                answerCtr = 0;

                String query3 = "SELECT * " +
                            "FROM Date " +
                            "WHERE dateID = " + surveyDateTakenID;

                ResultSet resultSet3 = statement.executeQuery(query3);

                while(resultSet3.next()) {
                    dateTaken.setYear(resultSet3.getInt("Year"));
                    dateTaken.setMonth(resultSet3.getInt("Month"));
                    dateTaken.setDay(resultSet3.getInt("Day"));
                }

                tempSurvey.setRespondentUsername(respondentUsername);
                tempSurvey.setAnswers(answers);
                tempSurvey.setComment(comment);
                tempSurvey.setDateTaken(dateTaken);
                tempSurvey.setInstanceID(instanceID);
                surveys.add(tempSurvey);
            }
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }

        if(surveys.size() == 0)
            return null;
        else
            return surveys;
    }

    public double calculateInstanceMean() {
        double sum = 0.0;
        for (Survey cSurvey : surveys) {
            sum += cSurvey.getSurveyMean();
        }
        return sum / surveys.size();  // Return the mean of the group
    }

    public String getInterpretation() {
        if (zScore >= 0) { // if positive
            return "NOT SAFE";
        }
        else
            return "SAFE";
    }
}