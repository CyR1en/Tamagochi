package AirScape.Handlers;

import com.APCSFinal.AirScape.Entity.Planes;
import com.APCSFinal.AirScape.Entity.Trees;
import com.cyrus.CGDL.Main.GamePanel;

import java.util.ArrayList;

/**
 * this class handles the spawning of the trees and the planes. AKA column
 */
public class ColumnSpawner {

    private static ArrayList<Trees> trees;
    private static ArrayList<Planes> planes;

    //timer for the spawning
    private static long startTime;

    //spawn interval
    private static int spawningInterval;

    // initialize the trees and the planes ArrayList from the instance variables
    public static void init(ArrayList<Trees> trees, ArrayList<Planes> planes, int spawningInterval) {
        ColumnSpawner.trees = trees;
        ColumnSpawner.planes = planes;
        startTime = System.nanoTime();
        ColumnSpawner.spawningInterval = spawningInterval;
    }

    //spawns trees and planes, depends on the spawn interval
    public static void spawnColumn() {
        //checks the elapsed time since the initialization of this class
        long elapsed = (System.nanoTime() - startTime) / 1000000;

        // height of spawning tree
        int height = (int) (Math.random() * ((GamePanel.HEIGHT * 0.7) - 200) + 200);

        /**
         * if the ArrayList is null then that means we just spawn a new column
         * and ignore the spawning interval
         */
        if (trees == null && planes == null) {
            trees.add(new Trees(height));
            planes.add(new Planes(GamePanel.HEIGHT - (height + 200)));
        }

        /**
         * if the the elapsed time became greater that or equal to the spawn interval
         *that means it should spawn a new column
         */
        if (elapsed >= spawningInterval) {
            trees.add(new Trees(height));
            planes.add(new Planes(GamePanel.HEIGHT - (height + 200)));
            startTime = System.nanoTime();
        }
    }
}
