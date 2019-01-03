package ar.com.fran150.game;

import java.awt.*;
import java.util.LinkedList;

public class Handler {
    LinkedList<GameObject> objects;

    public Handler() {
        objects = new LinkedList<GameObject>();
    }

    public void tick() {
        for (GameObject object : objects) {
            object.tick();
        }
    }

    public void render(Graphics g) {
        for (GameObject object : objects) {
            object.render(g);
        }
    }

    public void addObject(GameObject object) {
        objects.add(object);
    }

    public void removeObject(GameObject object) {
        objects.remove(object);
    }
}
