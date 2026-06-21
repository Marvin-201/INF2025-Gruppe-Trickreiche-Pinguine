package rng;

public class RandomSequenceGenerator            //Diese Klasse ist kein Generator, sie benutzt nur einen
{                                               // Deshalb kein Implements Jungs!
    //Attributes
    private static final String NAME = "RandomSequenceGenerator";

    //Getter
    @Override
    Public String getName(){return NAME;}

    public double[] generate (RNG rng, int count)
    {
        double[] result = new double[count];        //erstellen eines Arrays

        for(int i = 0; i < count; i++){
            result[i]= rng.nextDouble();
        }
        return result;                              //return von Array der voll ist mit Zufallszahlen
    }
}
