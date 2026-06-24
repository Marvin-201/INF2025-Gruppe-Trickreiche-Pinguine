package analysis;

public class HistogramAnalyzer {
    public static int[] createHistogram(double[] values, int bins){
        int[] histogramData = new int[bins];
        double binWidth = 1.0/bins;
        
        for (double value : values) {
            int bin = (int) (value/binWidth);

            if(bin == bins){
                bin = bins - 1;
            }

            histogramData[bin]++;
        }

        return histogramData;
    }
}
