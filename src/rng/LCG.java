package rng;

public class LCG implements RNG {

    //Attributes
    private static final String NAME = "Linear Congruental Generator (LCG)";  //name of the algorithm

    //Getter
    @Override
    public String getName() {
        return NAME;
    }
}
