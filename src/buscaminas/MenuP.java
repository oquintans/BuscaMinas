package buscaminas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MenuP implements ActionListener {

    Ficheros fich = new Ficheros();
    private final JFrame frame;
    private final JPanel panel;
    private final JLabel eDificultad;
    private final JComboBox dificultad;
    private final JButton begin, score, exit;

    public MenuP() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("BUSCAMINAS");
        frame.setSize(400, 400);
        frame.setIconImage(Tablero.icono.getImage());
        panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        eDificultad = new JLabel("Selecciona una dificultad");
        dificultad = new JComboBox();
        dificultad.addItem("Facil");
        dificultad.addItem("Media");
        dificultad.addItem("Dificil");
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
            Tablero.TAM = 10;
            Tablero tab = new Tablero();
            tab.crear();
            tab.minas();
            tab.visualizar();
            frame.dispose();
        }
        if (e.getSource().equals(begin) && dificultad.getSelectedItem().toString().equals("Media")) {
            Tablero.dificultad = "Media";
            Tablero.NMINAS = 30;
            Tablero.TAM = 15;
            Tablero tab = new Tablero();
            tab.crear();
            tab.minas();
            tab.visualizar();
            frame.dispose();
        }
        if (e.getSource().equals(begin) && dificultad.getSelectedItem().toString().equals("Dificil")) {
            Tablero.dificultad = "Dificil";
            Tablero.NMINAS = 60;
            Tablero.TAM = 20;
            Tablero tab = new Tablero();
            tab.crear();
            tab.minas();
            tab.visualizar();
            frame.dispose();
        }

        if (e.getSource().equals(score) && dificultad.getSelectedItem().toString().equals("Facil")) {
            fich.ordenar("facil");
            fich.visualizar("facil");
        }
        if (e.getSource().equals(score) && dificultad.getSelectedItem().toString().equals("Media")) {
            fich.ordenar("medio");
            fich.visualizar("medio");
        }

        if (e.getSource().equals(score) && dificultad.getSelectedItem().toString().equals("Dificil")) {
            fich.ordenar("dificil");
            fich.visualizar("dificil");
        }
        if (e.getSource().equals(exit)) {
            System.exit(0);
        }
    }

}
