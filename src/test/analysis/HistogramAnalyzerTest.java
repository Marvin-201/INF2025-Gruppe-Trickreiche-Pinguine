package test.analysis;

import analysis.HistogramAnalyzer;
import java.util.Arrays;

public class HistogramAnalyzerTest {

    public static void main(String[] args) {
        testGleichmaessigeVerteilung();
        testWertEins();
        testLeeresArray();
        testAlleImSelbenBin();

        System.out.println("\nAlle Tests erfolgreich.");
    }

    private static void testGleichmaessigeVerteilung() {
        double[] values = {0.1, 0.3, 0.5, 0.7, 0.9};
        int[] result = HistogramAnalyzer.createHistogram(values, 5);
        int[] expected = {1, 1, 1, 1, 1};

        assertArrayEquals(expected, result, "Gleichmäßige Verteilung");
    }

    private static void testWertEins() {
        double[] values = {1.0};

        int[] result = HistogramAnalyzer.createHistogram(values, 5);

        int[] expected = {0, 0, 0, 0, 1};

        assertArrayEquals(expected, result, "Wert 1.0 muss im letzten Bin landen");
    }

    private static void testLeeresArray() {
        double[] values = {};

        int[] result = HistogramAnalyzer.createHistogram(values, 4);

        int[] expected = {0, 0, 0, 0};

        assertArrayEquals(expected, result, "Leeres Array");
    }

    private static void testAlleImSelbenBin() {
        double[] values = {0.01, 0.05, 0.08};

        int[] result = HistogramAnalyzer.createHistogram(values, 5);

        int[] expected = {3, 0, 0, 0, 0};

        assertArrayEquals(expected, result, "Alle Werte im ersten Bin");
    }

    private static void assertArrayEquals(int[] expected, int[] actual, String testName) {

        if (!Arrays.equals(expected, actual)) {
            throw new AssertionError(
                "\nTest fehlgeschlagen: " + testName +
                "\nErwartet: " + Arrays.toString(expected) +
                "\nErhalten: " + Arrays.toString(actual)
            );
        }

        System.out.println(testName + " erfolgreich");
    }
}
