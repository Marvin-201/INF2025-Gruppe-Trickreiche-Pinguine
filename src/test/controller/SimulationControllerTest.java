package test.controller;

import controller.SimulationController;
import controller.SimulationResult;

public class SimulationControllerTest {

    public static void main(String[] args) {

        testLCGSimulation();
        testMiddleSquareSimulation();
        testInvalidGenerator();

        System.out.println("\nAlle Tests erfolgreich.");
    }

    private static void testLCGSimulation() {

        int sampleSize = 100;
        int bins = 10;

        SimulationResult result = SimulationController.runSimulation("LCG", sampleSize, bins);

        assertNotNull(result, "LCG liefert Ergebnis");

        assertEquals(sampleSize, result.getValues().length, "LCG Sequenzgröße");

        assertEquals(bins, result.getHistogramData().length, "LCG Histogrammgröße");
        
        /* 
        double[] values = result.getValues();
        int[] histogramData = result.getHistogramData();

        for (double value : values) {
            System.out.println(value);
        }

        for (int binData : histogramData) {
            System.out.println(binData);
        }
        */
    }

    private static void testMiddleSquareSimulation() {

        int sampleSize = 100;
        int bins = 10;

        SimulationResult result = SimulationController.runSimulation("MiddleSquareGenerator", sampleSize, bins);

        assertNotNull(result, "MiddleSquare liefert Ergebnis");

        assertEquals(sampleSize, result.getValues().length, "MiddleSquare Sequenzgröße");

        assertEquals(bins, result.getHistogramData().length, "MiddleSquare Histogrammgröße");
    }

    private static void testInvalidGenerator() {

        try {

            SimulationController.runSimulation("Ungueltig", 100, 10);

            throw new AssertionError("Es wurde keine Exception geworfen.");

        } catch (IllegalArgumentException e) {
            System.out.println("erfolgreicher Test: Ungültiger Generator löst Exception aus");
        }
    }

    private static void assertEquals(int expected, int actual, String testName) {

        if (expected != actual) {

            throw new AssertionError("\nTest fehlgeschlagen: " + testName + "\nErwartet: " + expected + "\nErhalten: " + actual);

        }

        System.out.println("erfolgreicher Test: " + testName);
    }

    private static void assertNotNull(Object obj, String testName) {

        if (obj == null) {

            throw new AssertionError("\nTest fehlgeschlagen: " + testName + "\nObjekt ist null");
        }

        System.out.println("erfolgreicher Test: " + testName);
    }
}
