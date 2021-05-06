/*
  Class Controller
  @author Ladislav Dokoupil (xdokou14)
  @author Adrián Bobola (xbobol00)
 */

package ija21;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.application.Application;

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

    public void resetObjects(){
        //remove old objects
        root.getChildren().removeAll(jsonParser.getAllShelfs().values());
        root.getChildren().removeAll(jsonParser.getTrolleys());
        root.getChildren().removeAll(jsonParser.getEdges());

        //add staging areas for esthetic reasons
        root.getChildren().addAll(jsonParser.getStages());
        //add paths
        root.getChildren().addAll(getPaths(jsonParser.getAllShelfs()));
        //add shelfs/obstacles/trolleys from file
        root.getChildren().addAll(jsonParser.getAllShelfs().values());
        root.getChildren().addAll(jsonParser.getTrolleys());

        //put rectange into edge of map
        root.getChildren().addAll(jsonParser.getEdges());


        //update every time shelfs are changed
        findPath.updatePaths(jsonParser.getAllShelfs());
    }

    public void start(int mapSpeed) {
        //set global scene variable so Shelfs/Trolleys can add their elements
        GlobalScene.pane = root;
        resetObjects();

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


    public List<PathObject> getPaths(Map<Coordinates, Shelf> shelfs)
    {
        List<PathObject> paths = new ArrayList<>();
        //make new paths (complement of shelfs with respect to 0-maxX and 0-maxY)
        paths.clear();
        for (int x = 0; x <= jsonParser.getMaxX(); x += blockWidth.SCALE) {
            for (int y = 0; y <= jsonParser.getMaxY(); y += blockWidth.SCALE) {
                Coordinates position = new Coordinates(x, y);
                //is shelf
                if (shelfs.containsKey(position))
                    continue;
                PathObject n = new PathObject(position,this);
                paths.add(n);
            }
        }
        return paths;
    }
}
