package TankGame.src.game;


import TankGame.src.GameConstants;
import TankGame.src.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.List;

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
    private ArrayList<GameObject> gObjs = new ArrayList<>(); // game objects

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
                this.tick++; // performance dependant
                this.t1.update(); // update tank 1
                this.t2.update(); //updates tank 2
                this.checkCollisions();
                this.renderFrame();
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

    private void checkCollisions() {

        final Tank t = this.t1;
        for (int i = 0; i < this.gObjs.size() ; i++) {
            // did tank 1 hit something in gameobject list

        }
        final List<Bullet> t2Ammo = this.t2.getAmmo();
        for (int i = 0; i < t2Ammo.size(); i++) {
            // did t1 get hit by any t2 bullets
        }

        final Tank t2 = this.t2;
        for (int i = 0; i < this.gObjs.size() ; i++) {
            // did tank 2 hit something in gameobject list

        }
        final List<Bullet> t1Ammo = this.t1.getAmmo();
        for (int i = 0; i < t1Ammo.size(); i++) {
            // did t1 get hit by any t1 bullets
        }

//        for (int i = 0; i < this.gObjs.size(); i++) {
//            GameObject obj1 = this.gObjs.get(i);
//            for (int j = 0; j < this.gObjs.size(); j++) {
//                if (i == j) continue; // so you dont collide with yourself (tank) constantly
//                GameObject obj2 = this.gObjs.get(j);
//                if (obj1.getHitbox().intersects(obj2.getHitbox())) {
//                    System.out.println("Collision Detected");
//                }
//            }
//        }
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
//                System.out.println(Arrays.toString(objs));
                for (int col = 0; col < objs.length; col++) {
                    String gameItem = objs[col];
                    if("0".equals(gameItem)) continue; // 0 means nothing in csv, skips over
                    this.gObjs.add(GameObject.newInstance(gameItem, col*32, row*32)); // implementation inside GameObject class
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

        this.gObjs.add(t1);
        this.gObjs.add(t2);
        //TODO add tank1 and  2 to game objects

        background = ResourceManager.getSprite("background");
    }

    private void renderFloor(Graphics buffer){
        BufferedImage floor = ResourceManager.getSprite("floor");
        for (int i = 0; i < GameConstants.GAME_SCREEN_WIDTH; i += floor.getWidth()) {
            for (int j = 0; j < GameConstants.GAME_SCREEN_HEIGHT; j += floor.getHeight()) {
                buffer.drawImage(floor, i, j, null);
            }
        }
    }

    static double scaleFactor = .25; // for minimap to be bigger or smaller (scaling on diff resolutions)

    private void displayMiniMap(Graphics2D onScreenPanel) { // creating minimap from BufferedImage floor/world
        BufferedImage mm = this.world.getSubimage(0, 0, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT);
        double mmx = GameConstants.GAME_SCREEN_WIDTH/2. - (GameConstants.GAME_SCREEN_WIDTH*scaleFactor)/2; // top left corner onf minimap
        double mmy = GameConstants.GAME_SCREEN_HEIGHT - (GameConstants.GAME_SCREEN_HEIGHT*scaleFactor);

        AffineTransform scaler = AffineTransform.getTranslateInstance(mmx, mmy); // moves Minimap to a certain portion of map
        scaler.scale(scaleFactor, scaleFactor); // scales minimap
        onScreenPanel.drawImage(mm,scaler,null);
    }

    private void displaySplitScreen(Graphics2D onScreenPanel) {
        BufferedImage lh = this.world.getSubimage((int)this.t1.getScreen_x(), (int)this.t1.getScreen_y(), GameConstants.GAME_SCREEN_WIDTH/2, GameConstants.GAME_SCREEN_HEIGHT);
        onScreenPanel.drawImage(lh, 0, 0, null);

        BufferedImage rh = this.world.getSubimage((int)this.t2.getScreen_x(), (int)this.t2.getScreen_y(), GameConstants.GAME_SCREEN_WIDTH/2, GameConstants.GAME_SCREEN_HEIGHT);
        onScreenPanel.drawImage(rh, GameConstants.GAME_SCREEN_WIDTH/2, 0, null);
    }

    private void renderFrame() { // offloading onto anthoer thread
        // 1:05:46 in CSC 413 MM & Split Screen Lexture
        // ?? doesnt work bc of buffer
        // dispatch thread ang game thread
    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // for bg image, load badkground before tanks
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        //        this.setBackground(Color.BLACK);

        // TODO why dpes this shit drop my fps to like 3
        buffer.drawImage(background, 0, 0, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT, this);

        // renders game objects from csv
        this.gObjs.forEach(go -> go.drawImage(buffer));

        //TODO close breakable wall buffer when shot at by tank

        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
//        g2.drawImage(world, 0, 0, null);

        // FYI: being drawn to JPanel meaning Split Screen MUST be before Minimap otherwise minimap is hidden below splitscreen stack
        this.displaySplitScreen(g2);
        this.displayMiniMap(g2);

    }




}
