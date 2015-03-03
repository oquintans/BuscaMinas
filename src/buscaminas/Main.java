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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Tablero tab=new Tablero();
        tab.crear();
        tab.minas();
        tab.visualizar();
    }
    
}
