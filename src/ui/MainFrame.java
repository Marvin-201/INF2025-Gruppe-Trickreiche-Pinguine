package ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    //Hauptfenster der Anwendung.
    public MainFrame() {
        setTitle("Zufallszahlengeneratoren und Verteilungsanalyse");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //BorderLayout teilt das Fenster in Bereiche auf. 
        setLayout(new BorderLayout());

        add(createControlArea(), BorderLayout.WEST);
        add(createHistogramArea(), BorderLayout.CENTER);
        add(createScatterPlotArea(), BorderLayout.SOUTH);
    }

    // Steuerungsbereich der GUI
    private JPanel createControlArea() {
        JPanel controlArea = new JPanel();

        // Feste Breite für den linken Steuerungsbereich
        controlArea.setPreferredSize(new Dimension(250, 700));

        // Innenabstand, damit die Elemente nicht direkt am Rand kleben
        controlArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //Gridlayout ordnet die Elemente mit abstand 10 untereinander an
        controlArea.setLayout(new GridLayout(0, 1, 10, 10));

        JLabel titleLabel = new JLabel("Steuerung / Eingaben");

        JLabel generatorLabel = new JLabel("Generator auswählen:");

        //Dropdown für RNG
        JComboBox<String> generatorDropdown = new JComboBox<>(new String[]{
                "LCG",
                "Middle-Square"
        });

        // Eingabe für die Anzahl der zu erzeugenden Zufallszahlen
        JLabel amountLabel = new JLabel("Anzahl Werte:");
        JTextField amountField = new JTextField("1000");

        // Eingabe für den Startwert des Generators
        JLabel seedLabel = new JLabel("Seed:");
        JTextField seedField = new JTextField("12345");

        // Startbutton, aktuell noch ohne Funktion
        JButton startButton = new JButton("Simulation starten");

        controlArea.add(titleLabel);
        controlArea.add(generatorLabel);
        controlArea.add(generatorDropdown);
        controlArea.add(amountLabel);
        controlArea.add(amountField);
        controlArea.add(seedLabel);
        controlArea.add(seedField);
        controlArea.add(startButton);

        return controlArea;
    }

    //Bereich für das Histogramm (noch platzhalter)
    private JPanel createHistogramArea() {
        JPanel histogramArea = new JPanel();
        histogramArea.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Histogramm-Bereich", SwingConstants.CENTER);
        JLabel placeholderLabel = new JLabel("Hier wird später das Histogramm angezeigt.", SwingConstants.CENTER);

        histogramArea.add(titleLabel, BorderLayout.NORTH);
        histogramArea.add(placeholderLabel, BorderLayout.CENTER);

        return histogramArea;
    }

    //Streudiagramm 
    private JPanel createScatterPlotArea() {
        JPanel scatterPlotArea = new JPanel();

        // Feste Höhe für den unteren Diagrammbereich
        scatterPlotArea.setPreferredSize(new Dimension(1000, 200));
        scatterPlotArea.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Streudiagramm-Bereich", SwingConstants.CENTER);
        JLabel placeholderLabel = new JLabel("Hier wird später das 2D-Streudiagramm angezeigt.", SwingConstants.CENTER);

        scatterPlotArea.add(titleLabel, BorderLayout.NORTH);
        scatterPlotArea.add(placeholderLabel, BorderLayout.CENTER);

        return scatterPlotArea;
    }
}