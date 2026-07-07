package util;

public class NumberConverter {

    public static int[] convertToIntegers(double[] values, int min, int max) {

        int[] result = new int[values.length];

        for (int i = 0; i < values.length; i++) {
            result[i] = min + (int)(values[i] * (max - min + 1));
        }

        return result;
    }
}
