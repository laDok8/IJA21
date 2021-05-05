/*
  @author Ladislav Dokoupil (xdokou14)
  @author Adrián Bobola (xbobol00)
 */

package ija21;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;

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
    int mapSpeed = 500;
    double mapZOOM = 1.0;
    boolean pausedScene = false;
    boolean scenePlaying = false;
    Pane root;
    Scene scene;
    Controller controller;

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();
        primaryStage.setTitle("IJA 2020/21 - Projekt");

        //load shelfs
        JsonParser jsonParser = null;
        try {
            jsonParser = new JsonParser("data/sample.json", blockWidth.SCALE);
        } catch (Exception e) {
            System.err.println("chyba parser: sample.json");
            System.exit(1);
        }
        final int maxX = jsonParser.getMaxX();
        final int maxY = jsonParser.getMaxY();

        RequirementsParser reqParser = requirementsProcessing();
        controller = new Controller(root,jsonParser,reqParser.getRequirements(), blockWidth.SCALE);

        //windows params
        int windowWidth = 768;
        int windowLength = 1366;

        addButtons(windowWidth - 40, windowLength, primaryStage);
        scene = new Scene(root,windowLength,windowWidth);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Scene window is closed
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                primaryStage.close();
                System.out.println("Graficke okno aplikacie zatvorene uzivatelom");
                System.exit(0);
            }
        });
    }

    public static RequirementsParser requirementsProcessing(){
        // ------------------------ REQUIREMENTS PARSER --------------------------
        RequirementsParser reqParser = null;
        try{
            reqParser = new RequirementsParser("data/requirements.json");
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

    public void addButtons(int ySur, int xSur, Stage primaryStage){
        //show actual MapSpeed
        Button buttonDisplaySpeed = new Button(Integer.toString(mapSpeed));
        buttonDisplaySpeed.setStyle("-fx-border-color: #000200; -fx-border-width: 1px;");
        buttonDisplaySpeed.setLayoutX(60);
        buttonDisplaySpeed.setLayoutY(ySur);
        root.getChildren().add(buttonDisplaySpeed);
        buttonDisplaySpeed.setOnAction(event1 -> {
            mapSpeed = 500;
            buttonDisplaySpeed.setText(Integer.toString(mapSpeed));
        });

        //Speed++ button
        Button buttonSpeedInc = new Button("+ Speed");
        buttonSpeedInc.setStyle("-fx-border-color: #8DC53F; -fx-border-width: 1px;");
        buttonSpeedInc.setLayoutX(105);
        buttonSpeedInc.setLayoutY(ySur);
        buttonSpeedInc.setOnAction(event1 -> {
            if (mapSpeed == 1) {
                mapSpeed = 5;
                buttonDisplaySpeed.setText(Integer.toString(mapSpeed));
            } else if (mapSpeed == 5) {
                mapSpeed = 10;
                buttonDisplaySpeed.setText(Integer.toString(mapSpeed));
            } else if (mapSpeed == 10) {
                mapSpeed = 25;
                buttonDisplaySpeed.setText(Integer.toString(mapSpeed));
            } else if (mapSpeed == 25) {
                mapSpeed = 50;
                buttonDisplaySpeed.setText(Integer.toString(mapSpeed));
            } else if (mapSpeed == 50) {
                mapSpeed = 100;
                buttonDisplaySpeed.setText(Integer.toString(mapSpeed));
            } else if (mapSpeed == 100) {
                mapSpeed = 250;
                buttonDisplaySpeed.setText(Integer.toString(mapSpeed));
            } else if (mapSpeed == 250) {
                mapSpeed = 500;
                buttonDisplaySpeed.setText(Integer.toString(mapSpeed));
            } else if (mapSpeed == 500) {
                mapSpeed = 750;
                buttonDisplaySpeed.setText(Integer.toString(mapSpeed));
            } else if (mapSpeed == 750) {
                mapSpeed = 1000;
                buttonDisplaySpeed.setText(Integer.toString(mapSpeed));
            }
        });
        root.getChildren().add(buttonSpeedInc);

        //Speed-- button
        Button buttonSpeedDec = new Button("- Speed");
        buttonSpeedDec.setStyle("-fx-border-color: #FE2D2D; -fx-border-width: 1px;");
        buttonSpeedDec.setLayoutX(0);
        buttonSpeedDec.setLayoutY(ySur);
        buttonSpeedDec.setOnAction(event1 -> {
            if (mapSpeed == 1000) {
                mapSpeed = 750;
                buttonDisplaySpeed.setText(Integer.toString(mapSpeed));
            } else if (mapSpeed == 750) {
                mapSpeed = 500;
                buttonDisplaySpeed.setText(Integer.toString(mapSpeed));
            } else if (mapSpeed == 500) {
                mapSpeed = 250;
                buttonDisplaySpeed.setText(Integer.toString(mapSpeed));
            } else if (mapSpeed == 250) {
                mapSpeed = 100;
                buttonDisplaySpeed.setText(Integer.toString(mapSpeed));
            } else if (mapSpeed == 100) {
                mapSpeed = 50;
                buttonDisplaySpeed.setText(Integer.toString(mapSpeed));
            } else if (mapSpeed == 50) {
                mapSpeed = 25;
                buttonDisplaySpeed.setText(Integer.toString(mapSpeed));
            } else if (mapSpeed == 25) {
                mapSpeed = 10;
                buttonDisplaySpeed.setText(Integer.toString(mapSpeed));
            } else if (mapSpeed == 10) {
                mapSpeed = 5;
                buttonDisplaySpeed.setText(Integer.toString(mapSpeed));
            } else if (mapSpeed == 5) {
                mapSpeed = 1;
                buttonDisplaySpeed.setText(Integer.toString(mapSpeed));
            }
        });
        root.getChildren().add(buttonSpeedDec);

        //PAUSE button
        Button buttonPause = new Button("PAUSE");
        buttonPause.setStyle("-fx-border-color: #1E5D1F; -fx-border-width: 5px;");
        buttonPause.setLayoutX(260);
        buttonPause.setLayoutY(ySur);
        buttonPause.setOnAction(event1 -> {
            if (!pausedScene && scenePlaying) {
                controller.stop();
                pausedScene = true;
                System.out.println("PAUSE");
                buttonPause.setText("RESUME");
            } else if (pausedScene){
                System.out.println("STOP PAUSE");
                buttonPause.setText("PAUSE");
                controller.play(mapSpeed);
                pausedScene = false;
            }
        });

        //show actual ZOOM
        Button buttonDisplayZOOM = new Button(Double.toString(mapZOOM));
        buttonDisplayZOOM.setStyle("-fx-border-color: #000200; -fx-border-width: 1px;");
        buttonDisplayZOOM.setLayoutX(xSur - 110);
        buttonDisplayZOOM.setLayoutY(ySur);
        root.getChildren().add(buttonDisplayZOOM);
        buttonDisplayZOOM.setOnAction(event1 -> {
            mapZOOM = 1.0;
            buttonDisplayZOOM.setText(Double.toString(mapZOOM));
        });

        //ZOOM+ button
        Button buttonZoomInc = new Button("+ZOOM");
        buttonZoomInc.setStyle("-fx-border-color: #0037FE; -fx-border-width: 1px;");
        buttonZoomInc.setLayoutX(xSur - 70);
        buttonZoomInc.setLayoutY(ySur);
        buttonZoomInc.setOnAction(event1 -> {
            if (mapZOOM == 0.25) {
                mapZOOM = 0.50;
                blockWidth.setZOOM(mapZOOM);
                buttonDisplayZOOM.setText(Double.toString(mapZOOM));
            } else if (mapZOOM == 0.50) {
                mapZOOM = 1.0;
                blockWidth.setZOOM(mapZOOM);
                buttonDisplayZOOM.setText(Double.toString(mapZOOM));
            } else if (mapZOOM == 1.0) {
                mapZOOM = 1.5;
                blockWidth.setZOOM(mapZOOM);
                buttonDisplayZOOM.setText(Double.toString(mapZOOM));
            } else if (mapZOOM == 1.5) {
                mapZOOM = 2.0;
                blockWidth.setZOOM(mapZOOM);
                buttonDisplayZOOM.setText(Double.toString(mapZOOM));
            }
        });
        root.getChildren().add(buttonZoomInc);

        //ZOOM- button
        Button buttonZoomDec = new Button("-ZOOM");
        buttonZoomDec.setStyle("-fx-border-color: #7F9AFE; -fx-border-width: 1px;");
        buttonZoomDec.setLayoutX(xSur - 180);
        buttonZoomDec.setLayoutY(ySur);
        buttonZoomDec.setOnAction(event1 -> {
            if (mapZOOM == 2.0) {
                mapZOOM = 1.5;
                blockWidth.setZOOM(mapZOOM);
                buttonDisplayZOOM.setText(Double.toString(mapZOOM));
            } else if (mapZOOM == 1.5) {
                mapZOOM = 1.0;
                blockWidth.setZOOM(mapZOOM);
                buttonDisplayZOOM.setText(Double.toString(mapZOOM));
            } else if (mapZOOM == 1.0) {
                mapZOOM = 0.50;
                blockWidth.setZOOM(mapZOOM);
                buttonDisplayZOOM.setText(Double.toString(mapZOOM));
            } else if (mapZOOM == 0.50) {
                mapZOOM = 0.25;
                blockWidth.setZOOM(mapZOOM);
                buttonDisplayZOOM.setText(Double.toString(mapZOOM));
            }
        });
        root.getChildren().add(buttonZoomDec);

        //RESET button
        Button buttonReset = new Button("RESET");
        buttonReset.setStyle("-fx-border-color: #FE2D2D; -fx-border-width: 5px;");
        buttonReset.setLayoutX(330);
        buttonReset.setLayoutY(ySur);
        buttonReset.setOnAction(event1 -> {
            controller.stop();
            pausedScene = false;
            scenePlaying = false;
            buttonPause.setText("PAUSE");
            System.out.println("RESET");
            start(primaryStage);
        });

        //Play button
        Button buttonPlay = new Button("PLAY");
        buttonPlay.setStyle("-fx-border-color: #3BB83E; -fx-border-width: 5px;");
        buttonPlay.setLayoutX(200);
        buttonPlay.setLayoutY(ySur);
        buttonPlay.setOnAction(event1 -> {
            if (pausedScene) {
                System.out.println("STOP PAUSE");
                buttonPause.setText("PAUSE");
                controller.play(mapSpeed);
                pausedScene = false;
            } else if (!scenePlaying){ //ochrana pred multi-clicking play
                start(primaryStage);
                System.out.println("START");
                buttonPause.setText("PAUSE");
                controller.start(mapSpeed);
                scenePlaying = true;
                root.getChildren().add(buttonPause);
                root.getChildren().add(buttonReset);
            }
        });
        root.getChildren().add(buttonPlay);

        //EXIT button
        Button buttonExit = new Button("EXIT");
        buttonExit.setStyle("-fx-border-color: #FE2D2D; -fx-border-width: 5px;");
        buttonExit.setLayoutX(450);
        buttonExit.setLayoutY(ySur);
        buttonExit.setOnAction(event1 -> {
            System.out.println("Tlacitko EXIT: Aplikacia ukoncena uzivatelom");
            System.exit(0);
        });
        root.getChildren().add(buttonExit);
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