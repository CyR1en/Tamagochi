package AirScape.Entity;


import com.cyr1en.cgdl.Entity.GameObject;
import com.cyr1en.cgdl.Handlers.ImageLoader;
import com.cyr1en.cgdl.Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * plane obstacles that spawns with the trees
 */
public class Planes extends GameObject {

    // Obstacle plan sprite
    private BufferedImage image;

    /**
     * Plane constructor
     * @param ySpawn the spawn Y coordinate of the plane
     */
    public Planes(int ySpawn) {
        width = 80; // width of the plane
        height = 30; // height of the plane
        this.setVector(-5, 0); // vector of the plane
        this.setPosition(GamePanel.WIDTH + 100, ySpawn); // initial position of the plane
        image = ImageLoader.load("/EnemyPlane.png"); // initialize the image sprite
    }

    //update the plane. The plane moves to the left
    public boolean update() {
        x += dx;
        return x + width < 0;
    }

    // if this plane collides with the object in the parameter
    public boolean collided(GameObject object) {
        return this.intersects(object);
    }

    //draw the plane
    public void draw(Graphics2D g) {
        g.drawImage(image, (int) x, (int) y - 11, null);
    }
}
