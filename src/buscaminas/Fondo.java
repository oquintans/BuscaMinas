package buscaminas;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Fondo extends JPanel {

    // Atributo que guardara la imagen de Background que le pasemos.
    private ImageIcon background;
    private String url;

    public Fondo(String url) {
        this.url = url;
    }

    // Metodo que es llamado automaticamente por la maquina virtual Java cada vez que repinta
    @Override
    public void paintComponent(Graphics g) {

        /* Obtenemos el tamaño del panel para hacer que se ajuste a este
         cada vez que redimensionemos la ventana y se lo pasamos al drawImage */
        int width = this.getSize().width;
        int height = this.getSize().height;

        // Mandamos que pinte la imagen en el panel
        background = new ImageIcon(getClass().getResource(url));
        g.drawImage(background.getImage(), 0, 0, width, height, null);

        setOpaque(false);
        super.paintComponent(g);
    }

}
