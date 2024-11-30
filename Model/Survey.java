package Model;

import java.util.ArrayList;

public class Survey {
    private int[] answers = new int[20];
    private double surveyMean;
    private String comment;
    private DateClass dateTaken;
    private String respondentUsername;
    private int instanceID;

    public int getInstanceID() {
        return instanceID;
    }

    public void setInstanceID(int x) {
        instanceID = x;
    }

    public Survey(int[] answers, String respondentUsername, String comment, DateClass dateTaken, Int id) {
        this.answers = answers;
        this.comment = comment;
        this.dateTaken = dateTaken;
        this.respondentUsername = respondentUsername;
        this.instanceID = id;
        double sum = 0;
        for (int num : answers) {
            sum += num;
        }
        this.surveyMean = sum/20;
    }


    public void setRespondentUsername(String username) { respondentUsername = username; }
    public void setAnswers(int[] answers) { this.answers = answers; }
    public void setComment(String comment) { this.comment = comment; }
    public void setDateTaken(DateClass date) { dateTaken = date; }

    public int[] getAnswers() { return answers; }
    public String getComment() { return comment; }
    public DateClass getDateTaken() { return dateTaken; }
    public double getSurveyMean() { return surveyMean; }
    public String getRespondentUsername() { return respondentUsername; }

    public String getTimeName() { return timeName; }
    public void setTimeName(String timeName) { this.timeName = timeName; }

    public static ArrayList<Survey> fetchByRespondentId(String respondentID) {
        // sa survey na table, query lahat ng may ganon, also match yung IDS ng instance names
        
        throw new UnsupportedOperationException("Not supported yet.");
    }    
}

