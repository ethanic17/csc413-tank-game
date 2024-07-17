package TankGame.src.game;

import java.awt.image.BufferedImage;

public class Wall extends GameObject {

    public Wall(float x, float y, BufferedImage img) { // x, y, img declared inside GameObject parent class
        this.x = x;
        this.y = y;
        this.img = img;

    }

}
