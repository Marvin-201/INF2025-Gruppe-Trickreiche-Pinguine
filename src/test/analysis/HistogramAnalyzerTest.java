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

    // Prüft an einem Beispiel, ob die HistogramAnalyzer Ausgabe korrekt ist (Ausgabe wird mit erwarteter Ausgabe verglichen)
    private static void testGleichmaessigeVerteilung() {
        double[] values = {0.1, 0.3, 0.5, 0.7, 0.9};
        int[] result = HistogramAnalyzer.createHistogram(values, 5);
        int[] expected = {1, 1, 1, 1, 1};

        assertArrayEquals(expected, result, "Gleichmäßige Verteilung");
    }

    // Prüft ob 1.0 richtig einsortiert wird (im letzten Bin)
    private static void testWertEins() {
        double[] values = {1.0};

        int[] result = HistogramAnalyzer.createHistogram(values, 5);

        int[] expected = {0, 0, 0, 0, 1};

        assertArrayEquals(expected, result, "Wert 1.0 muss im letzten Bin landen");
    }

    // Prüft ob bei keinen Werten überall 0 ausgegeben wird
    private static void testLeeresArray() {
        double[] values = {};

        int[] result = HistogramAnalyzer.createHistogram(values, 4);

        int[] expected = {0, 0, 0, 0};

        assertArrayEquals(expected, result, "Leeres Array");
    }

    // Prüft ob mehrere double-Werte im gleichen bin korrekt angezeigt werden
    private static void testAlleImSelbenBin() {
        double[] values = {0.01, 0.05, 0.08};

        int[] result = HistogramAnalyzer.createHistogram(values, 5);

        int[] expected = {3, 0, 0, 0, 0};

        assertArrayEquals(expected, result, "Alle Werte im ersten Bin");
    }

    //vergleicht zwei Arrays. Bei Ungleichheit wird Test fehlgeschlagen ausgegeben, sonst Test erfolgreich
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
