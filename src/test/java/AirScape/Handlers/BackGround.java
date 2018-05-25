package AirScape.Handlers;

import com.cyrus.CGDL.Handlers.ImageLoader;
import com.cyrus.CGDL.Main.GamePanel;

import java.awt.Graphics2D;
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
        image = ImageLoader.load("/bg.jpg");
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
        if (x + 900 <= 0)
            x = 0;
    }

    //draws the background
    public void draw(Graphics2D g) {

        g.drawImage(image, (int) x, (int) y, null);
        g.drawImage(image, (int) x + 900 , (int) y, null);
    }

}
