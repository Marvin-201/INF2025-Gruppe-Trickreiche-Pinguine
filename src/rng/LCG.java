package rng;

public class LCG implements RNG {

    //Attributes
    private static final String NAME = "Linear Congruential Generator (LCG)";  //name of the algorithm

    //Getter
    @Override
    public String getName()
    {
        return NAME;
    }

    private long seed;

    private static final long A = 1664525;
    private static final long C = 1013904223;
    private static final long M = (long) Math.pow(2, 32);

    public LCG(long seed)
    {
        this.seed = seed;
    }

    @Override
    public double nextDouble()
    {
        seed = (A * seed + C) % M;
        return (double) seed / M;
    }

    @Override
    public void reset(long seed) {
        this.seed = seed;
    }

    @Override
    public long getState(){
        return seed;
    }
}
