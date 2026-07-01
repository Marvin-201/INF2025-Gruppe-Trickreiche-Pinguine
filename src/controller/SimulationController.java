package controller;

import analysis.CorrelationAnalyzer;
import analysis.HistogramAnalyzer;
import analysis.PeriodAnalyzer;
import analysis.ScatterPoint;
import analysis.Statistics;
import rng.LCG;
import rng.MersenneTwister;
import rng.MiddleSquareGenerator;
import rng.RandomSequenceGenerator;
import rng.XORShiftGenerator;
import rng.RNG;

public class SimulationController {   
    public static SimulationResult runSimulation(String rngName, int seed, int sampleSize, int bins){

        if (seed <= 0) {
            throw new IllegalArgumentException("seed darf nicht 0 sein.");
        }

        if (sampleSize <= 0) {
            throw new IllegalArgumentException("sampleSize muss größer als 0 sein.");
        }

        if (bins <= 0) {
            throw new IllegalArgumentException("bins muss größer als 0 sein.");
        }

        RNG rng;

        RandomSequenceGenerator randomSequenceGenerator = new RandomSequenceGenerator();

        //initialisierung des angegebenen RNG
        switch (rngName){
            case "LCG":
                rng = new LCG(seed);
                break;
            
            case "MiddleSquareGenerator":
                rng = new MiddleSquareGenerator(seed);
                break;

            case "XORShiftGenerator":
                rng= new XORShiftGenerator(seed);
                break;

            case "MersenneTwister":
                rng = new MersenneTwister(seed);
                break;

            default: 
                throw new IllegalArgumentException(
                    rngName + " ist kein valider Generatorenname!"
                );
        }

        //generieren einer Sequenz von Zufallszahlen, der Länge sampleSize
        double[] randomSequence = randomSequenceGenerator.generate(rng, sampleSize);

        //Histogramm-Analyse der erzeugten Zufallssequenz
        int[] histogramData = HistogramAnalyzer.createHistogram(randomSequence, bins);

        //Mittelwert der erzeugten Werte wird berechnet -- 0,5 spricht für Gleichverteilung
        double mean = Statistics.calculateMean(randomSequence);

        //Varianz der erzeugten Werte wird berechnet
        double variance = Statistics.calculateVariance(randomSequence);

        //Erstellt die Korrelationsdaten der erzeugten Werte -- es sollte bei der Darstellung kein Muster erkennbar sein
        ScatterPoint[] correlationData = CorrelationAnalyzer.createScatterData(randomSequence);

        //Periodenanalyse des Zufallszahlengenerators
        int period = PeriodAnalyzer.analyze(rng, 1_000_000); // period = -1 bedeutet dass in den maxIterations keine periode gefunden wurde, in diesem Fall wären also die ersten 1_000_000 erzeugten Zahlen verschieden

        //initialisierung eines SimulationResults mit den erstellten Daten
        SimulationResult simulationResult = new SimulationResult(randomSequence, mean, variance, correlationData, period, histogramData); 

        return simulationResult;
    }
}
