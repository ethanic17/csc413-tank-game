package TankGame.src.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Wall {
    BufferedImage img;
    public Wall(String imgpath){
        img = ResourceManager.getSprite("bwall"); //TODO bwall in resource manager

    }
}
