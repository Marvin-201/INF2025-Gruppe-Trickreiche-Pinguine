package ui;

import controller.SimulationResult;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HistogramPanel extends JPanel {

    private static final Color FIRST_COLOR = new Color(45, 95, 210);
    private static final Color SECOND_COLOR = new Color(215, 55, 55);

    private int[] firstHistogramData;
    private int[] secondHistogramData;

    private final StatisticsPanel firstStatisticsPanel;
    private final StatisticsPanel secondStatisticsPanel;
    private final JLabel differenceLabel;
    private final JPanel chartPanel;

    public HistogramPanel() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel(
                "Vergleich der Generatoren",
                SwingConstants.CENTER
        );

        firstStatisticsPanel = new StatisticsPanel(FIRST_COLOR);
        secondStatisticsPanel = new StatisticsPanel(SECOND_COLOR);
        differenceLabel = new JLabel(
                "Nach dem Start werden hier die Unterschiede angezeigt.",
                SwingConstants.CENTER
        );

        JPanel statisticsColumns = new JPanel(new GridLayout(1, 2, 10, 0));
        statisticsColumns.add(firstStatisticsPanel);
        statisticsColumns.add(secondStatisticsPanel);

        JPanel comparisonHeader = new JPanel(new BorderLayout(0, 5));
        comparisonHeader.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        comparisonHeader.add(titleLabel, BorderLayout.NORTH);
        comparisonHeader.add(statisticsColumns, BorderLayout.CENTER);
        comparisonHeader.add(differenceLabel, BorderLayout.SOUTH);

        chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawHistogram(g);
            }
        };

        add(comparisonHeader, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);
    }

    /**
     * Übernimmt beide Ergebnisse gleichzeitig, damit immer derselbe Lauf
     * miteinander verglichen und gemeinsam gezeichnet wird.
     */
    public void setComparisonData(
            String firstName,
            SimulationResult firstResult,
            String secondName,
            SimulationResult secondResult
    ) {
        firstHistogramData = firstResult.getHistogramData();
        secondHistogramData = secondResult.getHistogramData();

        firstStatisticsPanel.setStatistics(firstName, firstResult);
        secondStatisticsPanel.setStatistics(secondName, secondResult);

        double meanDifference = Math.abs(firstResult.getMean() - secondResult.getMean());
        double varianceDifference =
                Math.abs(firstResult.getVariance() - secondResult.getVariance());

        differenceLabel.setText(String.format(
                "Absolute Unterschiede: Mittelwert %.6f  |  Varianz %.6f",
                meanDifference,
                varianceDifference
        ));

        chartPanel.repaint();
    }

    private void drawHistogram(Graphics g) {
        if (firstHistogramData == null || secondHistogramData == null
                || firstHistogramData.length == 0
                || firstHistogramData.length != secondHistogramData.length) {
            g.setColor(Color.BLACK);
            g.drawString("Hier wird der gemeinsame Histogrammvergleich angezeigt.", 30, 30);
            return;
        }

        int panelWidth = chartPanel.getWidth();
        int panelHeight = chartPanel.getHeight();

        int leftPadding = 70;
        int rightPadding = 30;
        int topPadding = 35;
        int bottomPadding = 50;

        int chartWidth = panelWidth - leftPadding - rightPadding;
        int chartHeight = panelHeight - topPadding - bottomPadding;

        if (chartWidth <= 0 || chartHeight <= 0) {
            return;
        }

        int maxValue = getMaximumValue();
        if (maxValue == 0) {
            return;
        }

        int xAxisY = panelHeight - bottomPadding;
        int yAxisX = leftPadding;

        drawAxesAndGrid(g, panelWidth, topPadding, rightPadding,
                chartHeight, xAxisY, yAxisX, maxValue);
        drawBars(g, chartWidth, chartHeight, xAxisY, yAxisX, maxValue);
    }

    private int getMaximumValue() {
        int maximum = 0;

        for (int i = 0; i < firstHistogramData.length; i++) {
            maximum = Math.max(maximum, firstHistogramData[i]);
            maximum = Math.max(maximum, secondHistogramData[i]);
        }

        return maximum;
    }

    private void drawAxesAndGrid(
            Graphics g,
            int panelWidth,
            int topPadding,
            int rightPadding,
            int chartHeight,
            int xAxisY,
            int yAxisX,
            int maxValue
    ) {
        g.setColor(Color.BLACK);
        g.drawLine(yAxisX, topPadding, yAxisX, xAxisY);
        g.drawLine(yAxisX, xAxisY, panelWidth - rightPadding, xAxisY);
        g.drawString("Häufigkeit", 10, topPadding - 10);
        g.drawString("Klassen", panelWidth - rightPadding - 50, xAxisY + 35);

        int ySteps = 5;
        for (int i = 0; i <= ySteps; i++) {
            int value = (maxValue * i) / ySteps;
            int y = xAxisY - (int) ((value / (double) maxValue) * chartHeight);

            g.setColor(Color.BLACK);
            g.drawLine(yAxisX - 5, y, yAxisX, y);
            g.drawString(String.valueOf(value), yAxisX - 40, y + 5);

            g.setColor(Color.LIGHT_GRAY);
            g.drawLine(yAxisX + 1, y, panelWidth - rightPadding, y);
        }
    }

    private void drawBars(
            Graphics g,
            int chartWidth,
            int chartHeight,
            int xAxisY,
            int yAxisX,
            int maxValue
    ) {
        int barCount = firstHistogramData.length;
        int groupWidth = Math.max(1, chartWidth / barCount);
        int singleBarWidth = Math.max(1, (groupWidth - 6) / 2);
        int labelStep = getLabelStep(barCount);

        for (int i = 0; i < barCount; i++) {
            int groupX = yAxisX + i * groupWidth;

            drawSingleBar(
                    g,
                    groupX + 2,
                    singleBarWidth,
                    firstHistogramData[i],
                    maxValue,
                    chartHeight,
                    xAxisY,
                    FIRST_COLOR
            );
            drawSingleBar(
                    g,
                    groupX + 3 + singleBarWidth,
                    singleBarWidth,
                    secondHistogramData[i],
                    maxValue,
                    chartHeight,
                    xAxisY,
                    SECOND_COLOR
            );

            if ((i + 1) % labelStep == 0 || i == 0 || i == barCount - 1) {
                g.setColor(Color.BLACK);
                g.drawString(
                        String.valueOf(i + 1),
                        groupX + groupWidth / 2 - 4,
                        xAxisY + 18
                );
            }
        }
    }

    private void drawSingleBar(
            Graphics g,
            int x,
            int width,
            int value,
            int maxValue,
            int chartHeight,
            int xAxisY,
            Color color
    ) {
        int height = (int) ((value / (double) maxValue) * chartHeight);
        int y = xAxisY - height;

        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
    }

    private int getLabelStep(int barCount) {
        if (barCount <= 20) {
            return 1;
        }
        if (barCount <= 50) {
            return 5;
        }
        if (barCount <= 100) {
            return 10;
        }
        return 20;
    }


    private static class StatisticsPanel extends JPanel {

        private final JLabel nameLabel;
        private final JLabel meanLabel;
        private final JLabel varianceLabel;
        private final JLabel periodLabel;

        StatisticsPanel(Color color) {
            setLayout(new BorderLayout(0, 4));
            setBorder(BorderFactory.createLineBorder(color, 2));

            nameLabel = new JLabel("Noch keine Daten", SwingConstants.CENTER);
            nameLabel.setForeground(color);
            nameLabel.setFont(nameLabel.getFont().deriveFont(
                    nameLabel.getFont().getStyle() | java.awt.Font.BOLD
            ));

            meanLabel = new JLabel("Mittelwert: -", SwingConstants.CENTER);
            varianceLabel = new JLabel("Varianz: -", SwingConstants.CENTER);
            periodLabel = new JLabel("Periode: -", SwingConstants.CENTER);

            JPanel valuePanel = new JPanel(new GridLayout(1, 3));
            valuePanel.add(meanLabel);
            valuePanel.add(varianceLabel);
            valuePanel.add(periodLabel);

            add(nameLabel, BorderLayout.NORTH);
            add(valuePanel, BorderLayout.CENTER);
        }

        void setStatistics(String name, SimulationResult result) {
            String periodText = result.getPeriod() < 0
                    ? "nicht gefunden"
                    : String.format("%.0f", result.getPeriod());

            nameLabel.setText(name);
            meanLabel.setText(String.format("Mittelwert: %.6f", result.getMean()));
            varianceLabel.setText(String.format("Varianz: %.6f", result.getVariance()));
            periodLabel.setText("Periode: " + periodText);
        }
    }
}
