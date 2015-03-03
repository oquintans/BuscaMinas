package buscaminas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Tablero extends JFrame implements ActionListener {

    private static final int TAM = 10; //Tamaño de los arrays
    private static final int MINAS = 10;
    private final int[] col = new int[TAM]; //Array de columnas
    private final int[] fil = new int[TAM]; //Array de filas
    private final int[][] tab = new int[fil.length][col.length]; //Matriz tablero
    private static JFrame ventana;
    private static final String bomb = "◙";
    private static final String safeStep = "¥";
    private static final ImageIcon icono = new ImageIcon(Tablero.class.getResource("/icono/icono.png"));

    public Tablero() {
        ventana = new JFrame();
        JButton[][] botonTab = new JButton[TAM][TAM];
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(800, 600);
        ventana.setVisible(true);
        ventana.setIconImage(icono.getImage());
        
    }

    public void crear() {
        // creamos el tablero inicializando todos los valores de la matriz a "0"
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                tab[i][j] = 0;
            }
        }
    }

    public void visualizar() {
        for (int i = 0; i < tab.length; i++) {
            System.out.print("\n");
            for (int j = 0; j < tab[i].length; j++) {
                if (tab[i][j] == 9) {
                    System.out.print("x" + "\t");
                } else {
                    System.out.print(tab[i][j] + "\t");
                }
            }
        }
    }

    public void minas() {
// genera de manera aleatoria las posiciones en la matriz de 10 minas
        int[] aux1 = new int[TAM];
        int[] aux2 = new int[TAM];
        for (int i = 0; i < MINAS; i++) {
            int posM1;
            int posM2;
            boolean cont;
            do {
                cont = false;
                aux1[i] = 0;
                aux2[i] = 0;
                posM1 = (int) (Math.random() * (TAM - 2)) + 1;
                posM2 = (int) (Math.random() * (TAM - 2)) + 1;
                for (int j = 0; j < aux1.length - 1; j++) {
                    if (aux1[j] == posM1 && aux2[j] == posM2) {
                        cont = true;
                    }
                }
                aux1[i] = posM1;
                aux2[i] = posM2;
            } while (cont);
//Todo el codigo de aqui arriba es para generar numeros sin repetir.
//Y aqui abajo cambia los numeros alrededor de las minas para que cuente el numero de minas en contacto
            System.out.println(posM1 + " " + posM2);
            tab[posM1][posM2] = 9; //MINA = 9
            if (tab[posM1 + 1][posM2] != 9) {
                tab[posM1 + 1][posM2] = tab[posM1 + 1][posM2] + 1;
            }
            if (tab[posM1 - 1][posM2] != 9) {
                tab[posM1 - 1][posM2] = tab[posM1 - 1][posM2] + 1;
            }
            if (tab[posM1][posM2 + 1] != 9) {
                tab[posM1][posM2 + 1] = tab[posM1][posM2 + 1] + 1;
            }
            if (tab[posM1][posM2 - 1] != 9) {
                tab[posM1][posM2 - 1] = tab[posM1][posM2 - 1] + 1;
            }
            if (tab[posM1 + 1][posM2 - 1] != 9) {
                tab[posM1 + 1][posM2 - 1] = tab[posM1 + 1][posM2 - 1] + 1;
            }
            if (tab[posM1 + 1][posM2 + 1] != 9) {
                tab[posM1 + 1][posM2 + 1] = tab[posM1 + 1][posM2 + 1] + 1;
            }
            if (tab[posM1 - 1][posM2 - 1] != 9) {
                tab[posM1 - 1][posM2 - 1] = tab[posM1 - 1][posM2 - 1] + 1;
            }
            if (tab[posM1 - 1][posM2 + 1] != 9) {
                tab[posM1 - 1][posM2 + 1] = tab[posM1 - 1][posM2 + 1] + 1;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
