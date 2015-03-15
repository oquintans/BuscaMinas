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
public class Score implements Comparable {

    private String nombre;
    private int tiempo;

    public Score() {
    }

    public Score(String nombre, int tiempo) {
        this.nombre = nombre;
        this.tiempo = tiempo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    @Override
    public String toString() {
        return nombre + "," + tiempo;
    }

    @Override
    public int compareTo(Object t) {
        Score sc = (Score) t;
        if (this.tiempo == sc.tiempo) {
            return 0;
        } else if (this.tiempo > sc.tiempo) {
            return 1;
        } else {
            return -1;
        }
    }

}
