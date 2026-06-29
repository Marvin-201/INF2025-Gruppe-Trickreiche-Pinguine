package rng;

/*
 Implementierung des 32-Bit-Mersenne-Twisters MT19937.

 Der Generator arbeitet mit einem internen Zustand aus 624 Integer-Werten.
 Nach jeweils 624 Ausgaben wird dieser Zustand in twist() neu gemischt.

    Hilfreich ist vor dem Anschauen von meinem Code dieses YouTube Video zu anschauen:
    https://www.youtube.com/watch?v=bd7k037zykY
 */
public class MersenneTwister implements RNG {

    private static final int u = 11;            //Variablem für die Formel
    private static final int s = 7;
    private static final int t = 15;
    private static final int l = 18;

    private static final String NAME = "Mersenne Twister (MT19937)";

    // Größe des internen Zustands des MT19937.
    private static final int last = 624;

    // Abstand zu einem weiteren Zustandswert beim Twist-Schritt.
    private static final int generatedNumbers = 397;

    private static final int MATRIX_A = 0x9908B0DF;

    // Masken zum Trennen des höchsten Bits von den unteren 31 Bits
    private static final int topBorder = 0x80000000;
    private static final int bottomBorder = 0x7FFFFFFF;

    // Anzahl aller möglichen vorzeichenlosen 32-Bit-Werte: 2³²
    private static final double UINT_32_RANGE = 4294967296.0;

    // Enthält den vollständigen internen Zustand des Generators
    private final int[] state = new int[last];

    // Position des nächsten zu Zustandswerts.
    private int index;

    // Anzahl der Werte, die seit dem letzten reset() erzeugt wurden
    private long generatedValues;

    // Der Konstruktor baut aus dem übregebenen Seed den Anfangszustand auf
    public MersenneTwister(long seed) {
        reset(seed);
    }

    @Override
    public double nextDouble() {
        /*
          nextInt() erzeugt ein 32-Bit-Bitmuster. Int wird in einen positiven long
          umgewandelt. Durch die Division durch 2^32 entsteht ein double im Intervall [0, 1)
         */
        return Integer.toUnsignedLong(nextInt()) / UINT_32_RANGE;
    }

    private int nextInt() {
        /*
         Wenn alle 624 Zustandswerte verbraucht sind, muss zuerst ein neuer
         Zustand berechnet werden. Nach reset() ist index ebenfalls 624,
         damit twist() auch vor der ersten Ausgabe ausgeführt wird
         */
        if (index >= last) {
            twist();
        }

        // Nächsten unbearbeiteten Wert aus dem Zustand lesen
        int value = state[index++];


        //XOR und Verschiebeoperationen verteilen die Bits

        value ^= value >>> u;                   //Fülle Links mit 0 auf
        value ^= (value << s) & 0x9D2C5680;
        value ^= (value << t) & 0xEFC60000;
        value ^= value >>> l;

        generatedValues++;
        return value;
    }

    private void twist()
    {
         /*
         Aus den bisherigen 624 Werten wird ein vollständig neuer Zustand
         aufgebaut. Der Wert 2 ist das Ergebniss aus der Rechnung mit Wert 1
         */
        for (int i = 0; i < last; i++)
        {
            /*
             Das höchste Bit von state[i] wird mit den unteren 31 Bits des
             nächsten Werts verbunden. Modulo sorgt dafür, dass nach dem
             letzten Arrayelement wieder das erste verwendet wird.
             */
            int combined = (state[i] & topBorder)
                    | (state[(i + 1) % last] & bottomBorder);

            // Mit dem 397 Positionen entfernten Zustandswert verknüpfen.
            int next = state[(i + generatedNumbers) % last] ^ (combined >>> 1);


            if ((combined & 1) != 0) // Bei einer ungeraden Kombination wird MATRIX_A eingerechnet
            {
                next ^= MATRIX_A;
            }

            state[i] = next;
        }


        index = 0;
    }

    @Override
    public void reset(long seed) {

        state[0] = (int) seed;

        /*
         Aus dem Seed werden die restlichen 623 Zustandswerte berechnet.
         int überläufe sind wie Mod 2³²
         */

        for (int i = 1; i < last; i++)
        {
            int previous = state[i - 1];
            state[i] = 1812433253 * (previous ^ (previous >>> 30)) + i;
        }

        // Vor der nächsten Ausgabe muss der neue Zustand getwistet werden.
        index = last;

        generatedValues = 0;
    }

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    public long getState()
    {
        return generatedValues;
    }
}