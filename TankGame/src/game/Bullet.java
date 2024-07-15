package TankGame.src.game;


import java.awt.image.BufferedImage;

public class Bullet {
    BufferedImage img;
    public Bullet(String imgpath){
        img = ResourceManager.getSprite("bullet");
    }


}
