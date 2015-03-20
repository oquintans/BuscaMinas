package buscaminas;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        JDialog.setDefaultLookAndFeelDecorated(true);
        ScoreTime time = new ScoreTime();
        Ficheros fich = new Ficheros();
        int option = JOptionPane.showOptionDialog(null, "Selecciona", "gameMenu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Empezar juego", "Mostrar highScore", "Salir"}, "Empezar juego");
        if (option == 0) {
            Tablero tab = new Tablero();
            tab.crear();
            tab.minas();
            tab.visualizar();
        }
        if (option == 1) {
            //Tablero bestScore
            fich.ordenar();
            fich.visualizar();
        } else if (option != 0 && option != 1) {
            System.exit(0);
        }
    }

}
