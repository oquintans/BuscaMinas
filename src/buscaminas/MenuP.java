package buscaminas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import javax.swing.*;

public class MenuP implements ActionListener {

    Ficheros fich = new Ficheros();
    private final JFrame frame;
    private final Fondo panel;
    private final JLabel eDificultad;
    private final JComboBox dificultad;
    private final JButton begin, score, exit;
    BaseData bd;

    public MenuP() {
        BaseData.conDerby();
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("BUSCAMINAS");
        frame.setSize(300, 450);
        frame.setIconImage(Tablero.icono.getImage());
        panel = new Fondo("/imagenes/minefield.jpg");
        eDificultad = new JLabel("Selecciona una dificultad");
        dificultad = new JComboBox();
        dificultad.addItem("Facil");
        dificultad.addItem("Media");
        dificultad.addItem("Dificil");
        dificultad.addItem("Personalizado");
        begin = new JButton("Empezar Juego");
        begin.addActionListener(this);
        score = new JButton("Ver mejores tiempos");
        score.addActionListener(this);
        exit = new JButton("Salir");
        exit.addActionListener(this);
        panel.add(eDificultad);
        panel.add(dificultad);
        panel.add(begin);
        panel.add(score);
        panel.add(exit);
        frame.add(panel);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(begin) && dificultad.getSelectedItem().toString().equals("Facil")) {
            Tablero.dificultad = "Facil";
            Tablero.NMINAS = 10;
            Tablero.FIL = 10;
            Tablero.COL = 10;
            Tablero tab = new Tablero();
            tab.crear();
            tab.minas();
            tab.visualizar();
            Tablero.win = false;
            CronometroThread.detenido = false;
            CronometroThread.setSegundos(0);
            CronometroThread.setMinutos(0);
            CronometroThread.setHoras(0);
            frame.dispose();
        }
        if (e.getSource().equals(begin) && dificultad.getSelectedItem().toString().equals("Media")) {
            Tablero.dificultad = "Media";
            Tablero.NMINAS = 30;
            Tablero.FIL = 15;
            Tablero.COL = 15;
            Tablero tab = new Tablero();
            tab.crear();
            tab.minas();
            tab.visualizar();
            Tablero.win = false;
            CronometroThread.detenido = false;
            CronometroThread.setSegundos(0);
            CronometroThread.setMinutos(0);
            CronometroThread.setHoras(0);
            frame.dispose();
        }
        if (e.getSource().equals(begin) && dificultad.getSelectedItem().toString().equals("Dificil")) {
            Tablero.dificultad = "Dificil";
            Tablero.NMINAS = 60;
            Tablero.FIL = 20;
            Tablero.COL = 20;
            Tablero tab = new Tablero();
            tab.crear();
            tab.minas();
            tab.visualizar();
            Tablero.win = false;
            CronometroThread.detenido = false;
            CronometroThread.setSegundos(0);
            CronometroThread.setMinutos(0);
            CronometroThread.setHoras(0);
            frame.dispose();
        }
        if (e.getSource().equals(begin) && dificultad.getSelectedItem().toString().equals("Personalizado")) {
            Tablero.dificultad = "Personalizado";
            Tablero.FIL = Integer.parseInt(JOptionPane.showInputDialog("Nº Filas"));
            Tablero.COL = Integer.parseInt(JOptionPane.showInputDialog("Nº Columnas"));
            do {
                Tablero.NMINAS = Integer.parseInt(JOptionPane.showInputDialog("Nº Minas"));
            } while (Tablero.NMINAS > (Tablero.FIL * Tablero.COL) - 1);
            Tablero tab = new Tablero();
            tab.crear();
            tab.minas();
            tab.visualizar();
            Tablero.win = false;
            CronometroThread.detenido = false;
            CronometroThread.setSegundos(0);
            CronometroThread.setMinutos(0);
            CronometroThread.setHoras(0);
            frame.dispose();
        }

        if (e.getSource().equals(score) && dificultad.getSelectedItem().toString().equals("Facil")) {
            bd.select("Facil");
        }
        if (e.getSource().equals(score) && dificultad.getSelectedItem().toString().equals("Media")) {
            bd.select("Media");
        }

        if (e.getSource().equals(score) && dificultad.getSelectedItem().toString().equals("Dificil")) {
            bd.select("Dificil");
        }
        if (e.getSource().equals(score) && dificultad.getSelectedItem().toString().equals("Personalizado")) {
            bd.select("Personalizado");
        }
        if (e.getSource().equals(exit)) {
            System.exit(0);
        }
    }

}
