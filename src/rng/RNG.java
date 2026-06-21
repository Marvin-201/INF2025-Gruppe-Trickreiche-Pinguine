package rng;

public interface RNG {              //Gemeinsame Schnittschtelle

    double nextDouble();

    void reset(long seed);

    String getName();
}