package test.rng;

import java.util.Arrays;
import java.util.Scanner;

import rng.*;

public class RNGTest
{
    public static void main(String[] args)                          //Der RandomSequenceGenerator braucht nur ein Objekt vom Typ RNG.
                                                                    // Ob dahinter ein LCG oder ein MiddleSquareGenerator steckt,
                                                                    // ist egal, weil beide das Interface RNG implementieren.
    {
        RandomSequenceGenerator generator = new RandomSequenceGenerator();

        while (true)
        {
            int zahl;
            System.out.println("Geben sie die Zahl ein:");
            Scanner scanner = new Scanner(System.in);
            zahl = scanner.nextInt();

            RNG lcg=new LCG(zahl);
            double[] lcgValues = generator.generate(lcg, 10);

            System.out.println(lcg.getName());
            System.out.println(Arrays.toString(lcgValues));

            RNG middleSquare = new MiddleSquareGenerator(zahl);
            double[] middleSquareValues = generator.generate(middleSquare, 10);

            System.out.println(middleSquare.getName());
            System.out.println(Arrays.toString(middleSquareValues));

            RNG XORShift= new XORShiftGenerator(zahl);
            double[] XORShiftGeneratorValues = generator.generate(XORShift, 10);

            System.out.println(XORShift.getName());
            System.out.println(Arrays.toString(XORShiftGeneratorValues));


        }
    }
}
