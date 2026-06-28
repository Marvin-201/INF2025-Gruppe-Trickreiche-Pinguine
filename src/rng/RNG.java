package rng;

public interface RNG {              //Gemeinsame Schnittstelle

    double nextDouble();

    void reset(long seed);

    String getName();
}