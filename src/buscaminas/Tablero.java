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
    private static final int TAM=10; //Tamaño de los arrays
    private String [] col = new String[TAM]; //Array de columnas
    private String [] fil = new String[TAM]; //Array de filas
    private String [][] tab = new String[fil.length][col.length]; //Matriz tablero
    
    public void crear(){
        
        for(int i=0;i<tab.length;i++){
            System.out.print("\n");
            for(int j=0;j<tab[i].length;j++){
                tab[i][j]= "0";
                System.out.print(tab[i][j]);
            }
        }
    }
    
}
