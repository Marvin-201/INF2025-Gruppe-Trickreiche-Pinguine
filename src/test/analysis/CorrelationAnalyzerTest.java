package test.analysis;

import analysis.CorrelationAnalyzer;
import analysis.ScatterPoint;

public class CorrelationAnalyzerTest {

    public static void main(String[] args) {
        testCreateScatterData();
        System.out.println("Alle Tests erfolgreich.");
    }

    private static void testCreateScatterData() {
        double[] values = {0.1, 0.5, 0.9, 0.2};

        ScatterPoint[] result =
                CorrelationAnalyzer.createScatterData(values);

        assertEquals(3, result.length, "Anzahl der ScatterPoints");

        assertEquals(0.1, result[0].getX(), "Punkt 0 X");
        assertEquals(0.5, result[0].getY(), "Punkt 0 Y");

        assertEquals(0.5, result[1].getX(), "Punkt 1 X");
        assertEquals(0.9, result[1].getY(), "Punkt 1 Y");

        assertEquals(0.9, result[2].getX(), "Punkt 2 X");
        assertEquals(0.2, result[2].getY(), "Punkt 2 Y");
    }

    private static void assertEquals(double expected, double actual, String message) {
        if (Math.abs(expected - actual) > 1e-9) {
            throw new AssertionError(
                "Test fehlgeschlagen: " + message +
                "\nErwartet: " + expected +
                "\nErhalten: " + actual);
        }
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(
                "Test fehlgeschlagen: " + message +
                "\nErwartet: " + expected +
                "\nErhalten: " + actual);
        }
    }
}