package rng;

public class MiddleSquareGenerator implements RNG {

    private static final String NAME = "Middle Square Generator";

    private long seed;          //Generator speichert aktuellen Zustand

    public MiddleSquareGenerator(long seed) {
        this.seed = seed;
    }

    @Override
    public double nextDouble() {
        seed = seed * seed;             //seed²

        String number = Long.toString(seed);

        while (number.length() < 8) {
            number = "0" + number;
        }

        int middle = number.length() / 2;

        String middleDigits = number.substring(middle - 2, middle + 2);

        seed = Long.parseLong(middleDigits);

        return seed / 10000.0;
    }

    @Override
    public void reset(long seed) {
        this.seed = seed;
    }

    @Override
    public String getName() {
        return NAME;
    }

}