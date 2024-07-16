package TankGame.src.game;


import TankGame.src.GameConstants;
import TankGame.src.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author anthony-pc
 */
public class GameWorld extends JPanel implements Runnable {

    private BufferedImage world;
    private Tank t1, t2;
    private final Launcher lf;
    private long tick = 0;
    private BufferedImage background;
    private BufferedImage wall, bwall, health, shield, speed, bullet;

//    ArrayList gObjs = new ArrayList();
    ArrayList<GameObject> gObjs = new ArrayList<>(); // game objects

    /**
     *
     */
    public GameWorld(Launcher lf) {
        this.lf = lf;
    }

    @Override
    public void run() {
        try {
            while (true) {
                this.tick++;
//                this.background.update();
                this.t1.update(); // update tank 1
                this.t2.update(); //updates tank 2
//                this.bwall.update(); // updates breakwable wall TODO
                this.repaint();   // redraw game
                /*
                 * Sleep for 1000/144 ms (~6.9ms). This is done to have our 
                 * loop run at a fixed rate per/sec. 
                */
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame() { //TODO fix rest game method
        this.tick = 0;
        this.t1.setX(300);
        this.t1.setY(300);

        this.t2.setX(400);
        this.t2.setY(400);
    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void InitializeGame() {
        this.world = new BufferedImage(GameConstants.GAME_SCREEN_WIDTH,
                GameConstants.GAME_SCREEN_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        int row = 0;
        InputStreamReader isr = new InputStreamReader(
                Objects.requireNonNull(
                    ResourceManager.class.getClassLoader().getResourceAsStream("world.csv")
                )
        );

        try (BufferedReader mapReader = new BufferedReader(isr)) { // reads csv file and creates game objects based on integer values in csv
            while(mapReader.ready()) {
                String line = mapReader.readLine();
                String[] objs = line.split(",");
                System.out.println(Arrays.toString(objs));
                for (int col = 0; col < objs.length; col++) {
                    String gameItem = objs[col];
                    if("0".equals(gameItem)) continue; // 0 means nothing in csv, skips over
                    this.gObjs.add(GameObject.newInstance(gameItem, col*32, row*32)); // implementation inside GameObject class
//                    System.out.println(gameItem);
                }
                row++;
            }
        } catch  (IOException e){
            throw new RuntimeException(e);
        }

        t1 = new Tank(300, 300, 0, 0, (short) 0, ResourceManager.getSprite("t1"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.lf.getJf().addKeyListener(tc1);

        t2 = new Tank(400, 400, 0, 0,  (short) 0, ResourceManager.getSprite("t2"));
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        this.lf.getJf().addKeyListener(tc2);

        background = ResourceManager.getSprite("background");
//        wall = ResourceManager.getSprite("wall");
//        bwall = ResourceManager.getSprite("bwall");
//        health = ResourceManager.getSprite("health");
//        shield = ResourceManager.getSprite("shield");
//        speed = ResourceManager.getSprite("speed");
//        bullet = ResourceManager.getSprite("bullet");

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // for bg image, load badkground before tanks
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();

//        buffer.drawImage(background, 0, 0, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT, this);
//        this.setBackground(Color.BLACK);


        // renders game objects from csv
        this.gObjs.forEach(go -> go.drawImage(buffer));

//        buffer.drawImage(wall, 100, 100, this); // walls test image
//        buffer.drawImage(bwall, 200, 200, this); // breakable walls test image
        //TODO close breakable wall buffer when shot at by tank

        //TODO resize images to fit
        //TODO better way to manage sprite buffer images? 3D array?
//        buffer.drawImage(health, 150, 150, this); // health bar test image
//        buffer.drawImage(shield, 250, 250, this); // shield test image
//        buffer.drawImage(speed, 350, 350, this); // speed test image
//        buffer.drawImage(bullet, 450, 450, this); // bullet test image


        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        g2.drawImage(world, 0, 0, null);

    }
}
