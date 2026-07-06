package ui;

import analysis.ScatterPoint;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class ScatterPlotPanel extends JPanel {

    // Array mit allen Punkten, die später im Streudiagramm gezeichnet werden
    private ScatterPoint[] scatterData;

    // Eigenes Panel, auf dem das Diagramm gezeichnet wird
    private final JPanel chartPanel;

    // Konstruktor: baut das Streudiagramm-Panel auf
    public ScatterPlotPanel() {
        // Legt die bevorzugte Größe des Panels fest
        setPreferredSize(new Dimension(1000, 200));

        // BorderLayout: Titel oben, Diagramm in der Mitte
        setLayout(new BorderLayout());

        // Überschrift für das Streudiagramm
        JLabel titleLabel = new JLabel("Streudiagramm", SwingConstants.CENTER);

        // Hier wird ein eigenes JPanel erstellt, dessen Zeichenmethode überschrieben wird
        chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Zeichnet das Panel zuerst normal neu und löscht alte Zeichnungen
                super.paintComponent(g);

                // Danach wird unser Streudiagramm gezeichnet
                drawScatterPlot(g);
            }
        };

        // Titel oben hinzufügen
        add(titleLabel, BorderLayout.NORTH);

        // Zeichenfläche in die Mitte hinzufügen
        add(chartPanel, BorderLayout.CENTER);
    }

    // Methode bekommt neue Streudiagramm-Daten von außen
    public void setScatterData(ScatterPoint[] scatterData) {
        // Daten speichern
        this.scatterData = scatterData;

        // Panel neu zeichnen, damit die neuen Punkte sichtbar werden
        chartPanel.repaint();
    }

    // Zeichnet das Streudiagramm
    private void drawScatterPlot(Graphics g) {
        // Falls noch keine Daten vorhanden sind, wird nur ein Hinweistext angezeigt
        if (scatterData == null || scatterData.length == 0) {
            g.drawString("Hier wird später das 2D-Streudiagramm angezeigt.", 30, 30);
            return;
        }

        // Aktuelle Größe der Zeichenfläche holen
        int panelWidth = chartPanel.getWidth();
        int panelHeight = chartPanel.getHeight();

        // Abstände zum Rand, damit Platz für Achsen und Beschriftungen bleibt
        int leftPadding = 60;
        int rightPadding = 30;
        int topPadding = 30;
        int bottomPadding = 40;

        // Tatsächliche Breite und Höhe des Zeichenbereichs ohne Randabstände
        int chartWidth = panelWidth - leftPadding - rightPadding;
        int chartHeight = panelHeight - topPadding - bottomPadding;

        // Position der X-Achse und Y-Achse
        int xAxisY = panelHeight - bottomPadding;
        int yAxisX = leftPadding;

        // Achsen schwarz zeichnen
        g.setColor(Color.BLACK);

        // Y-Achse zeichnen
        g.drawLine(yAxisX, topPadding, yAxisX, xAxisY);

        // X-Achse zeichnen
        g.drawLine(yAxisX, xAxisY, panelWidth - rightPadding, xAxisY);

        // Achsen beschriften
        // x(n) ist der aktuelle Zufallswert
        g.drawString("x(n)", panelWidth - rightPadding - 30, xAxisY + 30);

        // x(n+1) ist der darauffolgende Zufallswert
        g.drawString("x(n+1)", 10, topPadding - 10);

        // Alle Punkte des Streudiagramms zeichnen
        for (ScatterPoint point : scatterData) {

            // Zufallswerte liegen normalerweise zwischen 0 und 1.
            // Deshalb kann man sie direkt auf die Diagrammbreite und -höhe skalieren.
            int x = yAxisX + (int) (point.getX() * chartWidth);

            // Bei Swing ist y = 0 oben.
            // Deshalb wird von der X-Achse nach oben gerechnet.
            int y = xAxisY - (int) (point.getY() * chartHeight);

            // Punktfarbe setzen
            g.setColor(Color.BLUE);

            // Kleinen Punkt zeichnen
            // x - 2 und y - 2 sorgen dafür, dass der Punkt um die Koordinate zentriert ist
            g.fillOval(x - 2, y - 2, 4, 4);
        }
    }
}