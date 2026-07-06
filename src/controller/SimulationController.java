package controller;

import analysis.CorrelationAnalyzer;
import analysis.HistogramAnalyzer;
import analysis.PeriodAnalyzer;
import analysis.ScatterPoint;
import analysis.Statistics;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import rng.LCG;
import rng.MersenneTwister;
import rng.MiddleSquareGenerator;
import rng.RNG;
import rng.RandomSequenceGenerator;
import rng.XORShiftGenerator;

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
        RNG periodRng;

        RandomSequenceGenerator randomSequenceGenerator = new RandomSequenceGenerator();

        //initialisierung des angegebenen RNG
        switch (rngName){
            case "LCG":
                rng = new LCG(seed);
                periodRng = new LCG(seed);
                break;
            
            case "MiddleSquareGenerator":
                rng = new MiddleSquareGenerator(seed);
                periodRng = new MiddleSquareGenerator(seed);
                break;

            case "XORShiftGenerator":
                rng= new XORShiftGenerator(seed);
                periodRng = new XORShiftGenerator(seed);
                break;

            case "MersenneTwister":
                rng = new MersenneTwister(seed);
                periodRng = new MersenneTwister(seed);
                break;

            default: 
                throw new IllegalArgumentException(
                    rngName + " ist kein valider Generatorenname!"
                );
        }



        //generieren einer Sequenz von Zufallszahlen, der Länge sampleSize
        double[] randomSequence = randomSequenceGenerator.generate(rng, sampleSize);



        //Berechnen der Analysen mit Multithreading
        ExecutorService executor = Executors.newFixedThreadPool(5);

        try {
            Future<int[]> histogramFuture = executor.submit(() ->
                    HistogramAnalyzer.createHistogram(randomSequence, bins));

            Future<Double> meanFuture = executor.submit(() ->
                    Statistics.calculateMean(randomSequence));

            Future<Double> varianceFuture = executor.submit(() ->
                    Statistics.calculateVariance(randomSequence));

            Future<ScatterPoint[]> correlationFuture = executor.submit(() ->
                    CorrelationAnalyzer.createScatterData(randomSequence));

            // Für die Periodenanalyse besser eine neue RNG-Instanz verwenden!
            

            Future<Integer> periodFuture = executor.submit(() ->
                    PeriodAnalyzer.analyze(periodRng, 1_000_000));

            // Ergebnisse abholen
            int[] histogramData = histogramFuture.get();
            double mean = meanFuture.get();
            double variance = varianceFuture.get();
            ScatterPoint[] correlationData = correlationFuture.get();
            int period = periodFuture.get();

            return new SimulationResult(randomSequence, mean, variance, correlationData, period, histogramData);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Simulation wurde unterbrochen.", e);

        } catch (ExecutionException e) {
            throw new RuntimeException("Fehler während der Simulation.", e.getCause());

        } finally {
            executor.shutdown();
        }
    }
}
