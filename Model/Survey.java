package Model;

import java.util.ArrayList;

public class Survey {
    private int[] answers = new int[20];
    private double surveyMean;
    private String comment;
    private DateClass dateTaken;
    private String respondentUsername;
    private String placeName;
    private String dayName;
    private String timeName;


    public Survey(int[] answers, String respondentUsername, String comment, DateClass dateTaken, String place, String day, String time) {
        this.answers = answers;
        this.comment = comment;
        this.dateTaken = dateTaken;
        this.respondentUsername = respondentUsername;
        this.placeName = place;
        this.dayName = day;
        this.timeName = time;
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

    public String getPlaceName() { return placeName; }
    public void setPlaceName(String placeName) { this.placeName = placeName; }
    
    public String getDayName() { return dayName; }
    public void setDayName(String dayName) { this.dayName = dayName; }
    
    public String getTimeName() { return timeName; }
    public void setTimeName(String timeName) { this.timeName = timeName; }

    public static ArrayList<Survey> fetchByRespondentId(String respondentID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }    
}

