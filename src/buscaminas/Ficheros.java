/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Oscar
 */
@SuppressWarnings("rawtypes")
public class Ficheros {

    PrintWriter fich;
    Scanner sc;
    String nombF = "archivos/scores/ScoreBoardF.txt";
    String nombM = "archivos/scores/ScoreBoardM.txt";
    String nombD = "archivos/scores/ScoreBoardD.txt";
    String delim = ",";
    String linea;
    List<Score> scoreBoard;

    public void add(String nombre, int tiempo, String dif) {
        String nomb;
        if (dif.equalsIgnoreCase("Facil")) {
            nomb = nombF;
        } else if (dif.equalsIgnoreCase("Medio")) {
            nomb = nombM;
        } else {
            nomb = nombD;
        }
        try {
            fich = new PrintWriter(new FileWriter(new File(nomb), true));
            sc = new Scanner(new File(nomb));
            scoreBoard = new ArrayList<>();
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

    public void visualizar(ArrayList<Score> scoreBoard) {
        String aux = "";
        for (int j = 0; j < scoreBoard.size(); j++) {
            aux = aux + scoreBoard.get(j).getNombre() + " -----> " + scoreBoard.get(j).getTiempo() + " segundos.\n";
        }
        JOptionPane.showMessageDialog(null, aux, "mejores tiempos", JOptionPane.PLAIN_MESSAGE, Tablero.boom);

    }

    public void ordenar(String dif) {
        scoreBoard = new ArrayList<>();
        String nomb;
        if (dif.equalsIgnoreCase("Facil")) {
            nomb = nombF;
        } else if (dif.equalsIgnoreCase("Medio")) {
            nomb = nombM;
        } else {
            nomb = nombD;
        }
        try {
            sc = new Scanner(new File(nomb)).useDelimiter(delim);
            fich = new PrintWriter(new FileWriter(new File(nomb), true));
            while (sc.hasNextLine()) {
                linea = sc.nextLine();
                String[] l = linea.split(",");
                for (int i = 0; i < l.length; i += 2) {
                    scoreBoard.add(new Score(l[i], Integer.parseInt(l[i + 1])));
                }
            }
            Collections.sort(scoreBoard);
            sc.close();
            fich.close();
            File f = new File(nombF);
            f.delete();
            f.createNewFile();
            fich = new PrintWriter(new FileWriter(new File(nombF), true));
            for (int i = 0; i < scoreBoard.size(); i++) {
                fich.println(scoreBoard.get(i));
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ficheros.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ficheros.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fich.close();
            sc.close();
        }
    }

    public String bestTime(String dif) {
        scoreBoard = new ArrayList<>();
        String aux = "";
        String nomb;
        if (dif.equalsIgnoreCase("Facil")) {
            nomb = nombF;
        } else if (dif.equalsIgnoreCase("Medio")) {
            nomb = nombM;
        } else {
            nomb = nombD;
        }
        try {
            sc = new Scanner(new File(nomb)).useDelimiter(delim);
            while (sc.hasNextLine()) {
                linea = sc.nextLine();
                String[] l = linea.split(",");
                for (int i = 0; i < l.length; i += 2) {
                    scoreBoard.add(new Score(l[i], Integer.parseInt(l[i + 1])));
                }
            }
            aux = scoreBoard.get(0).getNombre() + " -> " + scoreBoard.get(0).getTiempo();
            return aux;
        } catch (IOException ex) {
            Logger.getLogger(Ficheros.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            sc.close();
        }
        return aux;

    }
}
