package buscaminas;

import java.util.concurrent.TimeUnit;

public class ScoreTime {

    private int tiempo;
    private long tInicio, tFinal;

    public ScoreTime() {
    }
    //aki no hay muxo que explicar el TimeUnit pasa los milliseg a seg
    public long empiezaJuego() {
        long tIniciomill = System.currentTimeMillis();
        tInicio = TimeUnit.MILLISECONDS.toSeconds(tIniciomill);
        return tInicio;
    }

    public long acabaJuego() {
        long tFinalmill = System.currentTimeMillis();
        tFinal = TimeUnit.MILLISECONDS.toSeconds(tFinalmill);
        return tFinal;
    }
    
    public int tiempo() {
        long aux = (tFinal - tInicio);
        long mins = TimeUnit.SECONDS.toMinutes(aux);
        tiempo =(int)aux;
        System.out.println(tiempo);
        return tiempo;
    }
    
    public int getTiempo() {
        return tiempo;
    }
    
}
