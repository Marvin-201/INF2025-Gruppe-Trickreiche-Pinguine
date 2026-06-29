package ui;

import controller.SimulationController;
import controller.SimulationResult;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;

public class MainFrame extends JFrame {

    private ControlPanel controlPanel;
    private HistogramPanel histogramPanel;
    private ScatterPlotPanel scatterPlotPanel;

    // Hauptfenster der Anwendung.
    public MainFrame() {
        setTitle("Zufallszahlengeneratoren und Verteilungsanalyse");
        setSize(1000, 700);
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
            String selectedGenerator = controlPanel.getSelectedGenerator();

            int amount = Integer.parseInt(controlPanel.getAmountText());
            int seed = Integer.parseInt(controlPanel.getSeedText());
            int bins = Integer.parseInt(controlPanel.getBinText());

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

            /*
             * Im Dropdown steht "Middle-Square".
             * Der SimulationController erwartet aktuell aber "MiddleSquareGenerator".
             * Deshalb wird der Name hier angepasst.
             */
            String controllerGeneratorName = selectedGenerator;

            if ("Middle-Square".equals(selectedGenerator))
            {
                controllerGeneratorName = "MiddleSquareGenerator";
            }


            if("XOR Shift".equals(selectedGenerator))
            {
                controllerGeneratorName = "XORShiftGenerator";
            }

            if ("Mersenne Twister".equals(selectedGenerator))
            {
                controllerGeneratorName = "MersenneTwister";
            }

            SimulationResult result = SimulationController.runSimulation(
                    controllerGeneratorName,
                    seed,
                    amount,
                    bins
            );


            histogramPanel.setHistogramData(result.getHistogramData());

        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Bitte gib gültige Zahlen für Seed, Anzahl Werte und Histogramm-Balken ein."
            );
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage());
        }
    }
}
