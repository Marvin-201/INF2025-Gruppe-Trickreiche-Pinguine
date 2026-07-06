package rng;

/**
 * Implementierung des 32-Bit-Mersenne-Twisters MT19937.
 *
 * <p>Der Code ist in der Reihenfolge angeordnet, in der der Generator arbeitet:
 * Zustand aus dem Seed aufbauen, eine Zahl anfordern, bei Bedarf den Zustand
 * erneuern und zuletzt die Bits der Zahl mischen (Tempering).</p>
 */
public class MersenneTwister implements RNG {

    private static final int u = 11;            //Variablen für die Formel
    private static final int s = 7;
    private static final int t = 15;
    private static final int l = 18;
    private static final int b = 0x9D2C5680;;
    private static final int c = 0xEFC60000;;

    private static final String NAME = "Mersenne Twister (MT19937)";

    /*
     * Festgelegte Parameter des Algorithmus MT19937:
     *
     * w = 32: Ein Zustandswert hat 32 Bits.
     * n = 624: Der Zustand besteht aus 624 Werten.
     * m = 397: Beim Twist wird ein Wert 397 Stellen weiter verwendet.
     *
     * Die übrigen Konstanten sind Bitmasken und Verschiebungen aus der
     * Definition von MT19937. Sie wurden nicht im Programm ausgerechnet,
     * sondern bestimmen genau diese Variante des Mersenne-Twisters.
     */
    private static final int STATE_SIZE = 624;             // n
    private static final int TWIST_OFFSET = 397;           // m
    private static final int TWIST_MATRIX = 0x9908B0DF;    // a

    private static final int UPPER_BIT_MASK = 0x80000000;
    private static final int LOWER_31_BITS_MASK = 0x7FFFFFFF;

    private static final int TEMPER_SHIFT_U = 11;          // u
    private static final int TEMPER_SHIFT_S = 7;           // s
    private static final int TEMPER_MASK_B = 0x9D2C5680;   // b
    private static final int TEMPER_SHIFT_T = 15;          // t
    private static final int TEMPER_MASK_C = 0xEFC60000;   // c
    private static final int TEMPER_SHIFT_L = 18;          // l

    private static final int SEED_MULTIPLIER = 1812433253;

    // 2^32 mögliche Bitmuster werden auf das Intervall [0, 1) abgebildet.
    private static final double UINT_32_RANGE = 4294967296.0;

    // Der vollständige interne Zustand des Generators.
    private final int[] state = new int[STATE_SIZE];

    // Zeigt auf den Zustandswert, der als Nächstes ausgegeben wird.
    private int index;

    // Anzahl der seit dem letzten reset() ausgegebenen Werte.
    private long generatedValues;

    /**
     * Erstellt den Generator und baut aus dem Seed den ersten Zustand auf.
     */
    public MersenneTwister(long seed) {
        reset(seed);
    }

    /**
     * Schritt 1: Aus dem Seed werden alle 624 Werte des Zustands berechnet.
     */
    @Override
    public void reset(long seed) {
        // MT19937 verwendet einen 32-Bit-Seed. Daher zählen nur die unteren 32 Bits.
        state[0] = (int) seed;

        for (int i = 1; i < STATE_SIZE; i++) {
            int previous = state[i - 1];

            /*
             * Rekursionsformel zur Initialisierung:
             * state[i] = 1812433253 * (previous XOR (previous >>> 30)) + i
             *
             * Ein Java-int läuft bei Bedarf über. Dieser Überlauf entspricht
             * dem von MT19937 verlangten Rechnen modulo 2^32.
             */
            state[i] = SEED_MULTIPLIER
                    * (previous ^ (previous >>> 30))
                    + i;
        }

        /*
         * Der initialisierte Zustand ist noch nicht getwistet. STATE_SIZE sorgt
         * dafür, dass vor der ersten Ausgabe in nextInt32() twist() aufgerufen wird.
         */
        index = STATE_SIZE;
        generatedValues = 0;
    }

    /**
     * Schritt 2: Liefert die nächste Zufallszahl im Intervall [0, 1).
     */
    @Override
    public double nextDouble() {
        int randomBits = nextInt32();

        /*
         * Java-int ist vorzeichenbehaftet. Die Umwandlung in long interpretiert
         * dasselbe 32-Bit-Muster als Zahl von 0 bis 2^32 - 1. Nach der Division
         * durch 2^32 liegt das Ergebnis zwischen 0 einschließlich und 1 ausschließlich.
         */
        long unsignedValue = Integer.toUnsignedLong(randomBits);
        return unsignedValue / UINT_32_RANGE;
    }

    /**
     * Schritt 3: Holt einen 32-Bit-Wert aus dem Zustand.
     */
    private int nextInt32() {
        // Nach 624 Ausgaben muss zuerst ein neuer Zustandsblock berechnet werden.
        if (index >= STATE_SIZE) {
            twist();
        }

        int rawValue = state[index];
        index++;

        generatedValues++;
        return temper(rawValue);
    }

    /**
     * Schritt 4: Berechnet aus den bisherigen 624 Werten einen neuen Zustand.
     */
    private void twist() {
        for (int i = 0; i < STATE_SIZE; i++) {
            int nextIndex = (i + 1) % STATE_SIZE;
            int offsetIndex = (i + TWIST_OFFSET) % STATE_SIZE;

            /*
             * Das höchste Bit von state[i] und die unteren 31 Bits des
             * Folgewerts werden zu einem neuen 32-Bit-Wert zusammengesetzt.
             */
            int combined = (state[i] & UPPER_BIT_MASK)
                    | (state[nextIndex] & LOWER_31_BITS_MASK);

            /*
             * Der zusammengesetzte Wert wird durch 2 geteilt (logischer
             * Rechtsshift) und mit dem 397 Positionen entfernten Wert verknüpft.
             */
            int twistedValue = state[offsetIndex] ^ (combined >>> 1);

            /*
             * Ist combined ungerade, war sein niedrigstes Bit 1. Dieses Bit
             * geht beim Rechtsshift verloren und wird durch TWIST_MATRIX
             * entsprechend der MT19937-Formel berücksichtigt.
             */
            if ((combined & 1) == 1) {
                twistedValue ^= TWIST_MATRIX;
            }

            state[i] = twistedValue;
        }

        // Der nächste Aufruf liest wieder beim ersten Wert des neuen Zustands.
        index = 0;
    }

    /**
     * Schritt 5: Verteilt die Bits eines Zustandswerts besser (Tempering).
     */
    private int temper(int value) {
        /*
         * >>> schiebt Nullen von links hinein. Bei den beiden Linksshifts
         * begrenzen die Masken, welche verschobenen Bits übernommen werden.
         */
        value ^= value >>> TEMPER_SHIFT_U;
        value ^= (value << TEMPER_SHIFT_S) & TEMPER_MASK_B;
        value ^= (value << TEMPER_SHIFT_T) & TEMPER_MASK_C;
        value ^= value >>> TEMPER_SHIFT_L;
        return value;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public long getState() {
        return generatedValues;
    }
}
