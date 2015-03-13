package buscaminas;

public class ScoreTime {

    private float tiempo = 0;
    private long tInicio = 0, tFinal = 0;

    public ScoreTime() {
    }

    public long empiezaJuego() {
        tInicio = System.currentTimeMillis();
        return tInicio;
    }

    public long acabaJuego() {
        tFinal = System.currentTimeMillis();
        return tFinal;
    }

    public float tiempo() {
        tiempo = (float) (tInicio - tFinal) / 1000;
        return tiempo;
    }

    public float getTiempo() {
        return tiempo;
    }
}
