package ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ControlPanel extends JPanel {

    private static final String[] GENERATORS = {
            "LCG",
            "Middle-Square",
            "XOR Shift",
            "Mersenne Twister"
    };

    private final JComboBox<String> firstGeneratorDropdown;
    private final JComboBox<String> secondGeneratorDropdown;

    private final JTextField amountField;
    private final JTextField seedField;
    private final JTextField binField;

    private final JComboBox<String> numberModeDropdown;

    private final JLabel minLabel;
    private final JLabel maxLabel;

    private final JTextField minField;
    private final JTextField maxField;
    
    private final JButton startButton;
    private final JButton exportButton;

    // Steuerungsbereich der GUI
    public ControlPanel() {
        setPreferredSize(new Dimension(250, 700));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setLayout(new GridLayout(0, 1, 10, 10));

        JLabel titleLabel = new JLabel("Steuerung / Eingaben");

        JLabel firstGeneratorLabel = new JLabel("Generator 1 (Blau):");
        firstGeneratorDropdown = new JComboBox<>(GENERATORS);

        JLabel secondGeneratorLabel = new JLabel("Generator 2 (Rot):");
        secondGeneratorDropdown = new JComboBox<>();

        /*
         * Im zweiten Menü werden nur die drei Generatoren angeboten, die nicht
         * bereits im ersten Menü ausgewählt sind.
         */
        updateSecondGeneratorOptions();
        firstGeneratorDropdown.addActionListener(e -> updateSecondGeneratorOptions());


        JLabel amountLabel = new JLabel("Anzahl Werte:");
        amountField = new JTextField("1000");

        JLabel seedLabel = new JLabel("Seed:");
        seedField = new JTextField("12345");

        JLabel binLabel = new JLabel("Histogramm-Balken:");
        binField = new JTextField("10");

        JLabel modeLabel = new JLabel("Ausgabe:");
        numberModeDropdown = new JComboBox<>(new String[]{"Kommazahlen", "Ganzzahlen"});


        minLabel = new JLabel("Minimum:");
        minField = new JTextField("0");

        maxLabel = new JLabel("Maximum:");
        maxField = new JTextField("100");

        minLabel.setVisible(false);
        minField.setVisible(false);

        maxLabel.setVisible(false);
        maxField.setVisible(false);


        numberModeDropdown.addActionListener(e -> updateNumberMode());

        startButton = new JButton("Generatoren vergleichen");

        exportButton = new JButton("CSV exportieren");

        add(titleLabel);
        add(firstGeneratorLabel);
        add(firstGeneratorDropdown);
        add(secondGeneratorLabel);
        add(secondGeneratorDropdown);
        add(amountLabel);
        add(amountField);
        add(seedLabel);
        add(seedField);
        add(binLabel);
        add(binField);
        add(modeLabel);
        add(numberModeDropdown);
        add(minLabel);
        add(minField);
        add(maxLabel);
        add(maxField);
        add(startButton);
        add(exportButton);
    }

    private void updateSecondGeneratorOptions() {
        String firstGenerator = (String) firstGeneratorDropdown.getSelectedItem();
        String previousSecondGenerator = (String) secondGeneratorDropdown.getSelectedItem();

        secondGeneratorDropdown.removeAllItems();

        for (String generator : GENERATORS) {
            if (!generator.equals(firstGenerator)) {
                secondGeneratorDropdown.addItem(generator);
            }
        }

        /*
         * Die bisherige zweite Auswahl bleibt erhalten, sofern sie nicht gerade
         * als erster Generator gewählt wurde.
         */
        if (previousSecondGenerator != null
                && !previousSecondGenerator.equals(firstGenerator)) {
            secondGeneratorDropdown.setSelectedItem(previousSecondGenerator);
        }
    }

    // zeigt nur bei Auswahl von Ganzzahl die Felder max und min an
    private void updateNumberMode() {

        boolean integerMode =
                numberModeDropdown.getSelectedItem().equals("Ganzzahlen");

        minLabel.setVisible(integerMode);
        minField.setVisible(integerMode);

        maxLabel.setVisible(integerMode);
        maxField.setVisible(integerMode);

        revalidate();
        repaint();
    }

    public String getFirstSelectedGenerator() {
        return (String) firstGeneratorDropdown.getSelectedItem();
    }

    public String getSecondSelectedGenerator() {
        return (String) secondGeneratorDropdown.getSelectedItem();
    }

    public boolean isIntegerMode() {
    return numberModeDropdown.getSelectedItem().equals("Ganzzahlen");
    }

    public String getMinimumText() {
        return minField.getText();
    }

    public String getMaximumText() {
        return maxField.getText();
    }

    public String getAmountText() {
        return amountField.getText();
    }

    public String getSeedText() {
        return seedField.getText();
    }

    public String getBinText() {
        return binField.getText();
    }

    public void addStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    public void addExportButtonListener(ActionListener listener) {
        exportButton.addActionListener(listener);
    }
}
