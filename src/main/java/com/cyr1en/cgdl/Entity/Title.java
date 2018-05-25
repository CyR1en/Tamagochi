package com.cyr1en.cgdl.Entity;

import com.cyr1en.cgdl.Main.GamePanel;

import java.awt.*;

/**
 * A title object that we called on the Menu state
 */
public class Title extends GameObject {

    public double targetY;

    //String for the title
    private String title;
    private Color color;

    //title constructor
    public Title(String title) {
        this.title = title;
        y = -100;
        dy = 5;
        targetY = GamePanel.HEIGHT * 0.23;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    //update the title variables
    public void update() {
        lastY = y;
        dy = (targetY - y) / 20;
        if(dy < 0.001)
            dy = 0;
        y += dy;
    }

    //draw the title
    public void draw(Graphics2D g, float interpolation) {
        int iY = (int) ((y - lastY) * interpolation + lastY - height / 2);
        g.setColor(color);
        g.setFont(new Font(null, Font.BOLD, 60));
        int length = (int) g.getFontMetrics().getStringBounds(title, g).getWidth();
        g.drawString(title, GamePanel.WIDTH / 2 - (length / 2), iY);
    }
}
