/*
  Class Controller
  @author Ladislav Dokoupil (xdokou14)
  @author Adrián Bobola (xbobol00)
 */

package ija21;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.*;
import java.lang.Object;

public class Controller {

    Pane root;
    JsonParser jsonParser;
    Map<String, Integer> requirements;
    int maxX, maxY;
    int scale;
    FindPath findPath;
    Timer timer;
    TimerTask mainLoop;

    public Controller(Pane root, JsonParser jsonParser, Map<String, Integer> requirements,int scale) {
        this.root = root;
        this.jsonParser = jsonParser;
        this.requirements = requirements;
        this.maxX = jsonParser.getMaxX();
        this.maxY = jsonParser.getMaxY();
        this.scale = scale;
        //initalize pathfinding structure
        findPath = new FindPath(maxX, maxY, scale);
        timer = new Timer(false);
        mainLoop = new MainLoop(jsonParser,requirements,findPath,scale);
    }

    public void start(int mapSpeed) {
        System.out.println(mapSpeed);
        //remove old objects
        root.getChildren().removeAll(jsonParser.getAllShelfs().values());
        root.getChildren().removeAll(jsonParser.getTrolleys());

        //add shelfs/trolleys from file
        root.getChildren().addAll(jsonParser.getAllShelfs().values());
        root.getChildren().addAll(jsonParser.getTrolleys());

        //update every time shelfs are changed
        findPath.updatePaths(jsonParser.getAllShelfs());

        timer.scheduleAtFixedRate(mainLoop, 0, 10000 / mapSpeed);
    }

    public void stop() {
        timer.cancel();
        /*timer.cancel();
        System.out.println("STOP --pepe");
        try {
            wait(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.scheduleAtFixedRate(mainLoop,0,1000 / mapSpeed);*/
        //play();
    }
    public void play(int mapSpeed) {
        findPath.updatePaths(jsonParser.getAllShelfs());
        mainLoop = new MainLoop(jsonParser,requirements,findPath,scale);
        timer = new Timer(false);
        timer.scheduleAtFixedRate(mainLoop, 0, 10000 / mapSpeed);
    }

}
