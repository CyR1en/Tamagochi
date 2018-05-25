package com.cyr1en.cgdl.Entity;

import com.cyr1en.cgdl.Main.GamePanel;

import java.awt.*;

/**
 * A title object that we called on the Menu state
 */
public class Title extends GameObject {

    //String for the title
    private String title;
    private Color color;

    //title constructor
    public Title(String title) {
        this.title = title;
        y = -100;
    }

    public void setColor(Color color) {
        this.color = color;
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
        g.setColor(color);
        g.setFont(new Font(null, Font.BOLD, 60));
        int length = (int) g.getFontMetrics().getStringBounds(title, g).getWidth();
        g.drawString(title ,GamePanel.WIDTH / 2 - (length / 2), (int)y);
    }
}
