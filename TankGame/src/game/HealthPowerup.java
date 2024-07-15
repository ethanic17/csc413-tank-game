package TankGame.src.game;

import java.awt.image.BufferedImage;

public class HealthPowerup {
    BufferedImage img;
    public HealthPowerup(String imgpath){
        img = ResourceManager.getSprite("health");
    }
}
