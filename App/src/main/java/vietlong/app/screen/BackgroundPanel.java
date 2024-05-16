package vietlong.app.screen;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
public class BackgroundPanel extends JPanel {
    private final Image backgroundImage;

    public BackgroundPanel(String imagePath){
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    @Override
    protected  void paintComponent(Graphics g){
        super.paintComponent(g);
        if (backgroundImage!=null){
            g.drawImage(backgroundImage,0,0,getWidth(),getHeight(),this);
        }
    }
}
