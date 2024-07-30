package TankGame.src.game;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class ResourceManager { // loads all resources in one class and can refer to them in other classes
    private final static Map<String, BufferedImage> sprites = new HashMap<>();
    private final static Map<String, Sound> sounds = new HashMap<>();
    private final static Map<String, List<BufferedImage>> anims = new HashMap<>();
    private final static Map<String, Integer> animInfo = new HashMap<>(){{
        put("bullethit", 24);
        put("bulletshoot", 24);
        put("powerpick", 32);
        put("puffsmoke", 32);
        put("rocketflame", 16);
        put("rockethit", 32);
    }};

    private static BufferedImage loadSprite(String path) throws IOException {
        return ImageIO.read(
                Objects.requireNonNull(ResourceManager.class.getClassLoader().getResource(path), "Resource %s was not found".formatted(path))
        );
    }

    private static Sound loadSound(String path) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        AudioInputStream ais = AudioSystem.getAudioInputStream(
                Objects.requireNonNull(
                        ResourceManager.class.getClassLoader().getResource(path),
                        "Sound Resource %s was not found".formatted(path)
        ));
        Clip c = AudioSystem.getClip();
        c.open(ais); // loads sound into clip/ais
        Sound s = new Sound(c);
        return s;
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

    private static void initSounds()  {
        try {
            ResourceManager.sounds.put("bg", loadSound("sounds/Music.mid")); // java only supports wav?
            ResourceManager.sounds.put("bullet_collide", loadSound("sounds/bullet.wav"));
            ResourceManager.sounds.put("pickup", loadSound("sounds/pickup.wav"));
            ResourceManager.sounds.put("shooting", loadSound("sounds/shotfiring.wav"));
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }

    }

    private static void initAnims(){
        String baseFormat = "animations/%s/%s_%04d.png";
        ResourceManager.animInfo.forEach((animationName, frameCount) -> { // refers to animInfo Hashmap to format resource list for each frames
            List<BufferedImage> f = new ArrayList<>(frameCount);
            try {
                for (int i = 0; i < frameCount; i++) {
                    String spritePath = String.format(baseFormat, animationName, animationName, i);
                    f.add(loadSprite(spritePath));
                }
                ResourceManager.anims.put(animationName, f);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


    // error handling
    public static void loadAssets() {
        try {
            initSprites();
            initSounds();
            initAnims();
//            System.out.println();
        } catch (IOException e) {
            throw new RuntimeException("Error loading assets", e);
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
//
//    public static void main(String[] args) { // check if assets r all loading, if any errors then u fucked up somewhere lol
//        ResourceManager.loadAssets();
//        System.out.println();
//    }
}
