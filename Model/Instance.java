package Model;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Instance extends TimeCategory {
    private int instanceID;
    
    private double zScore;
    private double sampleMean;
    private int sampleSize; // serves as the counter for survey as well

    private Place place;
    private Day day;
    private TimeCategory time;

    private ArrayList<Survey> surveys;
    private ArrayList<CommentSummaryAndTag> tags;
    private ArrayList<CommentSummaryAndTag> summaries;
    private ArrayList<IncidentReport> incidentReports;


    public Instance(Place place, Day day, TimeCategory time) {
        this.place = place;
        this.day = day;
        this.time = time;

        // add to database
    }

    public Instance() {

    }


    public int getInstanceID() { return instanceID; }
    public void setInstanceID(int instanceID) { this.instanceID = instanceID; }

    public double getZScore() { return zScore; }
    public void setZScore(double zScore) { this.zScore = zScore; }

    public double getSampleMean() { return sampleMean; }
    public void setSampleMean(double sampleMean) { this.sampleMean = sampleMean; }

    public int getSampleSize() { return sampleSize; }
    public void setSampleSize(int sampleSize) { this.sampleSize = sampleSize; }

    public Place getPlace() { return place; }
    public void setPlace(Place place) { this.place = place; }

    public Day getDay() { return day; }
    public void setDay(Day day) { this.day = day; }

    public TimeCategory getTime() { return time; }
    public void setTime(TimeCategory time) { this.time = time; }

    public ArrayList<Survey> getSurveys() { return surveys; }
    public void setSurveys(ArrayList<Survey> surveys) { this.surveys = surveys; }

    public ArrayList<CommentSummaryAndTag> getTags() { return tags; }
    public void setTags(ArrayList<CommentSummaryAndTag> tags) { this.tags = tags; }

    public ArrayList<CommentSummaryAndTag> getSummaries() { return summaries; }
    public void setSummaries(ArrayList<CommentSummaryAndTag> summaries) { this.summaries = summaries; }


    public static void initializeInstances(Instance[] instances) {
        // populate from database
    }


    public void addIncidentReport(IncidentReport incidentReport) {
        incidentReports.add(incidentReport);
        // add to database
    }


    public int getIncidentReportCount() {
        return incidentReports.size();
    } 

    public void addCommentSummary(CommentSummaryAndTag summary) {
        this.summaries.add(summary);
        // add to database
    }



    public void addTag(CommentSummaryAndTag tag) {
        tags.add(tag);
        // add to database then get there the id and set local variable
    }

    public void deleteTags(ArrayList<Integer> tagToDelete) {
        // if magmatch yung id, delete
        // parse then delete in database
    }

    public void deleteSummaries(ArrayList<Integer> summaryIDsToDelete) {
        // if magmatch yung id, delete 
        // parse then delete in database
    }


    public void takeSurvey(Survey survey) {
        this.surveys.add(survey);
        this.sampleSize++;
    }

    public static ArrayList<Survey> fetchByRespondentUsername(String username) {
        // find sa database
        return null;
    }
    
    public static ArrayList<Survey> fetchAnalystSurveyData() {
        // fetch ALL result
        // return all survey
        return null;
    }

    public static ArrayList<Survey> fetchGeneralData(ArrayList<String> places, ArrayList<String> days, ArrayList<String> times, int rankingType, int numRec) {
        
        // return filtered Arraylist<Survey>
        return null;
    }

    public double calculateInstanceMean() {
        double sum = 0.0;
        for (Survey cSurvey : surveys) {
            sum += cSurvey.computeSurveyMean();
        }
        return sum / surveys.size();  // Return the mean of the group
    }

    public ArrayList<Double> getSurveyMeans() {
        ArrayList<Double> newSurveyMeans = new ArrayList<>();
        for (Survey cSurvey : surveys) {
            newSurveyMeans.add(cSurvey.getSurveyMean());
        }
        return newSurveyMeans;
    }

    public String getInterpretation() {
        if (zScore >= 0) { // if positive
            return "NOT SAFE";
        }
        else
            return "SAFE";
    }


}