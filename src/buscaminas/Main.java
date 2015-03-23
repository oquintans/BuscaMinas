package buscaminas;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        JDialog.setDefaultLookAndFeelDecorated(true);
        ScoreTime time = new ScoreTime();
        Ficheros fich = new Ficheros();
        ImageIcon bomba = new ImageIcon(Tablero.class.getResource("/imagenes/boom.png"));
        int option;
        do {
            option = JOptionPane.showOptionDialog(null, "Selecciona una opción", "gameMenu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,Tablero.icono, new Object[]{"Empezar juego", "Mostrar highScore", "Salir"}, "Empezar juego");
            switch (option) {
                case 0:
                    Tablero tab = new Tablero();
                    tab.crear();
                    tab.minas();
                    tab.visualizar();
                    break;
                case 1:
                    //Tablero bestScore
                    fich.ordenar();
                    fich.visualizar();
                    break;
                case 2:
                    if (option == -1 && option == 2 && option == 0) {
                        System.exit(0);
                    }

            }
        } while (option != -1 && option != 2 && option != 0);
    }
}
