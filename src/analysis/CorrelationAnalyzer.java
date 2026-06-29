package analysis;

public class CorrelationAnalyzer {
    public static ScatterPoint[] createScatterData(double[] values){
        
        ScatterPoint[] scatterData = new ScatterPoint[values.length-1]; 
        
        // Erzeugt für jede Zufallszahl und ihren Nachfolger einen ScatterPoint. ScatterPoint ist nötig weil Point keinen Konstruktor mit (double, double) hat
        for (int i = 0; i < scatterData.length; i++) {
            scatterData[i] = new ScatterPoint(values[i], values[i+1]);
        }

        return scatterData;
    }
}
