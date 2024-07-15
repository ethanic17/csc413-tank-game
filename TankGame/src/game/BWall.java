package TankGame.src.game;


import java.awt.image.BufferedImage;

public class BWall {
    BufferedImage img;
    public BWall(String imgpath){
        img = ResourceManager.getSprite("bwall");

    }

    public void breakWall(){
//        img.close();
//        buffer.dispose();
    }
}
