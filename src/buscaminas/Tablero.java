package buscaminas;

import java.awt.*;
import java.awt.event.*;
import static java.lang.Math.*;
import javax.swing.*;

public class Tablero implements ActionListener, MouseListener {

    public static int TAM = 20, FIL = 20, COL = 20; //Tamaño de los arrays
    private static final int MINA = 9;
    public static int NMINAS = 20;
    private final int[][] tab = new int[FIL][COL]; //Matriz tablero
    private static JFrame ventana;
    public static String dificultad;
    static final ImageIcon icono = new ImageIcon(Tablero.class.getResource("/imagenes/mina.gif"));
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
    public static boolean win, loose;

    public Tablero() {
        // cr = new CronometroThread();
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        ventana = new JFrame();
        if ("Facil".equals(dificultad)) {
            ventana.setSize(800, 600);
            ventana.setResizable(false);
        }
        if ("Media".equals(dificultad)) {
            ventana.setSize(1024, 720);
            ventana.setResizable(false);
        }
        if ("Dificil".equals(dificultad)) {
            ventana.setSize(1200, 720);
            ventana.setResizable(false);
        }
        win = false;
        loose = false;
        ventana.setVisible(true);
        ventana.setIconImage(icono.getImage());
        botonMatriz = new JButton[FIL][COL];
        ventana.add(new CronometroThread());
        ventana.getContentPane().add(time(), BorderLayout.NORTH);
        ventana.getContentPane().add(creaPanelBotones(), BorderLayout.CENTER);
        ventana.getContentPane().add(creaPanelJuegoNuevo(), BorderLayout.SOUTH);
        ventana.setMinimumSize(new Dimension(800, 600));
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        time.empiezaJuego();
        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev) {
                ventana.dispose();
                MenuP b2Menu = new MenuP();
            }
        });
    }

    private JPanel time() {
        JPanel panel = new JPanel();
        JLabel scoreT;
        /**
         * Ficheros fich = new Ficheros(); fich.ordenar(dificultad);*
         */
        panel.setBounds(330, 5, 100, 25);
        panel.add(scoreT = new JLabel("\n"));
        return panel;
    }

    private JPanel creaPanelBotones() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(FIL, COL, 0, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (int i = 0; i < FIL; i++) {
            for (int j = 0; j < COL; j++) {
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
        for (int f = 0; f < FIL; f++) {
            for (int c = 0; c < COL; c++) {
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
                posM1 = (int) (Math.random() * (FIL));
                posM2 = (int) (Math.random() * (COL));

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
            if (posM1 != FIL - 1) {
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
            if (posM2 != COL - 1) {
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
            if (posM1 != FIL - 1) {
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
            if (posM1 != FIL - 1) {
                if (posM2 != COL - 1) {
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
                if (posM2 != COL - 1) {
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
        win = true;
        loose = false;
        time.acabaJuego();
        time.tiempo();
        CronometroThread.detenido = true;
        //recoge nombre aki para time usa el getTiempo
        //fich = new Ficheros();
        BaseData bd = new BaseData();
        String nombre = JOptionPane.showInputDialog(null, time.getTiempo() + " segundos" + "\nIntroduce tu nombre", "YOU Win", JOptionPane.PLAIN_MESSAGE);
        bd.insert(time.getTiempo(), nombre, dificultad);
        bVolver.setEnabled(true);

    }

    public void perder() {
        JOptionPane.showMessageDialog(null, "", "HAS PERDIDO!!", JOptionPane.PLAIN_MESSAGE, bomba);
        bVolver.setEnabled(true);
        CronometroThread.detenido = true;
        CronometroThread.crono_hilo.suspend();
        // CronometroThread.crono_hilo.stop();
        System.out.println(CronometroThread.crono_hilo.isAlive());
        win = false;
        loose = true;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        JButton evBoton = (JButton) ev.getSource();

        if (ev.getSource().equals(bVolver)) {
            win = false;
            ventana.dispose();
            MenuP menu = new MenuP();
            contador = 0;
            contBandera = 0;
            contBanderaBuena = 0;

        }
        for (int i = 0; i < FIL; i++) {
            for (int j = 0; j < COL; j++) {
                //enseñamos minas you lose
                if (evBoton.equals(botonMatriz[i][j]) && tab[i][j] == MINA) {
                    botonMatriz[i][j].setIcon(icono);
                    for (int k = 0; k < FIL; k++) {
                        for (int l = 0; l < COL; l++) {
                            if (tab[k][l] == MINA) {
                                botonMatriz[k][l].setIcon(icono);
                                time.acabaJuego();
                            } else {
                                botonMatriz[k][l].setEnabled(false);
                            }

                        }
                    }
                    this.perder();

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

                //new game set enabled pasa a true y contador a 0
                if (bLimpiar.equals(evBoton)) {

                    win = false;
                    loose = false;
                    CronometroThread.detenido = false;
                    CronometroThread.setSegundos(0);
                    CronometroThread.setMinutos(0);
                    CronometroThread.setHoras(0);
                    CronometroThread.crono_hilo.resume();
                    // CronometroThread ct=new CronometroThread();
                    botonMatriz[i][j].setEnabled(true);
                    this.limpiarTablero();
                    this.crear();
                    botonMatriz[i][j].setIcon(null);
                    botonMatriz[i][j].setBackground(null);
                    this.minas();
                    contador = 0;
                    contBandera = 0;
                    contBanderaBuena = 0;
                    bVolver.setEnabled(false);

                }
                if (contador == ((FIL * COL) - NMINAS) && tab[i][j] == MINA) {
                    for (int k = 0; k < FIL; k++) {
                        for (int l = 0; l < COL; l++) {
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
        if (contador == ((FIL * COL) - NMINAS) && ev.getSource() != bVolver && win == false) {
            this.ganar();
        }
    }

    public void mostrarCeros(int i, int j) {
        if (contador == 1) {
            time.empiezaJuego();
        }
        for (int i2 = max(0, i - 1); i2 < min(FIL, i + 2); i2++) {
            for (int j2 = max(0, j - 1); j2 < min(COL, j + 2); j2++) {
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
        for (int i = 0; i < FIL; i++) {
            for (int j = 0; j < COL; j++) {
                if (e.isMetaDown() && botonMatriz[i][j].equals(e.getSource()) && botonMatriz[i][j].isEnabled() && contBandera + contBanderaBuena < NMINAS && loose == false) {
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
                if (e.isMetaDown() && botonMatriz[i][j].equals(e.getSource()) && !botonMatriz[i][j].isEnabled() && loose == false) {
                    botonMatriz[i][j].setIcon(null);
                    botonMatriz[i][j].setEnabled(true);
                    if (tab[i][j] == MINA) {
                        contBanderaBuena--;
                    } else {
                        contBandera--;
                    }
                }
            }
        }
        if (contBanderaBuena == NMINAS && win == false) {
            this.ganar();
        }
    }

    @Override
    public void mousePressed(MouseEvent e
    ) {

    }

    @Override
    public void mouseReleased(MouseEvent e
    ) {

    }

    @Override
    public void mouseEntered(MouseEvent e
    ) {

    }

    @Override
    public void mouseExited(MouseEvent e
    ) {

    }

}
