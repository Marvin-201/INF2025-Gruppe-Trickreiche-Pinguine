package export;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class CSVExporter {


    public static void exportDoubleComparison(double[] first, double[] second, File file) throws IOException {
        try(PrintWriter writer = new PrintWriter(file)) {

            writer.println("Index;Generator1;Generator2");

            for(int i = 0; i < first.length; i++) {

                writer.println(i + ";" + first[i] + ";" + second[i]
                );
            }
        }
    }


    public static void exportIntegerComparison(int[] first, int[] second, File file) throws IOException {

        try(PrintWriter writer = new PrintWriter(file)) {

            writer.println("Index;Generator1;Generator2");

            for(int i = 0; i < first.length; i++) {

                writer.println(i + ";" + first[i] + ";" + second[i]);
            }
        }
    }
}