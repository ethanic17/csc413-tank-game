package TankGame.src.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ShieldPowerup extends GameObject {
    float x, y;
    BufferedImage img;
//    public ShieldPowerup(String imgpath){
//        img = ResourceManager.getSprite("shield");
//
//    }

    public ShieldPowerup(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;

    }

    public void drawImage(Graphics g) {
        g.drawImage(this.img, (int)x,(int)y, null);
    }
}
