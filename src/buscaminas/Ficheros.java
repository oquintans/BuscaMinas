/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Oscar
 */
public class Ficheros {

    PrintWriter fich;
    Scanner sc;
    String nombF = "ScoreBoard.txt";
    String delim = ",";
    String linea;
    ArrayList<Score> scoreBoard;

    public void add(String nombre, int tiempo) {
        try {
            fich = new PrintWriter(new FileWriter(new File(nombF), true));
            sc = new Scanner(new File(nombF));
            scoreBoard = new ArrayList();
            Score l = new Score(nombre, tiempo);
            scoreBoard.add(l);
            fich.println(l);
        } catch (IOException ex) {
            Logger.getLogger(Ficheros.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fich.close();
            sc.close();
        }
    }

    public void visualizar() {
        scoreBoard = new ArrayList();
        try {
            String aux = "";
            sc = new Scanner(new File(nombF)).useDelimiter(delim);
            while (sc.hasNextLine()) {
                linea = sc.nextLine();
                String[] l = linea.split(",");
                for (int i = 0; i < l.length; i += 2) {
                    scoreBoard.add(new Score(l[i], Integer.parseInt(l[i + 1])));
                }
            }
            for (int j = 0; j < scoreBoard.size(); j++) {
                aux = aux + scoreBoard.get(j).getNombre() + " -----> " + scoreBoard.get(j).getTiempo() + " segundos.\n";
            }
            JOptionPane.showMessageDialog(null, aux);
        } catch (IOException ex) {
            Logger.getLogger(Ficheros.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            sc.close();
        }
    }

    public void ordenar() {
        try {
            sc = new Scanner(new File(nombF)).useDelimiter(delim);
            fich = new PrintWriter(new FileWriter(new File(nombF), true));
            while (sc.hasNextLine()) {
                linea = sc.nextLine();
                String[] l = linea.split(",");
                for (int i = 0; i < l.length; i += 2) {
                    scoreBoard.add(new Score(l[i], Integer.parseInt(l[i + 1])));
                }
            }
            scoreBoard.sort(null);

        } catch (IOException ex) {
            Logger.getLogger(Ficheros.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fich.close();
            sc.close();
        }
    }

    public int bestTime() {
        scoreBoard = new ArrayList();
        int aux = 0;
        try {

            sc = new Scanner(new File(nombF)).useDelimiter(delim);
            while (sc.hasNextLine()) {
                linea = sc.nextLine();
                String[] l = linea.split(",");
                for (int i = 0; i < l.length; i += 2) {
                    scoreBoard.add(new Score(l[i], Integer.parseInt(l[i + 1])));
                }
            }
            aux = scoreBoard.get(0).getTiempo();
            return aux;
        } catch (IOException ex) {
            Logger.getLogger(Ficheros.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            sc.close();
        }
        return aux;

    }
}
