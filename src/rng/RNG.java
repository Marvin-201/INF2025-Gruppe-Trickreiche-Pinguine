package rng;

public interface RNG {              //Gemeinsame Schnittstelle

    double nextDouble();

    void reset(long seed);

    String getName();

    long getState(); //braucht man für Korrektheit beim Period Analyzer -- Gleitkommazahl Rundungsfehler können zu falschen Ausgaben führen
}