package controller;

import analysis.ScatterPoint;

public class SimulationResult {
    private final double[] values;
    private final double mean;
    private final double variance;
    private final ScatterPoint[] correlationData;
    private final long period;
    private final int[] histogramData;

    public SimulationResult(double[] values, double mean, double variance, ScatterPoint[] correlationData, long period, int[] histogramData){
        this.values = values;
        this.mean = mean;
        this.variance = variance;
        this.correlationData = correlationData;
        this.period = period;
        this.histogramData = histogramData;
    }

    public double[] getValues(){return values;}

    public double getMean(){return mean;}

    public double getVariance(){return variance;}

    public ScatterPoint[] getCorrelation(){return correlationData;}

    public double getPeriod(){return period;}

    public int[] getHistogramData(){return histogramData;}
}
