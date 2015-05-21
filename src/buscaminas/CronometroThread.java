package buscaminas;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CronometroThread extends JPanel implements Runnable {

    private static final long serialVersionUID = 3287496534931916605L;
//Atributos 
    Thread crono_hilo = null;
    private final boolean sw = true;
    private static boolean detenido = false;
    private int horas = 0;
    private int minutos = 0;
    private int segundos = 0;
    static JLabel[] lbl_digitos = null;
    JLabel etiqueta = null;
    static char[] crono = {0, 0, ':', 0, 0, ':', 0, 0};

    //Constructor
    public CronometroThread() {
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
