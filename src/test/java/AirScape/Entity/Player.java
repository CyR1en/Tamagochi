package AirScape.Entity;


import com.cyr1en.cgdl.BasicGraphics.Draw;
import com.cyr1en.cgdl.Entity.GameObject;
import com.cyr1en.cgdl.Entity.Particle;
import com.cyr1en.cgdl.Handlers.ImageLoader;
import com.cyr1en.cgdl.Handlers.ParticleFactory;
import com.cyr1en.cgdl.Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The player class
 */
public class Player extends GameObject {

    // constant for Y vector (going down)
    private static double GRAVITY = .4;

    //variable for all particles that the player generate
    private ArrayList<Particle> particles;

    //player logic
    private boolean up;
    private boolean crashed;
    private boolean outOfBounds;
    private boolean initial;

    //player image
    private BufferedImage image;

    //player score
    private int score;

    //player constructor
    public Player() {
        this.setVector(0, 0); // initial vector of the player
        width = 80; // initial width of the player
        height = 30; // initial height of the player
        x = 200; // initial X coordinate of the player
        y = GamePanel.HEIGHT / 2 - width; // initial Y coordinate of the player
        image = ImageLoader.load("/plane.png"); // initialize the image for the player sprite
        score = 0; // initial player score
        crashed = false; // initial crash logic
        particles = new ArrayList<>(); // initialized the Array List of particles
        initial = true; // initial Player state
        ParticleFactory.init(particles); //initialize particle factory
    }

    // update the "up" logic to become the parameter
    public void setUp(boolean b) {
        up = b;
    }

    // sets if it's the player state is initial, or first spawn
    public void setInitial(boolean b) {
        initial = b;
    }

    // increment score
    public void addScore() {
        score++;
    }

    // get the score
    public int getScore() {
        return score;
    }

    // edit the value of logic "crashed"
    public void Crashed(boolean b) {
        crashed = b;
    }

    // return the crashed logic
    public boolean isCrashed() {
        return crashed;
    }

    // return if the player is out of the screen
    public boolean isOutOfBounds() {
        return outOfBounds;
    }

    // updates of the player
    public void update() {

        /**
         * if the logic "up" is true then decrement the Y value (the y is reversed)
         * to the Y vector that also increments. TL;DR, there's acceleration for the Y
         * vector
         */
        if (up) {
            dy += -GRAVITY; // incremental Y vector
            if (dy < -10) // minimum of the y coordinate
                dy = -10;
        } else if (!initial) {
            dy += GRAVITY; // incremental y vector
            if (dy > 11) // the maximum player y coordinate
                dy = 11;
        }

        //update y coordinate with the current vector
        y += dy;

        //if player hit the ground -> you crashed crashed
        if (y + height > GamePanel.HEIGHT) {
            crashed = true;
            y = GamePanel.HEIGHT - height;
        }

        //if player gets out of the screen -> you die
        if (y + (height + 15) < 0) {
            outOfBounds = true;
            y = -(height + 15);
        }

        //only draw particle effects of the player when the player is not
        //ont the initial state
        if(!initial) {
            ParticleFactory.createSmoke(x + 5, y + 15, new Color(150, 150, 150, 100));
            particles.forEach(Particle::update);

        }
    }

    //draw the Player and all the particle effects
    public void draw(Graphics2D g) {
        Draw.init(g);
        for (Particle p : particles)
            p.draw(g);
        g.drawImage(image, (int) x, (int) y - 11, null);
    }
}
