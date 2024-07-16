package TankGame.src.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class HealthPowerup extends GameObject {
    float x, y;
    BufferedImage img;
//    public HealthPowerup(String imgpath){
//        img = ResourceManager.getSprite("health");
//    }

    public HealthPowerup(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;

    }

    public void drawImage(Graphics g) {
        g.drawImage(this.img, (int)x,(int)y, null);
    }
}
