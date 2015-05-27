package buscaminas;

import java.awt.*;
import java.awt.event.*;
import static java.lang.Math.*;
import javax.swing.*;

public class Tablero implements ActionListener, MouseListener {

    public static int TAM = 20; //Tamaño de los arrays
    private static final int MINA = 9;
    public static int NMINAS = 20;
    private final int[][] tab = new int[TAM][TAM]; //Matriz tablero
    private static JFrame ventana;
    public static String dificultad;
    static final ImageIcon icono = new ImageIcon(Tablero.class.getResource("/icono/Icono.png"));
    static final ImageIcon boom = new ImageIcon(Tablero.class.getResource("/icono/sonriente-carita.gif"));
    private static final ImageIcon bomba = new ImageIcon(Tablero.class.getResource("/imagenes/Boom.png"));
    private static final ImageIcon bandera = new ImageIcon(Tablero.class.getResource("/imagenes/bandeira.png"));
    private final JButton[][] botonMatriz;
    private final Font fuente = new Font("Verdana", Font.BOLD, 25);
    private JButton bLimpiar, bVolver;
    private static int contador = 0, contBanderaBuena = 0, contBandera = 0;
    ScoreTime time = new ScoreTime();
    CronometroThread cr;
    Ficheros fich;

    public Tablero() {
        cr = new CronometroThread();
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        ventana = new JFrame();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(800, 600);
        ventana.setVisible(true);
        ventana.setIconImage(icono.getImage());
        botonMatriz = new JButton[TAM][TAM];
        ventana.add(cr);
        ventana.getContentPane().add(scoreTime(), BorderLayout.NORTH);
        ventana.getContentPane().add(creaPanelBotones(), BorderLayout.CENTER);
        ventana.getContentPane().add(creaPanelJuegoNuevo(), BorderLayout.SOUTH);
        //ventana.pack();
        ventana.setMinimumSize(new Dimension(800, 600));
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev) {
                ventana.dispose();
            }
        });
    }

    private JPanel scoreTime() {
        JPanel panel = new JPanel();
        JLabel scoreT;
        Ficheros fich = new Ficheros();
        fich.ordenar(dificultad);
        panel.setBounds(330, 5, 100, 25);
        panel.add(scoreT = new JLabel("\n"));
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
                botonMatriz[i][j].addMouseListener(this);
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
        panel.add(bVolver = new JButton("Back to Menu"));
        bVolver.addActionListener(this);
        bVolver.setEnabled(false);
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
        int[] aux1 = new int[NMINAS];
        int[] aux2 = new int[NMINAS];

        for (int i = 0; i < NMINAS; i++) {
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
            if (posM1 != TAM - 1) {
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
            if (posM2 != TAM - 1) {
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
            if (posM1 != TAM - 1) {
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
            if (posM1 != TAM - 1) {
                if (posM2 != TAM - 1) {
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
                if (posM2 != TAM - 1) {
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

    public void ganar() {
        time.acabaJuego();
        time.tiempo();
        //recoge nombre aki para time usa el getTiempo
        fich = new Ficheros();
        String nombre = JOptionPane.showInputDialog(null, time.getTiempo() + " segundos" + "\nIntroduce tu nombre", "YOU Win", JOptionPane.PLAIN_MESSAGE);
        fich.add(nombre, time.getTiempo(), dificultad);
        bVolver.setEnabled(true);
        CronometroThread.detenido = true;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        JButton evBoton = (JButton) ev.getSource();

        if (ev.getSource().equals(bVolver)) {
            ventana.dispose();
            MenuP menu = new MenuP();
            cr.setSegundos(0);
            cr.setMinutos(0);
            cr.setHoras(0);

        }
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
                    JOptionPane.showMessageDialog(null, "", "HAS PERDIDO!!", JOptionPane.PLAIN_MESSAGE, bomba);
                    bVolver.setEnabled(true);

                    CronometroThread.detenido = true;

                }
                if (evBoton.equals(botonMatriz[i][j]) && tab[i][j] != MINA) {
                    botonMatriz[i][j].setText(String.valueOf(tab[i][j]));
                    evBoton.setEnabled(false);
                    contador++;

                    if (tab[i][j] == 0) {
                        botonMatriz[i][j].setBackground(Color.lightGray);
                        botonMatriz[i][j].setText(null);
                        this.mostrarCeros(i, j);
                    }
                    if (tab[i][j] == 1) {
                        botonMatriz[i][j].setBackground(Color.blue);
                    }
                    if (tab[i][j] == 2) {
                        botonMatriz[i][j].setBackground(Color.green);
                    }
                    if (tab[i][j] == 3) {
                        botonMatriz[i][j].setBackground(Color.red);
                    }
                    if (tab[i][j] == 4) {
                        botonMatriz[i][j].setBackground(Color.YELLOW);
                    }
                    if (tab[i][j] == 5) {
                        botonMatriz[i][j].setBackground(Color.MAGENTA);
                    }
                    if (tab[i][j] == 6) {
                        botonMatriz[i][j].setBackground(Color.cyan);
                    }
                    if (tab[i][j] == 7) {
                        botonMatriz[i][j].setBackground(Color.darkGray);
                    }
                    if (tab[i][j] == 8) {
                        botonMatriz[i][j].setBackground(Color.black);
                    }

                }
                //primer boton que tocas , empieza el juego
                if (contador == 1) {
                    time.empiezaJuego();
                }
                //new game set enabled pasa a true y contador a 0
                if (bLimpiar.equals(evBoton)) {
                    CronometroThread.detenido = false;
                    CronometroThread.setSegundos(0);
                    CronometroThread.setMinutos(0);
                    CronometroThread.setHoras(0);
                    // CronometroThread ct=new CronometroThread();
                    botonMatriz[i][j].setEnabled(true);
                    this.limpiarTablero();
                    this.crear();
                    botonMatriz[i][j].setIcon(null);
                    botonMatriz[i][j].setBackground(null);
                    this.minas();
                    contador = 0;
                    bVolver.setEnabled(false);

                }
                if (contador == ((TAM * TAM) - NMINAS) && tab[i][j] == MINA) {
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
        if (contador == ((TAM * TAM) - NMINAS) && ev.getSource() != bVolver) {
            this.ganar();
        }
    }

    public void mostrarCeros(int i, int j) {
        if (contador == 1) {
            time.empiezaJuego();
        }
        for (int i2 = max(0, i - 1); i2 < min(TAM, i + 2); i2++) {
            for (int j2 = max(0, j - 1); j2 < min(TAM, j + 2); j2++) {
                if (botonMatriz[i2][j2].isEnabled()) {
                    botonMatriz[i2][j2].setText(String.valueOf(tab[i2][j2]));
                    botonMatriz[i2][j2].setEnabled(false);
                    System.out.println(contador);
                    contador++;

                    if (tab[i2][j2] == 0) {
                        mostrarCeros(i2, j2);
                    }
                    if (tab[i2][j2] == 0) {
                        botonMatriz[i2][j2].setBackground(Color.LIGHT_GRAY);
                        botonMatriz[i2][j2].setText(null);
                    }
                    if (tab[i2][j2] == 1) {
                        botonMatriz[i2][j2].setBackground(Color.blue);
                    }
                    if (tab[i2][j2] == 2) {
                        botonMatriz[i2][j2].setBackground(Color.green);
                    }
                    if (tab[i2][j2] == 3) {
                        botonMatriz[i2][j2].setBackground(Color.red);
                    }
                    if (tab[i2][j2] == 4) {
                        botonMatriz[i2][j2].setBackground(Color.yellow);
                    }
                    if (tab[i2][j2] == 5) {
                        botonMatriz[i2][j2].setBackground(Color.magenta);
                    }
                    if (tab[i2][j2] == 6) {
                        botonMatriz[i2][j2].setBackground(Color.cyan);
                    }
                    if (tab[i2][j2] == 7) {
                        botonMatriz[i2][j2].setBackground(Color.darkGray);
                    }
                    if (tab[i2][j2] == 8) {
                        botonMatriz[i2][j2].setBackground(Color.black);
                    }
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {
                if (e.isMetaDown() && botonMatriz[i][j].equals(e.getSource()) && botonMatriz[i][j].isEnabled() && contBandera + contBanderaBuena < NMINAS) {
                    botonMatriz[i][j].setIcon(bandera);
                    botonMatriz[i][j].setEnabled(false);
                    if (tab[i][j] == MINA) {
                        contBanderaBuena++;
                        System.out.println(contBanderaBuena);
                    } else {
                        contBandera++;
                    }
                    break;
                }
                if (e.isMetaDown() && botonMatriz[i][j].equals(e.getSource()) && !botonMatriz[i][j].isEnabled()) {
                    botonMatriz[i][j].setIcon(null);
                    botonMatriz[i][j].setEnabled(true);
                    contBandera--;

                }
            }
        }
        if (contBanderaBuena == NMINAS) {
            this.ganar();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
