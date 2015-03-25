package buscaminas;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        JDialog.setDefaultLookAndFeelDecorated(true);
        Ficheros fich = new Ficheros();
        int option;
        int op;
        do {
            option = JOptionPane.showOptionDialog(null, "Selecciona una opción", "gameMenu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, Tablero.icono, new Object[]{"Empezar juego", "Mostrar highScore", "Salir"}, "Empezar juego");
            switch (option) {
                case 0:
                    do {
                        op = JOptionPane.showOptionDialog(null, "Selecciona una opción", "gameMenu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, Tablero.icono, new Object[]{"Facil", "Medio", "Dificil", "Exit"}, "Exit");
                        if (op == 0) {
                            Tablero.dificultad = "Facil";
                            Tablero.NMINAS = 10;
                            Tablero.TAM = 10;
                            break;
                        } else if (op == 1) {
                            Tablero.dificultad = "Medio";
                            Tablero.NMINAS = 30;
                            Tablero.TAM = 15;
                            break;
                        } else if (op == 2) {
                            Tablero.dificultad = "Dificil";
                            Tablero.NMINAS = 60;
                            Tablero.TAM = 20;
                            break;
                        } else {
                            System.exit(0);
                        }
                    } while (op != 3);
                    Tablero tab = new Tablero();
                    tab.crear();
                    tab.minas();
                    tab.visualizar();
                    break;
                case 1:
                    //Tablero bestScore
                    do {
                        op = JOptionPane.showOptionDialog(null, "Selecciona una opción", "gameMenu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, Tablero.icono, new Object[]{"Facil", "Medio", "Dificil", "Exit"}, "Exit");
                        if (op == 0) {
                            fich.ordenar("facil");
                            fich.visualizar("facil");
                            break;
                        } else if (op == 1) {
                            fich.ordenar("medio");
                            fich.visualizar("medio");
                            break;
                        } else if (op == 2) {
                            fich.ordenar("dificil");
                            fich.visualizar("dificil");
                            break;
                        } else {
                            System.exit(0);
                        }
                    } while (op != 3);

                    break;
                case 2:
                    if (option == -1 && option == 2 && option == 0) {
                        System.exit(0);
                    }
            }
        } while (option != -1 && option != 2 && option != 0);
    }
}
