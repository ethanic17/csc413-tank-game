package TankGame.src.game;

import TankGame.src.GameConstants;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anthony-pc
 */
public class Tank extends GameObject {
    private static ResourcePool<Bullet> bulletPool = new ResourcePool<>("bullet", Bullet.class, 500);
    private float screen_x; // cannot be final
    private float screen_y;
    private float x;
    private float y;
    private float vx;
    private float vy;
    private float angle;

    private float R = 5;
    private float ROTATIONSPEED = 3.0f;

    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;

    Bullet b;
    List<Bullet> ammo = new ArrayList<Bullet>(); // store list here or in gameworld? Easier for collisions here, Tank owns the bullets
    private long cooldown = 1500; // 1.5 seconds, how fast the user can shoot before cooldown/reload
    private long LastFired = 0;

    Tank(float x, float y, float vx, float vy, float angle, BufferedImage img) {
//        super(x, y, img);
        this.screen_x = x;
        this.screen_y = y;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
    }

    void setX(float x){ this.x = x; }

    void setY(float y) { this. y = y;}

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShoot() {this.ShootPressed = true; }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleShoot() {this.ShootPressed = false; }

    public float getScreen_x() {
        return screen_x;
    }

    public float getScreen_y() {
        return screen_y;
    }

    void update() { // pass GameWorld or GameState as a new object to get bullet access for tank?
        if (this.UpPressed) {
            this.moveForwards();
        }

        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }

        if (this.RightPressed) {
            this.rotateRight();
        }

        long currentTime = System.currentTimeMillis();
        if (this.ShootPressed && currentTime > this.LastFired + this.cooldown) { // adds a cooldown for shooting bullets
            this.LastFired = currentTime;
            var p = ResourcePools.getPooledInstance("bullet");
            p.initObject(x, y, angle);
            this.ammo.add((Bullet)p);
//            this.ammo.add( // creates new bullets inside arraylist ammo
//                    new Bullet(x + this.img.getWidth()/2f,
//                            y+ this.img.getHeight()/2f,
//                            angle, // +15, -15, multi-shot powerup?
//                            ResourceManager.getSprite("bullet")));

        }

        for (int i = 0; i< this.ammo.size(); i++){
            this.ammo.get(i).update();
        }

        if (b != null) { // only allows user to have 1 bullet at a time
            b.update();
        }
        centerScreen();

    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx =  Math.round(R * Math.cos(Math.toRadians(angle)));
        vy =  Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
       checkBorder();
       checkWall();
    }

    private void moveForwards() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        checkWall();
    }

    private void centerScreen(){ // preventing rasteriazation error/tank going off to nowhere
        this.screen_x = this.x - GameConstants.GAME_SCREEN_WIDTH/4f;
        this.screen_y = this.y - GameConstants.GAME_SCREEN_HEIGHT/2f;

        // lower bound check
        if (this.screen_x < 0) screen_x = 0;
        if (this.screen_y < 0) screen_y = 0;

        //upper bound check
        if (this.screen_x > GameConstants.GAME_SCREEN_WIDTH - GameConstants.GAME_SCREEN_WIDTH/2f) { // going abobe max, set back to max
            this.screen_x = GameConstants.GAME_SCREEN_WIDTH - GameConstants.GAME_SCREEN_WIDTH/2f;
        }
        if (this.screen_y > GameConstants.GAME_SCREEN_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT) {
            this.screen_y = GameConstants.GAME_SCREEN_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT;
        }
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

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    public void drawImage(Graphics2D g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        g.drawImage(this.img, rotation, null);

        for(int i = 0; i< this.ammo.size(); i++){
            this.ammo.get(i).drawImage(g);
        }
//        if( b != null) {
//            b.drawImage(g);
//        }

    }
}
