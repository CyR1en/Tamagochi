package com.cyr1en.cgdl.Entity;

import com.cyr1en.cgdl.Handlers.ImageLoader;
import com.cyr1en.cgdl.Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The background for the game
 */
public class BackGround {

    //the background image
    private BufferedImage image;

    //Coordinate
    private double x;
    private double y;

    //vector
    private double dx;
    private double dy;

    //very straight forward constructor
    public BackGround() {
        //used the ImageLoader class from the CGDL(thanks to ethan)
        image = ImageLoader.load("/assets/bg2.png");
        //initialize the coordinate of the BG to (0,0)
        x = 0;
        y = 0;
    }

    //sets the vector
    public void setVector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    //updates the background
    public void update() {
        x += dx;
        y += dy;
        if (x + image.getWidth() <= 0 || x >= GamePanel.WIDTH)
            x = 0;
        if (y + image.getWidth() <= 0 || y >= GamePanel.HEIGHT)
            y = 0;
    }

    //draws the background
    public void draw(Graphics2D g) {
        //center
        g.drawImage(image, (int) x, (int) y, null);
        //right
        g.drawImage(image, (int) x + image.getWidth(), (int) y, null);
        //left
        g.drawImage(image, (int) x - image.getWidth(), (int) y, null);
        //top
        g.drawImage(image, (int) x, (int) y - image.getHeight(), null);
        //bottom
        g.drawImage(image, (int) x, (int) y + image.getHeight(), null);
        //top-left
        g.drawImage(image, (int) x - image.getWidth(), (int) y - image.getHeight(), null);
        //top-right
        g.drawImage(image, (int) x + image.getWidth(), (int) y - image.getHeight(), null);
        //bottom-left
        g.drawImage(image, (int) x - image.getWidth(), (int) y + image.getHeight(), null);
        //bottom-right
        g.drawImage(image, (int) x + image.getWidth(), (int) y + image.getHeight(), null);
    }


}
