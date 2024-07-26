package TankGame.src.game;


import TankGame.src.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject implements Poolable {
    private float x;
    private float y;
    private float vx;
    private float vy;
    private float angle;

    private float R = 5;
    private float ROTATIONSPEED = 3.0f;

//    private final int tankId;

    private BufferedImage img;

    public Bullet(BufferedImage img) {
//        super( 0, 0, img);
        this.vx = 0;
        this.vy = 0;
        this.angle = 0;
    }

    public Bullet(float x, float y, float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.img = img;
        this.angle = angle;
    }

    void update() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        checkWall();
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void checkBorder() {
        if (x < 30) x = 30;
        if (y < 40) y = 40;
        if (x >= GameConstants.GAME_SCREEN_WIDTH - 88) {
            x = GameConstants.GAME_SCREEN_WIDTH - 88;
        }
        if (y >= GameConstants.GAME_SCREEN_HEIGHT - 80) {
            y = GameConstants.GAME_SCREEN_HEIGHT - 80;
        }
    }



    // checks if wall is in direction
    private void checkWall() {

    }



    void drawImage(Graphics2D g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        g.drawImage(this.img, rotation, null);
//        g2d.setColor(Color.RED);
        //g2d.rotate(Math.toRadians(angle), bounds.x + bounds.width/2, bounds.y + bounds.height/2);
//        g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());

    }

    @Override
    public void initObject(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void initObject(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    @Override
    public void resetObject() {
        this.x = -5;
        this.y = -5;
    }

//    public void handleCollision(Object with) {
//        if (with instanceof Bullet) {
//            // lose health
//        } else if (with instanceof HealthPowerup) {
//            // gain health
//        } else if (with instanceof ShieldPowerup) {
//            // gain shield
//        } else if (with instanceof SpeedPowerup) {
//            // gain speed
//        } else if (with instanceof Wall) {
//            // stop or undo move
//        }
//    }

}
