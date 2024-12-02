package ServiceClassPackage;

import Model.*;
import java.util.ArrayList;
import java.util.Collections;

public class ServiceClass {
    public void zTestComputation(Instance[] instances) {
        // Step 2: Flatten all survey means into a single list for population calculations
        ArrayList<Double> allSurveyMeans = new ArrayList<>();
        for (Instance instance : instances) {
            if (!instance.getSurveyMeans().isEmpty()) 
                allSurveyMeans.addAll(instance.getSurveyMeans());
        }

        // Step 3: Calculate population mean
        double populationMean = calculateMean(allSurveyMeans);

        // Step 4: Calculate population standard deviation
        double populationStdDev = calculateStandardDeviation(allSurveyMeans, populationMean);

        for (int i = 0; i < Constants.MAX_INSTANCE; i++) {
            if (!instances[i].getSurveyMeans().isEmpty()) {
                if (!instances[i].getSurveys().isEmpty()) {
                    double instanceMean = instances[i].getSampleMean();
                    instances[i].setZScore((instanceMean - populationMean) / populationStdDev);
                    instances[i].setUpdated(true);
                }
            }
        }
    }
    // calculate the mean of a list
    private static double calculateMean(ArrayList<Double> data) {
        double sum = 0.0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.size();
    }
    //calculate the standard deviation of a list
    private static double calculateStandardDeviation(ArrayList<Double> data, double mean) {
        double sumSquaredDiffs = 0.0;
        for (double value : data) {
            sumSquaredDiffs += Math.pow(value - mean, 2);
        }
        return Math.sqrt(sumSquaredDiffs / data.size());
    }


    // sorting/filter data
    public static ArrayList<Instance> FilterData(Instance[] instances, ArrayList<String> places, ArrayList<String> days, ArrayList<String> times) {
        ArrayList<Instance> temp = new ArrayList<>();
        for (int i=0; i<Constants.MAX_INSTANCE; i++ ) {
            if (places.contains(instances[i].getPlaceName()) && days.contains(instances[i].getDayName()) && times.contains(instances[i].getTimeName()) && instances[i].getSampleSize() != 0) {
                temp.add(instances[i]);
            }
        }
        return temp;
    }

    public static ArrayList<Instance> RankData(Instance[] instances, ArrayList<String> places, 
                         ArrayList<String> days, ArrayList<String> times, String recommendation, int nRec) {
        ArrayList<Instance> temp = new ArrayList<>();
        int counter = 0;
        for (int i=0; (i<Constants.MAX_INSTANCE) && (counter <= nRec); i++ ) {
            if (places.contains(instances[i].getPlaceName()) && days.contains(instances[i].getDayName()) && times.contains(instances[i].getTimeName()) && instances[i].getSampleSize() != 0) {
                temp.add(instances[i]);
                counter++;
            }
        }

        // sort
        if ( recommendation.equals("Best to worst") ) {
            Collections.sort(temp, (Instance o1, Instance o2) -> Double.compare(o1.getZScore(), o2.getZScore()));
        } else {
            Collections.sort(temp, (Instance o1, Instance o2) -> Double.compare(o2.getZScore(), o1.getZScore()));
        }

        return temp;
    }

}