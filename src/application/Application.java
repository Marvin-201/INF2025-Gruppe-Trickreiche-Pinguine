package application;

import javax.swing.SwingUtilities;
import ui.MainFrame;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}


// Generator           Bester Seed    Verteilung pro Klasse
//  ━━━━━━━━━━━━━━━━━━  ━━━━━━━━━━━━━  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
//   LCG                        6270    100, 100, 95, 99, 101, 100, 99, 103, 101, 102
//  ──────────────────  ─────────────  ───────────────────────────────────────────────
//   Middle-Square              1532    weiterhin sehr ungleichmäßig
//  ──────────────────  ─────────────  ───────────────────────────────────────────────
//   XOR Shift                  7547    98, 95, 98, 104, 102, 101, 99, 101, 102, 100
//  ──────────────────  ─────────────  ───────────────────────────────────────────────
//   Mersenne Twister           5259    100, 101, 101, 96, 102, 101, 98, 95, 106, 100