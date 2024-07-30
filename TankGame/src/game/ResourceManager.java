package TankGame.src.game;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ResourceManager { // loads all resources in one class and can refer to them in other classes
    private final static Map<String, BufferedImage> sprites = new HashMap<>();
    private final static Map<String, Sound> sounds = new HashMap<>();
    private final static Map<String, List<BufferedImage>> anims = new HashMap<>();

    private static BufferedImage loadSprite(String path) throws IOException {
        return ImageIO.read(
                Objects.requireNonNull(ResourceManager.class.getClassLoader().getResource(path), "Resource %s was not found".formatted(path))
        );
    }

    // throws IOException throws exception back to caller, if not handled there it'll throw back to JVM and then JVM crashes
    private static void initSprites() throws IOException {
        ResourceManager.sprites.put("t1", loadSprite("tank1.png"));
        ResourceManager.sprites.put("t2", loadSprite("tank2.png"));
        ResourceManager.sprites.put("menu", loadSprite("title.png"));
        ResourceManager.sprites.put("wall", loadSprite("wall.png"));
        ResourceManager.sprites.put("bwall", loadSprite("wall2.png"));
        ResourceManager.sprites.put("background", loadSprite("backgroundsculk.png"));
        ResourceManager.sprites.put("floor", loadSprite("backgroundsculk.png")); // duplicate/same background/floor ?
        ResourceManager.sprites.put("speed", loadSprite("speed.png"));
        ResourceManager.sprites.put("health", loadSprite("health.png"));
        ResourceManager.sprites.put("shield", loadSprite("shield.png"));
        ResourceManager.sprites.put("bullet", loadSprite("bullet.png"));
    }

    private static void initSounds(){
        //TODO load sounds
    }

    private static void initAnimations(){
        //TODO load animations
    }


    // error handling
    public static void loadAssets() {
        try {
            initSprites();
        } catch (IOException e) {
            throw new RuntimeException("Error loading assets", e);
        }
    }

    public static void loadSounds() {
        try {
            initSounds();
        } catch (Exception e) {
            throw new RuntimeException("Error loading sounds", e);
        }
    }

    public static void loadAnimations() {
        try {
            initAnimations();
        } catch (Exception e) {
            throw new RuntimeException("Error loading animations", e);
        }
    }

    public static BufferedImage getSprite(String key) { // getter method for Launcher to get resources/sprite
        if (!ResourceManager.sprites.containsKey(key)) {
            throw new IllegalArgumentException(
                    "Resource %s is not in map".formatted(key)
            );
        }
        return ResourceManager.sprites.get(key);
    }

    public static Sound getSound(String key) {
        if (!ResourceManager.sounds.containsKey(key)) {
            throw new IllegalArgumentException(
                    "Resource %s is not in map".formatted(key)
            );
        }
        return ResourceManager.sounds.get(key);
    }

    public static List<BufferedImage> getAnim(String key) {
        if (!ResourceManager.anims.containsKey(key)) {
            throw new IllegalArgumentException(
                    "Resource %s is not in map".formatted(key)
            );
        }
        return ResourceManager.anims.get(key);
    }

    public static void main(String[] args) { // check if assets r all loading, if any errors then u fucked up somewhere lol
        ResourceManager.loadAssets();
        ResourceManager.loadSounds();
        ResourceManager.loadAnimations();
        System.out.println();
    }
}
