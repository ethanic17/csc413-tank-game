package TankGame.src.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    protected float x, y; // because its protected, u dont have to declare this in all subclasses of GameObject or use setters and getters
    protected BufferedImage img;
    protected Rectangle hitbox;
    protected boolean hasCollided = false;

    public GameObject(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitbox = new Rectangle((int)x, (int)y, img.getWidth(), img.getHeight()); // creates a rectangle hitbox for every game object
    }
    /**
     * * creates new game object based on type
     * @param type the type of the object to create
     * @param x the x coordinate of the object
     * @param y the y coordinate of the object
     * @return a new instance of the object
     */

    public static GameObject newInstance(String type, float x, float y) {
        return switch(type) { //switch expressions
            case "3", "9" -> new Wall(x, y, ResourceManager.getSprite("wall"));
            case "2" -> new BWall(x, y, ResourceManager.getSprite("bwall"));
            case "4" -> new SpeedPowerup(x, y, ResourceManager.getSprite("speed"));
            case "5" -> new HealthPowerup(x, y, ResourceManager.getSprite("health"));
            case "6" -> new ShieldPowerup(x, y, ResourceManager.getSprite("shield"));
            default -> throw new IllegalArgumentException("Unsupported type -> %s\n".formatted(type));
        };
    }

    // FYI dont reduce visibility on abstract methods in subclasses
    public void drawImage(Graphics g) {
        g.drawImage(this.img, (int)x,(int)y, null); // Uses GameObject variables ONLY if there arent any in the subclass
    }

    public Rectangle getHitbox() {
        return hitbox.getBounds();
    }

    public boolean getHasCollided() {
        return this.hasCollided;
    }

    public void handleCollision(GameObject by) {
        // empty method for override in Tank.java and Bullet.java
        // reference obj GameObjects in GameWorld.java
    }
}
