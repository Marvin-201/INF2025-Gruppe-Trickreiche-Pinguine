package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HistogramPanel extends JPanel {

    //Wie hoch die Balken sind, wird in diesem Array gespeichert.
    private int[] histogramData;
    private final JPanel chartPanel;

    // Bereich für das Histogramm
    public HistogramPanel() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Histogramm-Bereich", SwingConstants.CENTER);
        // Panel, in dem das Histogramm gezeichnet wird
        chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawHistogram(g);
            }
        };

        add(titleLabel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);
    }

    // Methode zum Neuzeichnen des Histogramms 
    public void setHistogramData(int[] histogramData) {
        this.histogramData = histogramData;
        chartPanel.repaint();
    }

    // Methode zum Zeichnen des Histogramms
    private void drawHistogram(Graphics g) {
        //Prüfen, ob daten vorhanden sind
        if (histogramData == null || histogramData.length == 0) {
            g.drawString("Hier wird später das Histogramm angezeigt.", 30, 30);
            return;
        }

        // Berechnung der darstellung der Histogramms
        int panelWidth = chartPanel.getWidth();
        int panelHeight = chartPanel.getHeight();

        int leftPadding = 70;
        int rightPadding = 30;
        int topPadding = 50;
        int bottomPadding = 50;

        int chartWidth = panelWidth - leftPadding - rightPadding;
        int chartHeight = panelHeight - topPadding - bottomPadding - 20;

        int barCount = histogramData.length;
        int barWidth = chartWidth / barCount;

        int maxValue = 0;
        // Maximalwert im Histogramm
        for (int value : histogramData) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        
        if (maxValue == 0) {
            return;
        }
        // Koordinaten für die Achsen
        int xAxisY = panelHeight - bottomPadding;
        int yAxisX = leftPadding;

        // Achsen zeichnen
        g.setColor(Color.BLACK);
        g.drawLine(yAxisX, topPadding, yAxisX, xAxisY);
        g.drawLine(yAxisX, xAxisY, panelWidth - rightPadding, xAxisY);

        // Y-Achse beschriften
        g.drawString("Häufigkeit", 10, topPadding - 15);
        
        int ySteps = 5;
        
        for (int i = 0; i <= ySteps; i++) {
            int value = (maxValue * i) / ySteps;
            int y = xAxisY - (int) ((value / (double) maxValue) * chartHeight);

            // kleine Markierung an der Y-Achse
            g.drawLine(yAxisX - 5, y, yAxisX, y);

            // Zahl links von der Y-Achse
            g.drawString(String.valueOf(value), yAxisX - 35, y + 5);

            // leichte Hilfslinie
            g.setColor(Color.LIGHT_GRAY);
            g.drawLine(yAxisX + 1, y, panelWidth - rightPadding, y);
            g.setColor(Color.BLACK);
        }

        // X-Achse beschriften
        g.drawString("Klassen", panelWidth - rightPadding - 50, xAxisY + 35);

        // Balken zeichnen
        for (int i = 0; i < barCount; i++) {
            int barHeight = (int) ((histogramData[i] / (double) maxValue) * chartHeight);

            int x = yAxisX + i * barWidth;
            int y = xAxisY - barHeight;

            g.setColor(Color.GRAY);
            g.fillRect(x + 2, y, barWidth - 4, barHeight);

            g.setColor(Color.BLACK);
            g.drawRect(x + 2, y, barWidth - 4, barHeight);

            // X-Beschriftung nur bei wenigen Balken vollständig anzeigen.
            // Bei vielen Balken wird nur jede 5., 10. oder 20. Zahl angezeigt,
            // damit sich die Zahlen nicht überschneiden.
            int labelStep;

            if (barCount <= 20) {
                labelStep = 1;
            } else if (barCount <= 50) {
                labelStep = 5;
            } else if (barCount <= 100) {
                labelStep = 10;
            } else {
                labelStep = 20;
            }

            if ((i + 1) % labelStep == 0 || i == 0 || i == barCount - 1) {
                g.drawString(String.valueOf(i + 1), x + barWidth / 2 - 4, xAxisY + 18);
            }
        }
    }
}