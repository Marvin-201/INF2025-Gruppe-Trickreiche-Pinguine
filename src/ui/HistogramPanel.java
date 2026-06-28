package ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

public class HistogramPanel extends JPanel {

    private int[] histogramData;
    private JPanel chartPanel;

    // Bereich für das Histogramm
    public HistogramPanel() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Histogramm-Bereich", SwingConstants.CENTER);

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

    public void setHistogramData(int[] histogramData) {
        this.histogramData = histogramData;
        chartPanel.repaint();
    }

    private void drawHistogram(Graphics g) {
        if (histogramData == null || histogramData.length == 0) {
            g.drawString("Hier wird später das Histogramm angezeigt.", 30, 30);
            return;
        }

        int panelWidth = chartPanel.getWidth();
        int panelHeight = chartPanel.getHeight();

        int leftPadding = 70;
        int rightPadding = 30;
        int topPadding = 50;
        int bottomPadding = 50;

        int chartWidth = panelWidth - leftPadding - rightPadding;
        int chartHeight = panelHeight - topPadding - bottomPadding;

        int barCount = histogramData.length;
        int barWidth = chartWidth / barCount;

        int maxValue = 0;
        for (int value : histogramData) {
            if (value > maxValue) {
                maxValue = value;
            }
        }

        if (maxValue == 0) {
            return;
        }

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

            // X-Beschriftung
            g.drawString(String.valueOf(i + 1), x + barWidth / 2 - 4, xAxisY + 18);
        }
    }
}