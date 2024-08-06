package TankGame.src.game;

import java.awt.*;

public class HealthBar {
    private Tank tank;
    private int maxHealth;
    private int width;
    private int height;
    private int counter = 0;

    public HealthBar(Tank tank, int maxHealth, int width, int height) {
        this.tank = tank;
        this.maxHealth = maxHealth;
        this.width = width;
        this.height = height;
    }

    public int getCounter() {
        return counter;
    }

    public void draw(Graphics2D g, int x, int y) {
        int currentHealth = tank.getHealth();
        float healthPercent = (float) currentHealth / maxHealth;
        int healthWidth = (int) (width * healthPercent);

        g.setColor(Color.GRAY);
        g.fillRect(x, y, width, height);

        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

//        g.setColor(Color.RED);
//        g.fillRect(x, y, healthWidth, height);

        if(currentHealth == 125) { // shield power up
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x, y, healthWidth, height);
        } else if(currentHealth >= 101) { // health power up
            g.setColor(Color.CYAN);
            g.fillRect(x, y, healthWidth, height);
        } else if (currentHealth >= 80) {
            g.setColor(Color.GREEN);
            g.fillRect(x, y, healthWidth, height);
        } else if (currentHealth >= 30) {
            g.setColor(Color.YELLOW);
            g.fillRect(x, y, healthWidth, height);
        } else if (currentHealth < 29) {
            g.setColor(Color.RED);
            g.fillRect(x, y, healthWidth, height);
        }  else if (currentHealth <= 0) {
            if (counter != 3) {
                counter++;
                tank.setHealth(100);
            } else {
                // switches to endGame panel from getCounter in gamewotld
            }

        }


    }
}
