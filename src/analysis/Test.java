package analysis;

public class Test {
    public static void main(String[] args) {
        double[] test = new double[5];
        test[0]=0.15;
        test[1]=0.91;
        test[2]=0.56;
        test[3]=0.33;
        test[4]=0.89;

        int[] histTest = HistogramAnalyzer.createHistogram(test, 10);
        int i=0;
        for (int binValue : histTest) {
            System.out.println(i + ": " + binValue);
            i++;
        }
    }
}
