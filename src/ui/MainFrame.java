package ui;

import javax.swing.JFrame;
import java.awt.BorderLayout;

public class MainFrame extends JFrame {

    // Hauptfenster der Anwendung.
    public MainFrame() {
        setTitle("Zufallszahlengeneratoren und Verteilungsanalyse");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // BorderLayout teilt das Fenster in Bereiche auf.
        setLayout(new BorderLayout());

        // Einzelne GUI-Bereiche werden als eigene Panel-Klassen eingebunden.
        add(new ControlPanel(), BorderLayout.WEST);
        add(new HistogramPanel(), BorderLayout.CENTER);
        add(new ScatterPlotPanel(), BorderLayout.SOUTH);
    }
}