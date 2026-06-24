package ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class ScatterPlotPanel extends JPanel {
        //Streudiagramm 
    public ScatterPlotPanel() {

        // Feste Höhe für den unteren Diagrammbereich
        setPreferredSize(new Dimension(1000, 200));
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Streudiagramm-Bereich", SwingConstants.CENTER);
        JLabel placeholderLabel = new JLabel("Hier wird später das 2D-Streudiagramm angezeigt.", SwingConstants.CENTER);

        add(titleLabel, BorderLayout.NORTH);
        add(placeholderLabel, BorderLayout.CENTER);

    }
}
