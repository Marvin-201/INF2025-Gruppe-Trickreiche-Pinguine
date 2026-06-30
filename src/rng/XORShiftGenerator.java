
package rng;

public class XORShiftGenerator implements RNG               //Generator
{
    private static final String NAME = "XORShift Generator";

    private long seed;

    public XORShiftGenerator(long seed)
    {
        this.seed = seed;                                               //XOR Arbeitsweise
    }                                                                   // 0 | 0 = 0
                                                                        // 0 | 1 = 1
                                                                        // 1 | 0 = 1
    @Override                                                           // 1 | 1 = 0
    public double nextDouble()
                                //0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0011 0000 0011 1001 = 12345
    {
        seed ^= (seed << 13);   //0000 0000 0000 0000 0000 0000 0000 0000 0000 0110 0000 0111 0010 0000 0000 0000 =
        //Nach den XOR wird es    0000 0000 0000 0000 0000 0000 0000 0000 0000 0110 0000 0111 0001 0000 0011 1001
        seed ^= (seed >>> 7);
        seed ^= (seed << 17);

        long positiveSeed = seed & Long.MAX_VALUE;

        return positiveSeed / (double) Long.MAX_VALUE;
    }

    @Override
    public void reset(long seed)
    {
        this.seed = seed;
    }

    @Override
    public  String getName()
    {
        return NAME;
    }

    @Override
    public long getState()
    {
        return seed;

    }
}
