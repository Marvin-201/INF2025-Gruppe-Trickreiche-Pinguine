package analysis;

public class HistogramAnalyzer {
    public static int[] createHistogram(double[] values, int bins){
        
        // in diesem Array wird für jede Säule des Histogramms der zugehörige Wert gespeichert
        int[] histogramData = new int[bins];

        // Berechnung der Intervallbreite, nach der die Werte in das Rückgabearray sortiert werden
        double binWidth = 1.0/bins;
        
        // Sortierung der Zufallszahlen nach den Intervallen
        for (double value : values) {
            int bin = (int) (value/binWidth); //erzwungene Ganzzahldivision

            //ordnet 1.0 dem letzten Intervall zu
            if(bin == bins){
                bin = bins - 1; 
            }

            histogramData[bin]++;
        }

        return histogramData;
    }
}
