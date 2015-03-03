/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

/**
 *
 * @author Oscar
 */
public class Tablero {

    private static final int TAM = 9; //Tamaño de los arrays
    private String[] col = new String[TAM]; //Array de columnas
    private String[] fil = new String[TAM]; //Array de filas
    private String[][] tab = new String[fil.length][col.length]; //Matriz tablero

    public void crear() {
        // creamos el tablero inicializando todos los valores de la matriz a "0"
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                tab[i][j] = "0";
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
            int posM1 = (int) (Math.random() * 9);
            int posM2 = (int) (Math.random() * 9);
            System.out.println(posM1 + " " + posM2);
            tab[posM1][posM2] = "x";
            //Hay que buscar la manera de que no se repitan las posiciones, no me voy a rayar por el momento
        }
    }

}
