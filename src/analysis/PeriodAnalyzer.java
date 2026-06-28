package analysis;

import java.util.HashSet;
import java.util.Set;
import rng.RNG;

public class PeriodAnalyzer {
    public static int analyze(RNG rng, int maxIterations){
        
        Set<Long> seenStates = new HashSet<>();

        while(seenStates.size() < maxIterations){

            long state = rng.getState();

            if(seenStates.contains(state)){
                return seenStates.size();
            }

            seenStates.add(state);
            rng.nextDouble();
            
        }

        return -1;

    }
}
