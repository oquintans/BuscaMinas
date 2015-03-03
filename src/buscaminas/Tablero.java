package buscaminas;

public class Tablero {

    public static final int TAM = 10; //Tamaño de los arrays
    public static final int MINAS = 10;
    private int[] col = new int[TAM]; //Array de columnas
    private int[] fil = new int[TAM]; //Array de filas
    private int[][] tab = new int[fil.length][col.length]; //Matriz tablero

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
}
// 

