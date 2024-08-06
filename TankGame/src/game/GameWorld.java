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
    private BufferedImage bulletImage;
    private ArrayList<GameObject> gObjs = new ArrayList<>(); // game objects
    private int winner = 0; // for win condition for endgame panel

    private int maxHealth = 100;
    private static GameWorld instance;
    static double scaleFactor = .20; // for minimap to be bigger or smaller (scaling on diff resolutions)

    /**
     *
     */
    public GameWorld(Launcher lf) {
        try {
            bulletImage = ImageIO.read(Objects.requireNonNull(ResourceManager.class.getClassLoader().getResourceAsStream("bullet.png")));
        } catch (IOException e){
            e.printStackTrace();
        }
        this.lf = lf;
    }

    @Override
    public void run() {
        this.resetGame();
        Sound bg = ResourceManager.getSound("bg"); // plays baxkground music
        bg.loopContinuously();
        bg.play();
        try {
            while (true) {
                this.tick++; // performance dependant
                for (int i = this.gObjs.size()-1; i >= 0; i--) {
                    if(this.gObjs.get(i) instanceof Updateable u) {
                        u.update(this);
                    }
                }

                for(int i = 0; i < Tank.getAnim().size(); i++){ // updates tank animations
                    Tank.getAnim().get(i).update();
                }

                this.checkCollisions();
                this.gObjs.removeIf(g -> g.getHasCollided()); // lambda expression to remove collided objects
                this.repaint();   // redraw game



                // win condition  check
                if (t1.getHealth() <= 0) {
                    System.out.println("Player 2 wins!");
                    winner = 2;
                    this.lf.updateEndGamePanel();
                    lf.setFrame("end");
                    break;
                } else if (t2.getHealth() <= 0) {
                    System.out.println("Player 1 wins!");
                    winner = 1;
                    this.lf.updateEndGamePanel();
                    lf.setFrame("end");
                    break;
                }

//                if (t1.getHealthBar().getCounter() == 3) {
//                    System.out.println("Player 2 wins!");
//                    winner = 2;
//                    lf.setFrame("end");
//                    break;
//                } else if (t2.getHealthBar().getCounter() == 3) {
//                    System.out.println("Player 1 wins!");
//                    winner = 1;
//                    lf.setFrame("end");
//                    break;
//                }

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
        for (int i = 0; i < this.gObjs.size(); i++) {
            GameObject obj1 = this.gObjs.get(i);
            for (int j = 0; j < this.gObjs.size(); j++) {
                if (i == j) continue; // so you dont collide with yourself (tank) constantly
                if (obj1 instanceof Tank) {
                    GameObject obj2 = this.gObjs.get(j);
                    if (obj1.getHitbox().intersects(obj2.getHitbox())) {
                        obj1.handleCollision(obj2); // handles object hit inside Tank
//                        System.out.println("Collision Detected");
                    }
                }
                if(obj1 instanceof Bullet) { // to stop bullets from going past walls
                    GameObject obj2 = this.gObjs.get(j);
                    if(obj1.getHitbox().intersects(obj2.getHitbox())) {
                        obj1.handleCollision(obj2);
                    }
                }

            }
        }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame() {
        this.gObjs.clear();
        this.tick = 0;

        this.t1.setX(300);
        this.t1.setY(300);
        this.t1.setHealth(100);
        this.t1.setSpeed(5);

        this.t2.setX(1500);
        this.t2.setY(300);
        this.t2.setHealth(100);
        this.t2.setSpeed(5);

        int row = 0;
        InputStreamReader isr = new InputStreamReader(
                Objects.requireNonNull(
                        ResourceManager.class.getClassLoader().getResourceAsStream("world.csv")
                )
        );

        try (BufferedReader mapReader = new BufferedReader(isr)) {
            while(mapReader.ready()) {
                String line = mapReader.readLine();
                String[] objs = line.split(",");
                for (int col = 0; col < objs.length; col++) {
                    String gameItem = objs[col];
                    if("0".equals(gameItem)) continue;
                    this.gObjs.add(GameObject.newInstance(gameItem, col*32, row*32));
                }
                row++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.gObjs.add(t1);
        this.gObjs.add(t2);
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

        t2 = new Tank(1500, 300, 0, 0,  (short) 0, ResourceManager.getSprite("t2"));
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        this.lf.getJf().addKeyListener(tc2);

        this.gObjs.add(t1);
        this.gObjs.add(t2);
        background = ResourceManager.getSprite("background");
    }

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

    private void displayHealthBar(Graphics2D onScreenPanel) {
        t1.getHealthBar().draw(onScreenPanel, (int)t1.getX(), (int)t1.getY() - 5);
        t2.getHealthBar().draw(onScreenPanel, (int)t2.getX(), (int)t2.getY() - 5);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // for bg image, load badkground before tanks
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        buffer.drawImage(background, 0, 0, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT, this);
        // renders game objects from csv
        this.gObjs.forEach(go -> go.drawImage(buffer)); // draws game objets from gObjs list to buffer

        for(int i = 0; i < Tank.getAnim().size(); i++){ // renders animations inside Tank anims
            Tank.getAnim().get(i).render(buffer);
        }

        // FYI: being drawn to JPanel meaning Split Screen MUST be before Minimap otherwise minimap is hidden below splitscreen stack
        this.displaySplitScreen(g2);
        this.displayHealthBar(g2);
        this.displayMiniMap(g2);
    }

    public void addGameObject(GameObject g) {
        this.gObjs.add(g);
    }


    public int getWinner() {
        return winner;
    }






}
