package TankGame.src.game;


import TankGame.src.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject implements Poolable, Updateable {
    private float x;
    private float y;
    private float vx;
    private float vy;
    private int tankID = -1;
    private float angle;

    private float R = 5;
    private float ROTATIONSPEED = 3.0f;

//    private final int tankId;
//
//    private BufferedImage img;

    public Bullet(BufferedImage img) {
        super( 0, 0, img);
        this.vx = 0;
        this.vy = 0;
        this.angle = 0;
    }

    public Bullet(float x, float y, float angle, BufferedImage img) {
        super(x, y, img);
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.img = img;
        this.angle = angle;
    }

    public void update(GameWorld gw) {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitbox.setLocation((int)x, (int)y);
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

    public void setOwner(int id){
        this.tankID = id;
    }


    public void drawImage(Graphics g) {
        if (this.img == null) {
            System.err.println("Bullet img is not intlized");
            return;
        }

        final Graphics2D g2d = (Graphics2D) g;
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        g2d.drawImage(this.img, rotation, null);
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

    public int getOwner() { // which tank owns bullet shot
        return this.tankID;
    }

    @Override
    public void handleCollision(GameObject by) { // handles wall collision when bullet hits a bwall or wall so doesnt fly thru wall
        if(by instanceof Wall) {
            this.x -= this.vx;
            this.y -= this.vy;
            this.vx = 0;
            this.vy = 0;
        } else if(by instanceof BWall) {
            GameWorld gw = GameWorld.getInstance();
            gw.removeGameObject(by);
            gw.repaint();
        }
    }





}
