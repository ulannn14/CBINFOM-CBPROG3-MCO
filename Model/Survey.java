package Model;

import java.util.ArrayList;

public class Survey {
    private int[] answers = new int[20];
    private ArrayList<String> comments;

    public double computeSurveyMean() {
        double sum = 0;
        for (int num : answers) {
            sum += num;
        }
        return sum/20; 
    }

    public static ArrayList<Survey> fetchByRespondentId(String respondentID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }    
}

