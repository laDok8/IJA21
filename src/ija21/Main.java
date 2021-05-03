/*
  @author Ladislav Dokoupil (xdokou14)
  @author Adrián Bobola (xbobol00)
 */

package ija21;

import java.util.*;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import java.io.File;

/*
public class putItems{
    public float x;
    public float y;
    public int id;

    public putItems(int idIn, float xIn, float yIn){
        this.id = idIn;
        this.x = xIn;
        this.y = yIn;
    }

    void putTrolleyToMap() {
        Rectangle trolleyRectangle = new Rectangle();
        trolleyRectangle.setX(this.x*10);
        trolleyRectangle.setY(this.y*10);
        trolleyRectangle.setWidth(10.0f);
        trolleyRectangle.setHeight(10.0f);
        trolleyRectangle.setFill(Color.DARKBLUE);
        root.getChildren().add(trolleyRectangle);
    }

    void putShelvesToMap() {
        Rectangle shelfRectangle = new Rectangle();
        shelfRectangle.setX(this.x*10);
        shelfRectangle.setY(this.y*10);
        shelfRectangle.setWidth(10.0f);
        shelfRectangle.setHeight(10.0f);
        shelfRectangle.setFill(Color.YELLOW);
        root.getChildren().add(shelfRectangle);
    }
}

*/


public class Main extends Application {

    Pane root;
    Scene scene;
    Controller controller;
    Map<Coordinates, Shelf> shelfs;

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();
        primaryStage.setTitle("Hello World!");
        scene = new Scene(root,300,300);
        primaryStage.setScene(scene);
        primaryStage.show();


        //load shelfs
        JsonParser jsonParser = null;
        try {
            jsonParser = new JsonParser("data/sample.json");
        } catch (Exception e) {
            System.err.println("chyba parser: sample.json");
            System.exit(1);
        }
        final int maxX = jsonParser.getMaxX();
        final int maxY = jsonParser.getMaxY();


        RequirementsParser reqParser = requirementsProcessing();
        controller = new Controller(root,jsonParser,reqParser.getRequirements());
        controller.start();

        //root.getChildren().add()

    }

    public static RequirementsParser requirementsProcessing(){
        // ------------------------ REQUIREMENTS PARSER --------------------------
        RequirementsParser reqParser = null;
        String filename = "data/requirements.json";
        try{
            reqParser = new RequirementsParser(filename);
            }
        catch (Exception e) {
            System.err.println("Chyba zadanych poziadaviek");
            System.exit(2);
        }

        System.out.println("-------------------- NACITANE POZIADAVKY -----------------------");
        reqParser.getRequirements().forEach(
                (name, count) -> System.out.println("Pozadovane zbozi: " + name + ", " + count + " ks"));
        System.out.println("----------------------------------------------------------------");
        // ------------------------ END REQUIREMENTS PARSER --------------------------
        return reqParser;
    }
    /*public static void putTrolleyToMap(float x, float y) {
        Rectangle trolleyRectangle = new Rectangle();
        trolleyRectangle.setX(x*10);
        trolleyRectangle.setY(y*10);
        trolleyRectangle.setWidth(10.0f);
        trolleyRectangle.setHeight(10.0f);
        trolleyRectangle.setFill(Color.DARKBLUE);
        root.getChildren().add(trolleyRectangle);
    }

    public static void putShelvesToMap(float x, float y) {
        Rectangle shelfRectangle = new Rectangle();
        shelfRectangle.setX(x*10);
        shelfRectangle.setY(y*10);
        shelfRectangle.setWidth(10.0f);
        shelfRectangle.setHeight(10.0f);
        shelfRectangle.setFill(Color.YELLOW);
        root.getChildren().add(shelfRectangle);
    }*/
/*

    /*
     Print map into output

    public static void printMap(String[][] map) {
        for (String[] strings : map) {
            for (String string : strings) {
                System.out.print(string);
            }
            System.out.println();
        }
    }
   */


    public static void main(String[] args) {


        //load shelfs
        /*JsonParser jsonParser = null;
        try {
            jsonParser = new JsonParser("data/sample.json");
        } catch (Exception e) {
            System.err.println("chyba parser: sample.json");
            System.exit(1);
        }
        final int maxX = jsonParser.getMaxX();
        final int maxY = jsonParser.getMaxY();

        //print all shelfs into output
        System.out.println("-------------------- REGAL NA SKLADE -----------------------");
        jsonParser.getAllShelfs().forEach((cordinates, tmp) ->
                System.out.println("Regal [" + cordinates.getX() + ":" + cordinates.getY() + "], zbozi:" + tmp.getStored()));
        System.out.println("-------------------- POZIADAVKY -----------------------");
        //print required items into output
        reqParser.getRequirements().forEach(
                (name, count) -> System.out.println("Pozadovane zbozi: " + name + ", " + count + " ks"));
        System.out.println("\n**************************************************************************");
        System.out.println("*************************  MAPA ******************************************");
        System.out.println("**************************************************************************\n");

        //initalize pathifinding structure
        FindPath findPath = new FindPath(maxX, maxY);
        //update every time shelfs are changed
        findPath.updatePaths(jsonParser.getAllShelfs());

        //create empty map
        String[][] map = new String[maxX][maxY];
        for (String[] row : map) {
            Arrays.fill(row, " ");
        }

        //put all shelf into map
        //putItems object = new putItems(1, 5, 5);

        //jsonParser.getAllShelfs().forEach((cordinates, tmp) -> putShelvesToMap(cordinates.x, cordinates.y));

        boolean running = true;
        while (running) {
            //set refreshing speed
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //iterate trolleys
            for (Trolley trolley : jsonParser.getTrolleys()) {

                //set task to empty trolleys
                if (trolley.task == null && reqParser.getRequirements().size() > 0) {
                    Map.Entry<String, Integer> tmp;
                    tmp = reqParser.getRequirements().entrySet().iterator().next();
                    trolley.task = new AbstractMap.SimpleEntry<>(tmp.getKey(), tmp.getValue());
                    reqParser.getRequirements().remove(tmp.getKey());
                }
                //ending condition
                if (reqParser.getRequirements().size() == 0 && jsonParser.getTrolleys().stream().allMatch(trolley1 -> trolley1.task == null)) {
                    running = false;
                    break;

                }

                //sort shelfs by distance
                TreeMap<Double, Coordinates> sortedByDistances = new TreeMap<>();
                Map<Coordinates, Shelf> found = jsonParser.findGoods(trolley.task.getKey());

                for (Map.Entry<Coordinates, Shelf> entry : found.entrySet()) {
                    double distance = entry.getKey().getDistance(trolley);
                    sortedByDistances.put(distance, entry.getKey());
                }

                System.out.println("Pozadovane zbozi: " + trolley.task + " ks");
                //compute path to shortest (if exists)
                if (sortedByDistances.size() == 0) {
                    System.out.println("!!!!!!! ERROR: POZADOVANE MNOZSTVI NENI SKLADEM !!!!!!!!!");
                    System.out.println("-------------------- DALSIA POZIADAVKA --------------------------");
                    trolley.task = null;
                    continue;
                }
                //min distance Cordinates value
                Coordinates lowestDisanceCord = sortedByDistances.firstEntry().getValue();
                Coordinates curentCoordinates = new Coordinates((int)trolley.getX(),(int)trolley.getY());
                PathNode vysl2 = findPath.aStar(curentCoordinates, lowestDisanceCord);

                //no path to this required item
                if (vysl2 == null) {
                    System.out.println("ERROR - Vozik se nemuze dostat k regalu s pozadovanym zbozim!");
                    System.out.println("-------------------- DALSIA POZIADAVKA --------------------------");
                    trolley.task = null;
                    continue;
                }
                //vysl2.parent != null &&
                PathNode tmpCord;
                tmpCord = vysl2;
                while (!tmpCord.getSelf().equals(curentCoordinates)) {
                    vysl2 = tmpCord;
                    tmpCord = vysl2.getParent();
                }

                //add new trolley coordinates into map
                map[(int)trolley.getY()][(int)trolley.getX()] = "T";
                //printMap(map);
                //putTrolleyToMap((float)trolley.x, (float)trolley.y);
                //delete trolley previous coordinates
                map[(int)trolley.getY()][(int)trolley.getX()] = " ";
                System.out.println("ZMENA POZICE VOZIKU: [" + trolley.getX() + ":" + trolley.getY() + "]");
                System.out.println("------------- MAP UPDATE ------------------");
                if (curentCoordinates.getDistance(lowestDisanceCord) != 1) {
                    trolley.setX(vysl2.getSelf().getX());
                    trolley.setY(vysl2.getSelf().getY());
                } else {
                    //Success - remove items from shelf
                    Shelf itemShelf = jsonParser.getAllShelfs().get(lowestDisanceCord);
                    int removedItem = itemShelf.delete_item(trolley.task.getKey(), trolley.task.getValue());
                    trolley.task.setValue(trolley.task.getValue() - removedItem);
                    trolley.add_item(trolley.task.getKey(), removedItem);
                    //Deleted OK - set task = nul;
                    if (trolley.task.getValue() == 0)
                        trolley.task = null;

                    System.out.println("-------------------- DALSIA POZIADAVKA --------------------------");

                    //Set map to default position - loading next require item
                    for (String[] row : map) {
                        Arrays.fill(row, " ");
                    }

                    //put all shelf into map
                    //jsonParser.getAllShelfs().forEach((cordinates, tmp) -> putShelvesToMap(cordinates.x, cordinates.y));
                }
            }
        }
        System.out.println("VSECHNY POZADAVKY USPESNE ZPRACOVANY.");
        int ntrolley = 1;
        for (Trolley trolley : jsonParser.getTrolleys()) {
            System.out.println("\n---POLOZKY VE VOZIKU " + ntrolley + "---");
            trolley.getStored().forEach((name, count) -> System.out.println(name + "-> " + count));
            ntrolley++;
        }*/

        launch(args);
    }
}