package ui;

import controller.SimulationController;
import controller.SimulationResult;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;

public class MainFrame extends JFrame {

    private final ControlPanel controlPanel;
    private final HistogramPanel histogramPanel;
    private final ScatterPlotPanel scatterPlotPanel;

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
    }

    private void startSimulation() {
        try {
            String firstGenerator = controlPanel.getFirstSelectedGenerator();
            String secondGenerator = controlPanel.getSecondSelectedGenerator();

            int amount = Integer.parseInt(controlPanel.getAmountText());
            int seed = Integer.parseInt(controlPanel.getSeedText());
            int bins = Integer.parseInt(controlPanel.getBinText());

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

            SimulationResult firstResult = SimulationController.runSimulation(
                    getControllerGeneratorName(firstGenerator),
                    seed,
                    amount,
                    bins
            );

            SimulationResult secondResult = SimulationController.runSimulation(
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
}
