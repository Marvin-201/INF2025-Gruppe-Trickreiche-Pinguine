package test.controller;

import controller.SimulationController;
import controller.SimulationResult;

public class SimulationControllerTest {

    public static void main(String[] args) {

        //Aufrufen der Tests
        testLCGSimulation();
        testMiddleSquareSimulation();
        testXORShiftSimulation();
        testMersenneTwisterSimulation();
        testInvalidGenerator();
        testInvalidParameters();

        System.out.println("\nAlle Tests erfolgreich.");
    }

    // Testet Simulation mit LCG
    private static void testLCGSimulation() {

        int sampleSize = 100;
        int bins = 10;
        int seed = 123457;

        SimulationResult result = SimulationController.runSimulation("LCG", seed, sampleSize, bins);

        // Prüft ob die Simulation ein Ergebnis zurückgegeben hat das nicht null ist
        assertNotNull(result, "LCG liefert Ergebnis");

        // Prüft ob die Anzahl der erzeugten Zufallszahlen der angegebenen sampleSize entspricht
        assertEquals(sampleSize, result.getValues().length, "LCG Sequenzgröße");

        // Prüft ob die Intervallanzahl der Histogrammanalyse mit dem angegebenen Wert bins übereinstimmt
        assertEquals(bins, result.getHistogramData().length, "LCG Histogrammgröße");

        // Prüft ob die Periode einen gültigen Wert hat
        assertTrue(result.getPeriod() == -1 || result.getPeriod() > 0, "LCG Periodenwert gültig");
        
        // schreibt alle erzeugten Zufallszahlen in die Konsole und anschließend die Anzahl der Zahlen die in jedem der Histogramm-Intervalle liegen. ZUR VERANSCHAULICHUNG
        /* double[] values = result.getValues();
        int[] histogramData = result.getHistogramData();

        for (double value : values) {
            System.out.println(value);
        }

        double i = 0.0;
        double binWidth = 1.0/bins;

        for (int binData : histogramData) {
            System.out.printf("%.3f-%.3f: ", i, i + binWidth);
            System.out.println(binData);
            i += binWidth;
        } */
        
    }

    // Testet Simulation mit MiddleSquareGenerator
    private static void testMiddleSquareSimulation() {

        int sampleSize = 100;
        int bins = 10;
        int seed = 123457;

        SimulationResult result = SimulationController.runSimulation("MiddleSquareGenerator", seed, sampleSize, bins);

        // Prüft ob die Simulation ein Ergebnis zurückgegeben hat das nicht null ist
        assertNotNull(result, "MiddleSquare liefert Ergebnis");

        // Prüft ob die Anzahl der erzeugten Zufallszahlen der angegebenen sampleSize entspricht
        assertEquals(sampleSize, result.getValues().length, "MiddleSquare Sequenzgröße");

        // Prüft ob die Intervallanzahl der Histogrammanalyse mit dem angegebenen Wert bins übereinstimmt
        assertEquals(bins, result.getHistogramData().length, "MiddleSquare Histogrammgröße");

        // Prüft ob die Periode einen gültigen Wert hat
        assertTrue(result.getPeriod() == -1 || result.getPeriod() > 0, "MiddleSquare Periodenwert gültig");

         
        // schreibt alle erzeugten Zufallszahlen in die Konsole und anschließend die Anzahl der Zahlen die in jedem der Histogramm-Intervalle liegen. ZUR VERANSCHAULICHUNG
        /* double[] values = result.getValues();
        int[] histogramData = result.getHistogramData();

        for (double value : values) {
            System.out.println(value);
        }

        double i = 0.0;
        double binWidth = 1.0/bins;

        for (int binData : histogramData) {
            System.out.printf("%.3f-%.3f: ", i, i + binWidth);
            System.out.println(binData);
            i += binWidth;
        } */
        
    }
    private static void testXORShiftSimulation() {

        int sampleSize = 100;
        int bins = 10;
        int seed = 12345;

        SimulationResult result = SimulationController.runSimulation("XORShiftGenerator", seed, sampleSize, bins);

        // Prüft ob die Simulation ein Ergebnis zurückgegeben hat das nicht null ist
        assertNotNull(result, "XORShift liefert Ergebnis");

        // Prüft ob die Anzahl der erzeugten Zufallszahlen der angegebenen sampleSize entspricht
        assertEquals(sampleSize, result.getValues().length, "XORShift Sequenzgröße");

        // Prüft ob die Intervallanzahl der Histogrammanalyse mit dem angegebenen Wert bins übereinstimmt
        assertEquals(bins, result.getHistogramData().length, "XORShift Histogrammgröße");

        // Prüft ob die Periode einen gültigen Wert hat
        assertTrue(result.getPeriod() == -1 || result.getPeriod() > 0, "XORShift Periodenwert gültig");
        // schreibt alle erzeugten Zufallszahlen in die Konsole und anschließend die Anzahl der Zahlen die in jedem der Histogramm-Intervalle liegen. ZUR VERANSCHAULICHUNG
        double[] values = result.getValues();
        int[] histogramData = result.getHistogramData();

        /*for (double value : values) {
            System.out.println(value);
        }

        double i = 0.0;
        double binWidth = 1.0/bins;

        for (int binData : histogramData) {
            System.out.printf("%.3f-%.3f: ", i, i + binWidth);
            System.out.println(binData);
            i += binWidth;
        }
        */
    }

    private static void testMersenneTwisterSimulation()
    {
        int sampleSize = 100;
        int bins = 10;
        int seed = 123457;

        SimulationResult result = SimulationController.runSimulation(
                "MersenneTwister", seed, sampleSize, bins
        );

        // Prüft, ob die Simulation ein Ergebnis zurückgegeben hat, das nicht null ist
        assertNotNull(result, "MersenneTwister liefert Ergebnis");

        // Prüft, ob die Anzahl der erzeugten Zufallszahlen der sampleSize entspricht
        assertEquals(
                sampleSize,
                result.getValues().length,
                "MersenneTwister Sequenzgröße"
        );

        // Prüft, ob die Anzahl der Histogramm-Intervalle dem Wert bins entspricht
        assertEquals(
                bins,
                result.getHistogramData().length,
                "MersenneTwister Histogrammgröße"
        );

        // Prüft, ob die Periodenanalyse einen gültigen Wert zurückgibt
        assertTrue(
                result.getPeriod() == -1 || result.getPeriod() > 0,
                "MersenneTwister Periodenwert gültig"
        );
        // schreibt alle erzeugten Zufallszahlen in die Konsole und anschließend die Anzahl der Zahlen die in jedem der Histogramm-Intervalle liegen. ZUR VERANSCHAULICHUNG
        double[] values = result.getValues();
        int[] histogramData = result.getHistogramData();

        for (double value : values) {
            System.out.println(value);
        }

        double i = 0.0;
        double binWidth = 1.0/bins;

        for (int binData : histogramData) {
            System.out.printf("%.3f-%.3f: ", i, i + binWidth);
            System.out.println(binData);
            i += binWidth;
        }
    }

    // Testet den Fall dass ein ungültiger Generatorname in runSimulation übergeben wird
    private static void testInvalidGenerator() {

        try {

            // startet Simulation mit ungültigem Generatorname
            SimulationController.runSimulation("Ungueltig",123456, 100, 10);

            //
            throw new AssertionError("Es wurde keine Exception geworfen.");

        } catch (IllegalArgumentException e) {
            System.out.println("erfolgreicher Test: Ungültiger Generator löst Exception aus");
        }
    }

    // Testet ob ungültige Parameter (<=0) korrekt behandelt werden
    private static void testInvalidParameters() {

        expectIllegalArgumentException("LCG", 0, 100, 10, "seed = 0");
        expectIllegalArgumentException("LCG", -1, 100, 10, "seed < 0");
        expectIllegalArgumentException("LCG", 123456, 0, 10, "sampleSize = 0");
        expectIllegalArgumentException("LCG", 123456, -100, 10, "sampleSize < 0");
        expectIllegalArgumentException("LCG", 123456, 100, 0, "bins = 0");
        expectIllegalArgumentException("LCG", 123456, 100, -10, "bins < 0");
    }


    private static void expectIllegalArgumentException(String generator, int seed, int sampleSize, int bins, String testName) {
        try {
            // Startet Simulation mit ungültigem Parameter
            SimulationController.runSimulation(generator, seed, sampleSize, bins);

            //wenn keine IllegalArgumentException ausgelöst wird, ist der Test fehlgeschlagen:
            throw new AssertionError("Test fehlgeschlagen: " + testName);

        } catch (IllegalArgumentException e) {
           
            // Ungültiger Parameter sollte IllegalArgumentException auslösen, dann ist der Test erfolgreich:
            System.out.println("erfolgreicher Test: " + testName);
        }
    }


    // =========================== HILFSMETHODEN ===========================


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

    private static void assertTrue(boolean condition, String testName) {

        if (!condition) {
            throw new AssertionError(
                "\nTest fehlgeschlagen: " + testName
            );
        }

        System.out.println("erfolgreicher Test: " + testName);
    }
}
