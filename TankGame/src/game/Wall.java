package TankGame.src.game;


import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall {
    int x, y;
    BufferedImage img;
    public Wall(String imgpath){
        img = ResourceManager.getSprite("wall");

    }

//    public Rectangle getBounds(){
//        return new Rectangle(x, y, img.getWidth(), img.getHeight());
//    }
}
