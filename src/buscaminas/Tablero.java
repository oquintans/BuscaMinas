package buscaminas;

public class Tablero {

    private static final int TAM = 9; //Tamaño de los arrays
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
                System.out.print(tab[i][j]);
            }
        }
    }

    public void minas() {
        // genera de manera aleatoria las posiciones en la matriz de 10 minas
        for (int i = 0; i <= 10; i++) {
            int posM1 = (int) (Math.random() * (TAM - 2)) + 1;
            int posM2 = (int) (Math.random() * (TAM - 2)) + 1;
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

            //Hay que buscar la manera de que no se repitan las posiciones, no me voy a rayar por el momento
        //EDIT vale se supone qe con todos estos if podemos cambiar los numeros alrededor de las minas para que muestren el numero de minas con las qe estan en contacto pero creo qe falla en algo.
    }
}
