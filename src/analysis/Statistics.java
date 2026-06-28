package analysis;

public class Statistics {
    public static double calculateMean(double[] values){
        double sum = 0.0;

        for (double value : values) {
            sum += value;
        }

        double mean = sum/values.length;

        return mean;
    }

    public static double calculateVariance(double[] values){
        double mean = calculateMean(values);

        double squaredDifferenceSum = 0.0;

        for (double value : values) {
            squaredDifferenceSum += (value - mean) * (value - mean);
        }

        double variance = squaredDifferenceSum/(values.length - 1);

        return variance;
    }
}
