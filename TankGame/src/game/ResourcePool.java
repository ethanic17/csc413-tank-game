package TankGame.src.game;

import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
//import java.util.concurrent.ConcurrentLinkedDeque;

public class ResourcePool<G extends Poolable> {
    private final static int INIT_CAPCAITY = 100;
    private final String resourceName;
    private final Class<G> resourceClass;
    private final ArrayList<G> resources;

    public ResourcePool(String resourceName, Class<G> resourceClass) {
        this.resourceName = resourceName;
        this.resourceClass = resourceClass;
        this.resources = new ArrayList<>(INIT_CAPCAITY);
    }

    public ResourcePool(String resourceName, Class<G> resourceClass, int initialCapacity) {
        this.resourceName = resourceName;
        this.resourceClass = resourceClass;
        this.resources = new ArrayList<>(initialCapacity);
    }

    public G removeFromPool() {
        if(!this.resources.isEmpty()) {
            this.refillPool();
        }
        return this.resources.removeLast(); //LIFO queue, o(1) TC
    }

    public void addToPool(G obj) {
        this.resources.addLast(obj);
    }

    private void refillPool() {
        this.fillPool(INIT_CAPCAITY);
    }

    public ResourcePool<G> fillPool(int size) {
        BufferedImage img = ResourceManager.getSprite(this.resourceName);
        for(int i = 0; i < size; i++){
            try {
                var g = this.resourceClass.getDeclaredConstructor(BufferedImage.class).newInstance(img);
                this.resources.add(g);
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return this;
    }
}
