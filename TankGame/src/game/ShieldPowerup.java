package TankGame.src.game;

import java.awt.image.BufferedImage;

public class ShieldPowerup extends GameObject {

    public ShieldPowerup(float x, float y, BufferedImage img) {
        super(x, y, img);
        this.x = x;
        this.y = y;
        this.img = img;
    }
}
