package test.analysis;

import analysis.PeriodAnalyzer;
import rng.LCG;
import rng.MiddleSquareGenerator;

public class PeriodAnalyzerTest {

    public static void main(String[] args) {

        testLCGNoPeriodFound();
        testMiddleSquarePeriodOne();

        System.out.println("Alle PeriodAnalyzer-Tests bestanden.");
    }

    /**
     * Der LCG besitzt eine extrem große Periode.
     * Innerhalb von 10.000 Iterationen sollte daher
     * keine Wiederholung auftreten.
     */
    private static void testLCGNoPeriodFound() {

        LCG rng = new LCG(12345);

        int period = PeriodAnalyzer.analyze(rng, 10_000);

        assertEquals(-1, period, "LCG: keine Periode innerhalb von 10000 Iterationen");
    }

    /**
     * Seed = 0 führt beim Middle-Square-Generator
     * sofort in einen Zyklus der Länge 1.
     */
    private static void testMiddleSquarePeriodOne() {

        MiddleSquareGenerator rng = new MiddleSquareGenerator(0);

        int period = PeriodAnalyzer.analyze(rng, 100);

        assertEquals(1, period, "MiddleSquare: Periode 1 erwartet");
    }

    private static void assertEquals(int expected, int actual, String message) {

        if (expected != actual) {
            throw new AssertionError("\nTest fehlgeschlagen: " + message + "\nErwartet: " + expected + "\nErhalten: " + actual);
        }
    }
}