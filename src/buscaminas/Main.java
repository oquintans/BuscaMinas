package buscaminas;

public class Main {

    public static void main(String[] args) {
        
        Tablero tab=new Tablero();
        tab.crear();
        tab.minas();
        tab.visualizar();
    }
    
}
