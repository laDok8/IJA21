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
        mainLoop = new MainLoop(jsonParser,requirements,findPath);
    }

    public void start(int mapSpeed) {
        //remove old objects
        root.getChildren().removeAll(jsonParser.getAllShelfs().values());
        root.getChildren().removeAll(jsonParser.getTrolleys());
        root.getChildren().removeAll(jsonParser.getObstacle());


        //add shelfs/obstacles/trolleys from file
        root.getChildren().addAll(jsonParser.getAllShelfs().values());
        root.getChildren().addAll(jsonParser.getTrolleys());
        root.getChildren().addAll(jsonParser.getObstacle());

        //add staging areas for esthetic reasons
        root.getChildren().addAll(jsonParser.getStages());

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
        mainLoop = new MainLoop(jsonParser,requirements,findPath);
        timer = new Timer(false);
        timer.scheduleAtFixedRate(mainLoop, 0, 10000 / mapSpeed);
    }

}
