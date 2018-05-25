package AirScape.Main;

import AirScape.Handlers.StateManager;
import com.cyr1en.cgdl.Handlers.ImageLoader;
import com.cyr1en.cgdl.Main.GameFrame;
import com.cyr1en.cgdl.Main.GamePanel;

import java.awt.image.BufferedImage;

public class Main {

    //Loading the icon for the frame
    private static BufferedImage frameIcon = ImageLoader.load("/FrameIcon2.png");

    //The frame for the game
    public static GameFrame frame;

    public static void main(String[] args) {
        GamePanel.initPanel(900 , 600); // initializing the size of the GamePanel
        frame = new GameFrame("AirScape", new StateManager(), 90); // initializing the game frame
        frame.setShowTitleInfo(false); // enable the title bar debug
        frame.setIconImage(frameIcon); // set the frame icon image as the buffered image in the instance variable
    }
}
