package rng;

import java.util.Arrays;

public class TestRNG {
    public static void main(String[] args) {
        RNG rng = new LCG(12345);

        RandomSequenceGenerator generator = new RandomSequenceGenerator();

        double[] values = generator.generate(rng, 10);

        System.out.println(rng.getName());
        System.out.println(Arrays.toString(values));
    }
}