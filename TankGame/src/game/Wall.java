package TankGame.src.game;


import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends GameObject {
    float x, y;
    BufferedImage img;
    // String imgpath in contrusctor?
//    public Wall(){
//        img = ResourceManager.getSprite("wall");
//
//    }

    public Wall(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;

    }

    public void drawImage(Graphics g) {
        g.drawImage(this.img, (int)x,(int)y, null);
    }

//    public Rectangle getBounds(){
//        return new Rectangle(x, y, img.getWidth(), img.getHeight());
//    }
}
