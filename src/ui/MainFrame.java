package ui;

import controller.SimulationController;
import controller.SimulationResult;
import export.CSVExporter;
import java.awt.BorderLayout;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import util.NumberConverter;

public class MainFrame extends JFrame {

    private final ControlPanel controlPanel;
    private final HistogramPanel histogramPanel;
    private final ScatterPlotPanel scatterPlotPanel;
    private SimulationResult firstResult;
    private SimulationResult secondResult;

    // Hauptfenster der Anwendung.
    public MainFrame() {
        setTitle("Zufallszahlengeneratoren und Verteilungsanalyse");
        setSize(1200, 800);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // BorderLayout teilt das Fenster in Bereiche auf.
        setLayout(new BorderLayout());

        // Panels werden als Attribute gespeichert,
        // damit MainFrame später auf sie zugreifen kann.
        controlPanel = new ControlPanel();
        histogramPanel = new HistogramPanel();
        scatterPlotPanel = new ScatterPlotPanel();

        add(controlPanel, BorderLayout.WEST);
        add(histogramPanel, BorderLayout.CENTER);
        add(scatterPlotPanel, BorderLayout.SOUTH);

        // Wenn der Startbutton geklickt wird, wird die Simulation gestartet.
        controlPanel.addStartButtonListener(e -> startSimulation());

        // Wenn der Exportbutton geklickt wird, wird die Methode aufgerufen
        controlPanel.addExportButtonListener(e -> exportCSV());
    }

    private void startSimulation() {
        try {
            String firstGenerator = controlPanel.getFirstSelectedGenerator();
            String secondGenerator = controlPanel.getSecondSelectedGenerator();

            int amount = Integer.parseInt(controlPanel.getAmountText());
            int seed = Integer.parseInt(controlPanel.getSeedText());
            int bins = Integer.parseInt(controlPanel.getBinText());
            int min = Integer.parseInt(controlPanel.getMinimumText());
            int max = Integer.parseInt(controlPanel.getMaximumText());

            // Validierung der Eingaben
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Die Anzahl der Werte muss größer als 0 sein.");
                return;
            }

            if (seed <= 0) {
                JOptionPane.showMessageDialog(this, "Der Seed muss größer als 0 sein.");
                return;
            }

            if (bins <= 0) {
                JOptionPane.showMessageDialog(this, "Die Anzahl der Histogramm-Balken muss größer als 0 sein.");
                return;
            }

            if (min < 0) {
                JOptionPane.showMessageDialog(this, "Der minimale Wert der ausgegeben werden soll muss größer als 0 sein.");
                return;
            }

            if (min >= max) {
                JOptionPane.showMessageDialog(this, "Der Minimalwert muss kleiner als der Maximalwert sein.");
                return;
            }

            if (max - min < 2) {
                JOptionPane.showMessageDialog(this, "Minimal- und Maximalwert müssen mindestens um 2 auseinander liegen.");
                return;
            }

            firstResult = SimulationController.runSimulation(
                    getControllerGeneratorName(firstGenerator),
                    seed,
                    amount,
                    bins
            );

            secondResult = SimulationController.runSimulation(
                    getControllerGeneratorName(secondGenerator),
                    seed,
                    amount,
                    bins
            );

            histogramPanel.setComparisonData(
                    firstGenerator,
                    firstResult,
                    secondGenerator,
                    secondResult
            );
            scatterPlotPanel.setComparisonData(
                    firstGenerator,
                    firstResult.getCorrelation(),
                    secondGenerator,
                    secondResult.getCorrelation()
            );

        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Bitte gib gültige Zahlen für Seed, Anzahl Werte und Histogramm-Balken ein."
            );
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage());
        }
    }

    /**
     * Übersetzt die Namen aus der GUI in die Namen des Controllers.
     */
    private String getControllerGeneratorName(String displayName) {
        switch (displayName) {
            case "Middle-Square":
                return "MiddleSquareGenerator";
            case "XOR Shift":
                return "XORShiftGenerator";
            case "Mersenne Twister":
                return "MersenneTwister";
            default:
                return displayName;
        }
    }

    private void exportCSV() {

        if (firstResult == null || secondResult == null) {
            JOptionPane.showMessageDialog(this, "Bitte zuerst eine Simulation starten.");
            return;
        }

        JFileChooser chooser = new JFileChooser();

        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }


        File file = chooser.getSelectedFile();


        try {

            if (controlPanel.isIntegerMode()) {

                int min = Integer.parseInt(controlPanel.getMinimumText());
                int max = Integer.parseInt(controlPanel.getMaximumText());


                int[] firstValues =
                        NumberConverter.convertToIntegers(
                                firstResult.getValues(),
                                min,
                                max
                        );


                int[] secondValues =
                        NumberConverter.convertToIntegers(
                                secondResult.getValues(),
                                min,
                                max
                        );


                CSVExporter.exportIntegerComparison(
                        firstValues,
                        secondValues,
                        file
                );


            } else {

                CSVExporter.exportDoubleComparison(
                        firstResult.getValues(),
                        secondResult.getValues(),
                        file
                );
            }


            JOptionPane.showMessageDialog(this, "CSV-Datei erfolgreich exportiert.");


        } catch (Exception exception) {

            JOptionPane.showMessageDialog(this, "Fehler beim Export: " + exception.getMessage());
        }
    }
    
}
