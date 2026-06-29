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

    private final JComboBox<String> generatorDropdown;
    private final JTextField amountField;
    private final JTextField seedField;
    private final JTextField binField;
    private final JButton startButton;

    // Steuerungsbereich der GUI
    public ControlPanel() {
        setPreferredSize(new Dimension(250, 700));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setLayout(new GridLayout(0, 1, 10, 10));

        JLabel titleLabel = new JLabel("Steuerung / Eingaben");

        JLabel generatorLabel = new JLabel("Generator auswählen:");
        generatorDropdown = new JComboBox<>(new String[]{
                "LCG",
                "Middle-Square",
                "XOR Shift"
        });

        JLabel amountLabel = new JLabel("Anzahl Werte:");
        amountField = new JTextField("1000");

        JLabel seedLabel = new JLabel("Seed:");
        seedField = new JTextField("12345");

        JLabel binLabel = new JLabel("Histogramm-Balken:");
        binField = new JTextField("10");

        startButton = new JButton("Simulation starten");

        add(titleLabel);
        add(generatorLabel);
        add(generatorDropdown);
        add(amountLabel);
        add(amountField);
        add(seedLabel);
        add(seedField);
        add(binLabel);
        add(binField);
        add(startButton);
    }

    public String getSelectedGenerator() {
        return (String) generatorDropdown.getSelectedItem();
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
}