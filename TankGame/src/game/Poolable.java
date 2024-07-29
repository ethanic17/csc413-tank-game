package TankGame.src.game;

import java.awt.image.BufferedImage;

public interface Poolable {
    void initObject(float x, float y);
    void initObject(float x, float y, float angle);
    void resetObject();
}
