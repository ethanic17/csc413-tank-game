package TankGame.src.game;

import java.awt.image.BufferedImage;

public class SpeedPowerup extends GameObject {

    public SpeedPowerup(float x, float y, BufferedImage img) {
        super(x, y, img);
        this.x = x;
        this.y = y;
        this.img = img;

    }
}
