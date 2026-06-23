package controller;

import analysis.HistogramAnalyzer;
import rng.LCG;
import rng.MiddleSquareGenerator;
import rng.RNG;
import rng.RandomSequenceGenerator;

public class SimulationController {
    public static SimulationResult runSimulation(String rngName, int sampleSize, int bins){

        RNG rng;

        RandomSequenceGenerator randomSequenceGenerator = new RandomSequenceGenerator();

        switch (rngName){
            case "LCG":
                rng = new LCG(sampleSize);
                break;
            
            case "MiddleSquareGenerator":
                rng = new MiddleSquareGenerator(sampleSize);
                break;

            default: 
                throw new IllegalArgumentException(
                    rngName + " ist kein valider Generatorenname!"
                );
        }

        double[] randomSequence = randomSequenceGenerator.generate(rng, sampleSize);
        int[] histogramData = HistogramAnalyzer.createHistogram(randomSequence, bins);

        SimulationResult simulationResult = new SimulationResult(randomSequence, 0, 0, 0, 0, histogramData);

        return simulationResult;
    }
}
