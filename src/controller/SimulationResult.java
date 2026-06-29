package controller;

public class SimulationResult {
    private final double[] values;
    private final double mean;
    private final double variance;
    private final double correlation;
    private final long period;
    private final int[] histogramData;

    public SimulationResult(double[] values, double mean, double variance, double correlation, long period, int[] histogramData){
        this.values = values;
        this.mean = mean;
        this.variance = variance;
        this.correlation = correlation;
        this.period = period;
        this.histogramData = histogramData;
    }

    public double[] getValues(){return values;}

    public double getMean(){return mean;}

    public double getVariance(){return variance;}

    public double getCorrelation(){return correlation;}

    public double getPeriod(){return period;}

    public int[] getHistogramData(){return histogramData;}
}
