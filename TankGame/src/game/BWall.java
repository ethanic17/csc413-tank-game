package TankGame.src.game;


import java.awt.*;
import java.awt.image.BufferedImage;

public class BWall extends GameObject {
    float x, y;
    BufferedImage img;
//    public BWall(String imgpath){
//        img = ResourceManager.getSprite("bwall");
//
//    }

    public void breakWall(){
//        img.close();
//        buffer.dispose();
    }

    public BWall(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;

    }

    public void drawImage(Graphics g) {
        g.drawImage(this.img, (int)x,(int)y, null);
    }
}
