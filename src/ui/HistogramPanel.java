package ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;


public class HistogramPanel extends JPanel {
    //Bereich für das Histogramm (noch platzhalter)
    public HistogramPanel() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Histogramm-Bereich", SwingConstants.CENTER);
        JLabel placeholderLabel = new JLabel("Hier wird später das Histogramm angezeigt.", SwingConstants.CENTER);

        add(titleLabel, BorderLayout.NORTH);
        add(placeholderLabel, BorderLayout.CENTER);

    }
}
