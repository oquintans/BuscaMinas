package buscaminas;

import java.awt.*;
import java.awt.event.*;
import static java.lang.Math.*;
import javax.swing.*;

public class Tablero extends JFrame implements ActionListener {

    private static final int TAM = 10; //Tamaño de los arrays
    private static final int MINA = 9;
    private final int[][] tab = new int[TAM][TAM]; //Matriz tablero
    private static JFrame ventana;
    private static final ImageIcon icono = new ImageIcon(Tablero.class.getResource("/icono/icono.png"));
    private static final ImageIcon boom = new ImageIcon(Tablero.class.getResource("/icono//sonriente-carita.gif"));
    private final JButton[][] botonMatriz;
    private final Font fuente = new Font("Verdana", Font.BOLD, 25);
    private JButton bLimpiar;
    private int contador = 0;
    ScoreTime time = new ScoreTime();
    Graphics g;

    public Tablero() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        ventana = new JFrame();
        JButton[][] botonTab = new JButton[TAM][TAM];
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(800, 600);
        ventana.setVisible(true);
        ventana.setIconImage(icono.getImage());
        ventana.setBackground(Color.BLACK);
        botonMatriz = new JButton[TAM][TAM];
        ventana.getContentPane().add(scoreTime(), BorderLayout.NORTH);
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

    private void pintarBoom(Graphics g) {
        Dimension size = getSize();
        g.drawImage(boom.getImage(), 0, 0, size.width, size.height, null);
        setOpacity(0);
        super.paint(g);

    }

    private JPanel scoreTime() {
        JPanel panel = new JPanel();
        JLabel scoreT;
        Ficheros fich = new Ficheros();
        fich.ordenar();
        panel.add(scoreT = new JLabel("Best Time: " + fich.bestTime() + " segundos"), BorderLayout.WEST);
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
                panel.setVisible(true);
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
                if (tab[i][j] == MINA) {
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

        for (int i = 0; i < TAM; i++) {
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
            tab[posM1][posM2] = MINA; //MINA = 9
            if (posM1 != MINA) {
                if (tab[posM1 + 1][posM2] != MINA) {
                    tab[posM1 + 1][posM2] = tab[posM1 + 1][posM2] + 1;
                }
                /*
                 0 0 0
                 0 x 0
                 0 1 0
                 */
            }
            if (posM1 != 0) {
                if (tab[posM1 - 1][posM2] != MINA) {
                    tab[posM1 - 1][posM2] = tab[posM1 - 1][posM2] + 1;
                }
                /*
                 0 1 0
                 0 x 0
                 0 0 0
                 */
            }
            if (posM2 != MINA) {
                if (tab[posM1][posM2 + 1] != MINA) {
                    tab[posM1][posM2 + 1] = tab[posM1][posM2 + 1] + 1;
                }
                /*
                 0 0 0
                 0 x 1
                 0 0 0
                 */
            }

            if (posM2 != 0) {
                if (tab[posM1][posM2 - 1] != MINA) {
                    tab[posM1][posM2 - 1] = tab[posM1][posM2 - 1] + 1;
                }
                /*
                 0 0 0
                 1 x 0
                 0 0 0
                 */
            }
            if (posM1 != MINA) {
                if (posM2 != 0) {
                    if (tab[posM1 + 1][posM2 - 1] != MINA) {
                        tab[posM1 + 1][posM2 - 1] = tab[posM1 + 1][posM2 - 1] + 1;
                    }
                    /*
                     0 0 0
                     0 x 0
                     1 0 0
                     */
                }
            }
            if (posM1 != MINA) {
                if (posM2 != MINA) {
                    if (tab[posM1 + 1][posM2 + 1] != MINA) {
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
                    if (tab[posM1 - 1][posM2 - 1] != MINA) {
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
                if (posM2 != MINA) {
                    if (tab[posM1 - 1][posM2 + 1] != MINA) {
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
        Ficheros fich;

        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {
                //enseñamos minas you lose
                if (evBoton.equals(botonMatriz[i][j]) && tab[i][j] == MINA) {
                    botonMatriz[i][j].setIcon(icono);
                    for (int k = 0; k < TAM; k++) {
                        for (int l = 0; l < TAM; l++) {
                            if (tab[k][l] == MINA) {
                                botonMatriz[k][l].setIcon(icono);
                                time.acabaJuego();
                            } else {
                                botonMatriz[k][l].setEnabled(false);
                            }

                        }
                    }
                    JOptionPane.showMessageDialog(this, "Perdiste");

                }
                if (evBoton.equals(botonMatriz[i][j]) && tab[i][j] != MINA) {

                    botonMatriz[i][j].setText(String.valueOf(tab[i][j]));

                    evBoton.setEnabled(false);
                    contador++;
                    this.mostrarCeros(i, j);
                    if (tab[i][j] == 0) {
                        botonMatriz[i][j].setBackground(Color.white);
                    }
                    if (tab[i][j] == 1) {
                        botonMatriz[i][j].setBackground(Color.green);
                    }
                    if (tab[i][j] == 2) {
                        botonMatriz[i][j].setBackground(Color.yellow);
                    }
                    if (tab[i][j] == 3) {
                        botonMatriz[i][j].setBackground(Color.magenta);
                    }
                    if (tab[i][j] == 4) {
                        botonMatriz[i][j].setBackground(Color.red);
                    }
                    if (tab[i][j] == 5) {
                        botonMatriz[i][j].setBackground(Color.RED);
                    }
                    if (tab[i][j] == 6) {
                        botonMatriz[i][j].setBackground(Color.RED);
                    }
                    if (tab[i][j] == 7) {
                        botonMatriz[i][j].setBackground(Color.RED);
                    }
                    if (tab[i][j] == 8) {
                        botonMatriz[i][j].setBackground(Color.RED);
                    }

                }
                //primer boton que tocas , empieza el juego
                if (contador == 1) {
                    time.empiezaJuego();
                }
                //new game set enabled pasa a true y contador a 0
                if (bLimpiar.equals(evBoton)) {
                    botonMatriz[i][j].setEnabled(true);
                    this.limpiarTablero();
                    this.crear();
                    botonMatriz[i][j].setIcon(null);
                    botonMatriz[i][j].setBackground(null);
                    this.minas();
                    contador = 0;
                }
                if (contador == ((TAM * TAM) - TAM) && tab[i][j] == MINA) {
                    for (int k = 0; k < TAM; k++) {
                        for (int l = 0; l < TAM; l++) {
                            botonMatriz[k][l].setEnabled(false);
                            botonMatriz[k][l].setText(null);
                            if (tab[k][l] == MINA) {
                                botonMatriz[k][l].setIcon(boom);
                            }
                        }
                    }
                }

            }
        }
        if (contador == ((TAM * TAM) - TAM)) {
            time.acabaJuego();
            time.tiempo();
            //recoge nombre aki para time usa el getTiempo
            fich = new Ficheros();
            String nombre = JOptionPane.showInputDialog(null, time.getTiempo() + " segundos" + "\nIntroduce tu nombre", "YOU Win", JOptionPane.PLAIN_MESSAGE);
            fich.add(nombre, time.getTiempo());
        }

    }

    public void mostrarCeros(int i, int j) {

        if (tab[i][j] == 0) {
            for (int i2 = max(0, i - 1); i2 < min(TAM, i + 2); i2++) {
                for (int j2 = max(0, j - 1); j2 < min(TAM, j + 2); j2++) {
                    if (botonMatriz[i2][j2].isEnabled()) {
                        botonMatriz[i2][j2].setText(String.valueOf(tab[i2][j2]));
                        botonMatriz[i2][j2].setEnabled(false);
                        contador++;
                        System.out.println(contador);
                        if (tab[i2][j2] == 0) {
                            mostrarCeros(i2, j2);
                        }
                        if (tab[i2][j2] == 0) {
                            botonMatriz[i2][j2].setBackground(Color.white);
                        }
                        if (tab[i2][j2] == 1) {
                            botonMatriz[i2][j2].setBackground(Color.green);
                        }
                        if (tab[i2][j2] == 2) {
                            botonMatriz[i2][j2].setBackground(Color.yellow);
                        }
                        if (tab[i2][j2] == 3) {
                            botonMatriz[i2][j2].setBackground(Color.magenta);
                        }
                        if (tab[i2][j2] == 4) {
                            botonMatriz[i2][j2].setBackground(Color.red);
                        }
                        if (tab[i][j] == 5) {
                            botonMatriz[i][j].setBackground(Color.RED);
                        }
                        if (tab[i][j] == 6) {
                            botonMatriz[i][j].setBackground(Color.RED);
                        }
                        if (tab[i][j] == 7) {
                            botonMatriz[i][j].setBackground(Color.RED);
                        }
                        if (tab[i][j] == 8) {
                            botonMatriz[i][j].setBackground(Color.RED);
                        }
                    }
                }
            }
        }
    }
}
