package AirScape.Entity;

import com.cyr1en.cgdl.Entity.GameObject;
import com.cyr1en.cgdl.Main.GamePanel;

import java.awt.*;

/**
 * A title object that we called on the Menu state
 */
public class Title extends GameObject {

    //String for the title
    private String title;

    //title constructor
    public Title(String title) {
        this.title = title;
        y = -100;
    }

    //update the title variables
    public void update() {
        dy += .08;
        y += dy;
        if( y > (GamePanel.HEIGHT * 0.2))
            y = (GamePanel.HEIGHT * 0.2);
    }

    //draw the title
    public void draw(Graphics2D g) {
        g.setFont(new Font("Fira Code", Font.BOLD, 60));
        int length = (int) g.getFontMetrics().getStringBounds(title, g).getWidth();
        g.drawString(title ,GamePanel.WIDTH / 2 - (length / 2), (int)y);
    }
}
