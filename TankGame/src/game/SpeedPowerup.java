package TankGame.src.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpeedPowerup extends GameObject {
    float x, y;
    BufferedImage img;
//    public SpeedPowerup(String imgpath){
//        img = ResourceManager.getSprite("speed");
//
//    }

    public SpeedPowerup(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;

    }

    public void drawImage(Graphics g) {
        g.drawImage(this.img, (int)x,(int)y, null);
    }
}
