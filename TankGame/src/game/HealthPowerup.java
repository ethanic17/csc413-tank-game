package TankGame.src.game;
import java.awt.image.BufferedImage;

public class HealthPowerup extends GameObject {

    public HealthPowerup(float x, float y, BufferedImage img) {
        super(x, y, img);
        this.x = x;
        this.y = y;
        this.img = img;

    }


}
