package buscaminas;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Tablero extends JFrame implements ActionListener {

    private static final int TAM = 10; //Tamaño de los arrays
    private static final int MINAS = 10;
    private final int[][] tab = new int[TAM][TAM]; //Matriz tablero
    private static JFrame ventana;
    private static final ImageIcon icono = new ImageIcon(Tablero.class.getResource("/icono/icono.png"));
    private final JButton[][] botonMatriz;
    private final Font fuente = new Font("Arial", Font.BOLD, 25);
    private JButton bLimpiar;

    public Tablero() {
        ventana = new JFrame();
        JButton[][] botonTab = new JButton[TAM][TAM];
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(800, 600);
        ventana.setVisible(true);
        ventana.setIconImage(icono.getImage());
        ventana.setBackground(Color.BLACK);
        botonMatriz = new JButton[TAM][TAM];
        ventana.getContentPane().add(scoreTime(),BorderLayout.NORTH);
        ventana.getContentPane().add(creaPanelBotones(), BorderLayout.CENTER);
        ventana.getContentPane().add(creaPanelJuegoNuevo(), BorderLayout.SOUTH);
        ventana.pack();
        ventana.setMinimumSize(new Dimension(800, 600));
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev) {
                dispose();
            }
        });
    }
    private JPanel scoreTime(){
        JPanel panel=new JPanel();
        JLabel scoreT;
        panel.add(scoreT=new JLabel("tempo"),BorderLayout.WEST);
        return panel;
    }
    private JPanel creaPanelBotones() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(TAM, TAM, 0, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {
                botonMatriz[i][j] = new JButton();
                botonMatriz[i][j].addActionListener(this);
                botonMatriz[i][j].setFont(fuente);
                panel.add(botonMatriz[i][j]);
                
            }
        }
        return panel;
    }

    private JPanel creaPanelJuegoNuevo() {
        JPanel panel = new JPanel();
        
        panel.add(bLimpiar = new JButton("New Game"), BorderLayout.CENTER);
        bLimpiar.addActionListener(this);
        return panel;
    }

    public void limpiarTablero() {
        for (int f = 0; f < TAM; f++) {
            for (int c = 0; c < TAM; c++) {
                botonMatriz[f][c].setText(null);
            }
        }
    }

    public void crear() {
        // creamos el tablero inicializando todos los valores de la matriz a "0"
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                tab[i][j] = 0;
                botonMatriz[i][j].setText(null);
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
                posM1 = (int) (Math.random() * (TAM));
                posM2 = (int) (Math.random() * (TAM));

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
            //¿Porque hay dos "if" o incluso 3? para que no salten errores de outOfBounds al poner minas en los extremos del tablero.
            System.out.println(posM1 + " " + posM2);
            tab[posM1][posM2] = 9; //MINA = 9
            if (posM1 != 9) {
                if (tab[posM1 + 1][posM2] != 9) {
                    tab[posM1 + 1][posM2] = tab[posM1 + 1][posM2] + 1;
                }
                /*
                 0 0 0
                 0 x 0
                 0 1 0
                 */
            }
            if (posM1 != 0) {
                if (tab[posM1 - 1][posM2] != 9) {
                    tab[posM1 - 1][posM2] = tab[posM1 - 1][posM2] + 1;
                }
                /*
                 0 1 0
                 0 x 0
                 0 0 0
                 */
            }
            if (posM2 != 9) {
                if (tab[posM1][posM2 + 1] != 9) {
                    tab[posM1][posM2 + 1] = tab[posM1][posM2 + 1] + 1;
                }
                /*
                 0 0 0
                 0 x 1
                 0 0 0
                 */
            }

            if (posM2 != 0) {
                if (tab[posM1][posM2 - 1] != 9) {
                    tab[posM1][posM2 - 1] = tab[posM1][posM2 - 1] + 1;
                }
                /*
                 0 0 0
                 1 x 0
                 0 0 0
                 */
            }
            if (posM1 != 9) {
                if (posM2 != 0) {
                    if (tab[posM1 + 1][posM2 - 1] != 9) {
                        tab[posM1 + 1][posM2 - 1] = tab[posM1 + 1][posM2 - 1] + 1;
                    }
                    /*
                     0 0 0
                     0 x 0
                     1 0 0
                     */
                }
            }
            if (posM1 != 9) {
                if (posM2 != 9) {
                    if (tab[posM1 + 1][posM2 + 1] != 9) {
                        tab[posM1 + 1][posM2 + 1] = tab[posM1 + 1][posM2 + 1] + 1;
                    }
                    /*
                     0 0 0
                     0 x 0
                     0 0 1
                     */
                }
            }
            if (posM1 != 0) {
                if (posM2 != 0) {
                    if (tab[posM1 - 1][posM2 - 1] != 9) {
                        tab[posM1 - 1][posM2 - 1] = tab[posM1 - 1][posM2 - 1] + 1;
                    }
                    /*
                     1 0 0
                     0 x 0
                     0 0 0
                     */
                }
            }
            if (posM1 != 0) {
                if (posM2 != 9) {
                    if (tab[posM1 - 1][posM2 + 1] != 9) {
                        tab[posM1 - 1][posM2 + 1] = tab[posM1 - 1][posM2 + 1] + 1;
                    }
                    /*
                     0 0 1
                     0 x 0
                     0 0 0
                     */
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        JButton evBoton = (JButton) ev.getSource();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (evBoton.equals(botonMatriz[i][j]) && tab[i][j] == 9) {
                    botonMatriz[i][j].setIcon(icono);
                    for (int k = 0; k < 10; k++) {
                        for (int l = 0; l < 10; l++) {
                            if (tab[k][l]==9){
                                botonMatriz[k][l].setIcon(icono);
                            }else{
                            botonMatriz[k][l].setText(String.valueOf(tab[k][l]));
                        }}
                    }
                }
                if (evBoton.equals(botonMatriz[i][j])&& tab[i][j] != 9) {
                    botonMatriz[i][j].setText(String.valueOf(tab[i][j]));
                }
                if(bLimpiar.equals(evBoton)){
                    this.crear();
                    botonMatriz[i][j].setIcon(null);
                    this.minas();
                    
                }
            }
        }

    }
}
// 

