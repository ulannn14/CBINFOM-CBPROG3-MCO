package ServiceClassPackage;

import Model.*;
import java.util.ArrayList;

public class ServiceClass {
    public void zTestComputation(Instance[] instances) {
        // Step 2: Flatten all survey means into a single list for population calculations

        
        ArrayList<Double> allSurveyMeans = new ArrayList<>();
        for (Instance instance : instances) {
            allSurveyMeans.addAll(instance.getSurveyMeans());
        }

        // Step 3: Calculate population mean
        double populationMean = calculateMean(allSurveyMeans);

        // Step 4: Calculate population standard deviation
        double populationStdDev = calculateStandardDeviation(allSurveyMeans, populationMean);

        for (int i = 0; i < Constants.MAX_INSTANCE; i++) {
            if (instances[i].getSurveys().size() > 0) {
                double instanceMean = instances[i].calculateInstanceMean();
                instances[i].setZScore((instanceMean - populationMean) / populationStdDev);
                instances[i].setSampleSize(instances[i].getSurveys().size());
            }
        }
    }

    // Helper method to calculate the mean of a list
    private static double calculateMean(ArrayList<Double> data) {
        double sum = 0.0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.size();
    }

    
    // Helper method to calculate the standard deviation of a list
    private static double calculateStandardDeviation(ArrayList<Double> data, double mean) {
        double sumSquaredDiffs = 0.0;
        for (double value : data) {
            sumSquaredDiffs += Math.pow(value - mean, 2);
        }
        return Math.sqrt(sumSquaredDiffs / data.size());
    }

}