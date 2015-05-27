package buscaminas;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CronometroThread extends JPanel implements Runnable {

    private static final long serialVersionUID = 3287496534931916605L;
//Atributos 
    static Thread crono_hilo = null;
    private final boolean sw = true;
    static boolean detenido = false;
    private static int horas;
    private static int minutos;
    private static int segundos;
    static JLabel[] lbl_digitos = null;
    JLabel etiqueta = null;
    static char[] crono = {0, 0, ':', 0, 0, ':', 0, 0};
    
    public static void setHoras(int horas) {
        CronometroThread.horas = horas;
    }

    public static void setMinutos(int minutos) {
        CronometroThread.minutos = minutos;
    }

    public static void setSegundos(int segundos) {
        CronometroThread.segundos = segundos;
    }

    //Constructor
    public CronometroThread() {
        horas = 0;
        minutos = 0;
        segundos = 0;
        lbl_digitos = new JLabel[8];
        //this.setLayout();
        this.setBounds(350, 5, 100, 20);

        for (int i = 0; i < lbl_digitos.length; i++) {
            lbl_digitos[i] = new JLabel();
            this.add(lbl_digitos[i]);
        }
        mostrarInfoCrono();

        crono_hilo = new Thread(this);
        crono_hilo.start();
    }

    public static void paraCrono() throws InterruptedException {
        crono_hilo.join(0);
    }

    private void mostrarInfoCrono() {
        for (int i = 0; i < lbl_digitos.length; i++) {
            if (crono[i] != ':') {
                lbl_digitos[i].setText(String.valueOf(crono[i]));
                lbl_digitos[i].setBounds(70 + (i * 13), 10, 13, 23);
            } else {
                lbl_digitos[i].setText(":");
            }
            lbl_digitos[i].setBounds(70 + (i * 13), 10, 13, 23);
        }
    }

    @Override
    public void run() {
// TODO Auto-generated method stub 
        StringBuffer tmp;
        while (sw == true) {
            try {
                if (detenido == false) {
                    mostrarInfoCrono();
                }
                Thread.sleep(1000);
                segundos++;
//Calculamos los valores

                if (segundos == 60) {
                    segundos = 0;
                    minutos++;
                    if (minutos == 60) {
                        minutos = 0;
                        horas++;
                        if (horas == 24) {
                            horas = 0;
                        }
                    }
                }

                //Formatear la hora
                tmp = new StringBuffer(8);

                if (horas < 10) {
                    tmp.append(0);
                }

                tmp.append(horas);
                tmp.append(':');

                if (minutos < 10) {
                    tmp.append(0);
                }

                tmp.append(minutos);
                tmp.append(':');

                if (segundos < 10) {
                    tmp.append(0);
                }

                tmp.append(segundos);
//System.out.println(tmp.toString());
                crono = tmp.toString().toCharArray();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
