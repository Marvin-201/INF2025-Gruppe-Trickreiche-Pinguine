# Zufallszahlengeneratoren und Verteilungsanalyse

## Projektbeschreibung

Dieses Projekt ist ein Java-Programmierprojekt im Rahmen von TINF2025.

Ziel des Projekts ist es, verschiedene Zufallszahlengeneratoren zu implementieren, die erzeugten Zufallszahlen zu analysieren und die Ergebnisse grafisch darzustellen.

Zufallszahlen werden in vielen Bereichen der Informatik verwendet, zum Beispiel in Simulationen, Statistik, Kryptographie und Algorithmen. In diesem Projekt soll untersucht werden, wie verschiedene Zufallszahlengeneratoren funktionieren und wie gut die erzeugten Zahlen verteilt sind.

## Projektziele

Das Projekt verfolgt folgende Ziele:

- Implementierung klassischer Zufallszahlengeneratoren
- Analyse der erzeugten Zufallszahlen
- Visualisierung der Verteilungen
- Vergleich verschiedener Generatoren
- Untersuchung von Mustern und Korrelationen
- Aufbau einer modularen Java-Anwendung

## Zu implementierende Zufallszahlengeneratoren

Folgende Generatoren sollen umgesetzt werden:

### Linear Congruential Generator, LCG

Der Linear Congruential Generator erzeugt Zufallszahlen mit einer rekursiven Formel.

Verwendete Parameter:

- `seed`
- `m`
- `a`
- `c`

### Middle-Square-Methode

Bei der Middle-Square-Methode wird eine Zahl quadriert. Aus der Mitte des Ergebnisses wird anschließend die nächste Zahl gebildet.

### XORShift-Generator

Der XORShift-Generator arbeitet mit Bitoperationen und Verschiebungen. Er ist einfach zu implementieren und kann schnell Zufallszahlen erzeugen.

## Analysefunktionen

Die erzeugten Zufallszahlen sollen untersucht werden.

Geplante Analysen:

- Erzeugung von Histogrammen
- Visuelle Analyse der Gleichverteilung
- Einfache statistische Auswertung
- Untersuchung der Korrelation aufeinanderfolgender Zufallszahlen
- Abschätzung der Periodenlänge

## Visualisierung

Die Ergebnisse sollen grafisch dargestellt werden.

Geplante Visualisierungen:

- 1D-Histogramm zur Verteilungsanalyse
- 2D-Streudiagramm zur Darstellung von Zahlenpaaren
- Vergleich verschiedener Generatoren

Im 2D-Streudiagramm sollen Punkte der Form `(xn, xn+1)` dargestellt werden. Dadurch können mögliche Muster oder Abhängigkeiten zwischen aufeinanderfolgenden Zufallszahlen erkannt werden.

## Verwendete Technologien

- Java
- Java Swing
- Java2D
- Git
- GitHub
- Visual Studio Code / IntelliJ IDEA / Eclipse

## Technische Vorgaben

Das Projekt wird vollständig in Java umgesetzt.

Die Bedienoberfläche wird mit Java Swing und gegebenenfalls Java2D erstellt.

Die Versionsverwaltung erfolgt über Git und GitHub.

Alle Teammitglieder müssen den bestehenden Code und das Klassenkonzept erklären können.

## Projektstruktur

Geplante Projektstruktur:

```text
src/
 ├── Main.java
 ├── rng/
 │    ├── RandomGenerator.java
 │    ├── LCG.java
 │    ├── MiddleSquareGenerator.java
 │    └── XORShiftGenerator.java
 ├── analysis/
 │    ├── HistogramAnalyzer.java
 │    ├── CorrelationAnalyzer.java
 │    └── Statistics.java
 └── ui/
      ├── MainFrame.java
      ├── HistogramPanel.java
      └── ScatterPlotPanel.java