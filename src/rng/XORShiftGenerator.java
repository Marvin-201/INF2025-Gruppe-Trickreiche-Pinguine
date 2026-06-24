package rng;

public class XORShiftGenerator implements RNG               //Generator
{
    private static final String NAME = "XORShift Generator";

    private long seed;

    public XORShiftGenerator(long seed)
    {
        this.seed = seed;
    }

    @Override
    public double nextDouble()          //Ja
    {
        seed ^= (seed << 13);               //bits verschieben
        seed ^= (seed >>> 7);
        seed ^= (seed << 17);

        long positiveSeed = seed & Long.MAX_VALUE;          //Betrag operation

        return positiveSeed / (double) Long.MAX_VALUE;
    }

    @Override
    public void reset(long seed)            //Ja
    {
        this.seed = seed;
    }

    @Override
    public String getName()                 //ja
    {
        return NAME;
    }
}

