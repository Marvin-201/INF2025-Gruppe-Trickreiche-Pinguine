package rng;

import java.util.Arrays;

public class TestRNG
{
    public static void main(String[] args)                          //Der RandomSequenceGenerator braucht nur ein Objekt vom Typ RNG.
                                                                    // Ob dahinter ein LCG oder ein MiddleSquareGenerator steckt,
                                                                    // ist egal, weil beide das Interface RNG implementieren.
    {
        RandomSequenceGenerator generator = new RandomSequenceGenerator();

        RNG lcg=new LCG(12345);
        double[] lcgValues = generator.generate(lcg, 10);

        System.out.println(lcg.getName());
        System.out.println(Arrays.toString(lcgValues));

        RNG middleSquare = new MiddleSquareGenerator(12345);
        double[] middleSquareValues = generator.generate(middleSquare, 10);

        System.out.println(middleSquare.getName());
        System.out.println(Arrays.toString(middleSquareValues));

        RNG xorShift = new XORShiftGenerator(12345);
        double[] xorShiftValues = generator.generate(xorShift, 10);

        System.out.println(xorShift.getName());
        System.out.println(Arrays.toString(xorShiftValues));
    }
}