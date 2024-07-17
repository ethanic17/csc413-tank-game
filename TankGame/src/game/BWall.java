package TankGame.src.game;


import java.awt.*;
import java.awt.image.BufferedImage;

public class BWall extends GameObject {

    public void breakWall(){
//        img.close();
//        buffer.dispose();
    }

    public BWall(float x, float y, BufferedImage img) { // x, y, img declared inside GameObject parent class
        this.x = x;
        this.y = y;
        this.img = img;

    }


}
