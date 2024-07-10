package TankGame.src.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall {
    private float x;
    private float y;
    private float width;
    private float height;
    private BufferedImage img;

    Wall(float x, float y, float width, float height, BufferedImage img){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, 100, 100, null);
        g2d.setColor(Color.RED);
        g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());
    }
}
