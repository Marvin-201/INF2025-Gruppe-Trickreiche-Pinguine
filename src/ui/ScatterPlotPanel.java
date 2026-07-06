package ui;

import analysis.ScatterPoint;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class ScatterPlotPanel extends JPanel {

    private static final Color FIRST_COLOR = new Color(45, 95, 210);
    private static final Color SECOND_COLOR = new Color(215, 55, 55);

    private ScatterPoint[] firstScatterData;
    private ScatterPoint[] secondScatterData;

    private final JLabel titleLabel;
    private final JCheckBox firstGeneratorCheckBox;
    private final JCheckBox secondGeneratorCheckBox;
    private final JPanel chartPanel;

    public ScatterPlotPanel() {
        setPreferredSize(new Dimension(1200, 240));
        setLayout(new BorderLayout());

        titleLabel = new JLabel(
                "Gemeinsames Streudiagramm",
                SwingConstants.CENTER
        );

        firstGeneratorCheckBox = new JCheckBox("Generator 1 anzeigen", true);
        firstGeneratorCheckBox.setForeground(FIRST_COLOR);

        secondGeneratorCheckBox = new JCheckBox("Generator 2 anzeigen", true);
        secondGeneratorCheckBox.setForeground(SECOND_COLOR);

        JPanel headerPanel = new JPanel(new BorderLayout());
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.add(firstGeneratorCheckBox);
        checkBoxPanel.add(secondGeneratorCheckBox);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(checkBoxPanel, BorderLayout.CENTER);

        chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawScatterPlot(g);
            }
        };

        // Bei jedem Klick wird das Diagramm mit der neuen Auswahl gezeichnet.
        firstGeneratorCheckBox.addActionListener(e -> chartPanel.repaint());
        secondGeneratorCheckBox.addActionListener(e -> chartPanel.repaint());

        add(headerPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);
    }

    public void setComparisonData(
            String firstName,
            ScatterPoint[] firstData,
            String secondName,
            ScatterPoint[] secondData
    ) {
        firstScatterData = firstData;
        secondScatterData = secondData;

        titleLabel.setText("Streudiagramm");
        firstGeneratorCheckBox.setText(firstName + " anzeigen");
        secondGeneratorCheckBox.setText(secondName + " anzeigen");

        chartPanel.repaint();
    }

    private void drawScatterPlot(Graphics g) {
        if (firstScatterData == null || secondScatterData == null) {
            g.setColor(Color.BLACK);
            g.drawString("Hier wird der gemeinsame Streudiagrammvergleich angezeigt.", 30, 30);
            return;
        }

        int panelWidth = chartPanel.getWidth();
        int panelHeight = chartPanel.getHeight();

        int leftPadding = 60;
        int rightPadding = 30;
        int topPadding = 30;
        int bottomPadding = 40;

        int chartWidth = panelWidth - leftPadding - rightPadding;
        int chartHeight = panelHeight - topPadding - bottomPadding;

        if (chartWidth <= 0 || chartHeight <= 0) {
            return;
        }

        int xAxisY = panelHeight - bottomPadding;
        int yAxisX = leftPadding;

        g.setColor(Color.BLACK);
        g.drawLine(yAxisX, topPadding, yAxisX, xAxisY);
        g.drawLine(yAxisX, xAxisY, panelWidth - rightPadding, xAxisY);
        g.drawString("x(n)", panelWidth - rightPadding - 30, xAxisY + 30);
        g.drawString("x(n+1)", 10, topPadding - 10);

        if (firstGeneratorCheckBox.isSelected()) {
            // Generator 1 wird als blauer Punkt dargestellt.
            drawDataSeries(
                    g,
                    firstScatterData,
                    chartWidth,
                    chartHeight,
                    xAxisY,
                    yAxisX,
                    FIRST_COLOR,
                    true
            );
        }

        if (secondGeneratorCheckBox.isSelected()) {
            // Generator 2 wird als rotes Kreuz dargestellt.
            drawDataSeries(
                    g,
                    secondScatterData,
                    chartWidth,
                    chartHeight,
                    xAxisY,
                    yAxisX,
                    SECOND_COLOR,
                    false
            );
        }
    }

    private void drawDataSeries(
            Graphics g,
            ScatterPoint[] data,
            int chartWidth,
            int chartHeight,
            int xAxisY,
            int yAxisX,
            Color color,
            boolean drawCircle
    ) {
        g.setColor(color);

        for (ScatterPoint point : data) {
            int x = yAxisX + (int) (point.getX() * chartWidth);
            int y = xAxisY - (int) (point.getY() * chartHeight);

            if (drawCircle) {
                g.fillOval(x - 2, y - 2, 4, 4);
            } else {
                g.drawLine(x - 2, y - 2, x + 2, y + 2);
                g.drawLine(x - 2, y + 2, x + 2, y - 2);
            }
        }
    }
}
