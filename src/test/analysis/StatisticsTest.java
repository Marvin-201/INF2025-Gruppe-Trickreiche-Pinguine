package test.analysis;

import analysis.Statistics;

public class StatisticsTest {
    public static void main(String[] args) {
        testMeanCalculation();
        testVarianceCalculation();

        System.out.println("\nAlle Tests erfolgreich!");
    }

    public static void testMeanCalculation(){
        double[] values = {0.2, 0.5, 0.7, 0.9, 0.4};
        double expected = 0.54;
        double result = Statistics.calculateMean(values);

        assertEquals(expected, result, "korrekte Mittelwertberechnung");
    }

    public static void testVarianceCalculation(){
        double[] values = {0.2, 0.5, 0.7, 0.9, 0.4};
        double expected = 0.073;
        double result = Statistics.calculateVariance(values);

        assertEquals(expected, result, "korrekte Varianzberechnung");
    }

    private static void assertEquals(double expected, double result, String testName) {

        double epsilon = 1e-9;

        if (!(Math.abs(expected - result) < epsilon)) {
            throw new AssertionError(
                "\nTest fehlgeschlagen: " + testName +
                "\nErwartet: " + expected +
                "\nErhalten: " + result
            );
        }

        System.out.println(testName + " erfolgreich");
    }
}
