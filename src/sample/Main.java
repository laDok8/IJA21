package sample;

/*import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;*/

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {


    /*Pane root; extends Application

    @Override
    public void start(Stage primaryStage){*/


        /*<taskdef resource="com/sun/javafx/tools/ant/antlib.xml"
        uri="javafx:com.sun.javafx.tools.ant" classpath=".:C:\Program Files\Java\jdk1.7.0_09\lib\ant-javafx.jar"/>*/
        /*Button btn = new Button();
        btn.setText("Say 'Hello World'");*/

        /*btn.setOnAction(actionEvent -> {
            btn.setLayoutX(btn.getLayoutX() + 10);
            btn.setLayoutY(btn.getLayoutY() + 10);
        })

        //root = new Pane();
        /*root.getChildren().add(btn);
        btn.setLayoutX(10);
        btn.setLayoutY(10);*/

        /*Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();*/
    //}

    public static void changeTrolleyPosition(Trolley trolley, int itemNumber, int moveCount, String[][] map){
        //Changing trolley X-sur
        if (trolley.x < trolley.newPosition[itemNumber][moveCount][0]) {
            trolley.moveX(1);
        } else if (trolley.x > trolley.newPosition[itemNumber][moveCount][0]) {
            trolley.moveX(-1);
        }

        //Changing trolley y-sur
        if (trolley.y < trolley.newPosition[itemNumber][moveCount][1]) {
            trolley.moveY(1);
        } else if (trolley.y > trolley.newPosition[itemNumber][moveCount][1]) {
            trolley.moveY(-1);
        }
        map[trolley.y][trolley.x] = "T";

    }

    public static void printMap(String[][] map){
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                System.out.print(map[row][col]);
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        //launch(args);
        JsonParser jsonParser = null;
        try {
            jsonParser = new JsonParser("sample.json");
        } catch (Exception e) {
            System.err.println("chyba parser: sample.json");
            System.exit(1);
        }
        final int maxX = jsonParser.getMaxX();
        final int maxY = jsonParser.getMaxY();


        RequirementsParser reqParser = null;
        try {
            reqParser = new RequirementsParser("requirements.json");
        } catch (Exception e) {
            System.err.println("chyba parser: requirements.json");
            System.exit(2);
        }

        jsonParser.getAllGoods().forEach((cordinates, tmp) -> {
            System.out.println("Stored-Obj: " + cordinates.x + " : " + cordinates.y + " " + tmp);
        });
        System.out.println("----------------------------------------------------------");
        reqParser.requirements.forEach((name, count) -> {
            System.out.println("REQUIRE-name: " + name + ", require amount: " + count);
        });
        System.out.println("\n**************************************************************************");
        System.out.println("*************************  ITEMS *****************************************");
        System.out.println("**************************************************************************\n\n");

        //initalize pathifinding sturcure
        FindPath findPath = new FindPath(maxX, maxY);
        //update every time shelfs are changed
        findPath.updatePaths(jsonParser.getAllGoods());


        JsonParser finalJsonParser = jsonParser;
        reqParser.requirements.forEach((name, count) -> {
            Cordinates myPositionCord = new Cordinates(finalJsonParser.getTrolleys().get(0).x, finalJsonParser.getTrolleys().get(0).y);
            Trolley trolley = finalJsonParser.getTrolleys().get(0); //TODO: ID vozika ktory berie polozku

            TreeMap<Double, Cordinates> sortedByDistances = new TreeMap<>();
            int RequiredItemsAmount = count;
            Map<Cordinates, Shelf> found = finalJsonParser.findGoods(name);

            if (found.size() < 1) {
                System.out.println("!!!!!!! ERROR: POZADOVANA POLOZKA: " + name + " NENI SKLADEM !!!!!!!!!");
            }

            for (Map.Entry<Cordinates, Shelf> entry : found.entrySet()) {
                int x = entry.getKey().x;//cordinate
                int y = entry.getKey().y;
                //entry.getValue();//shelf
                int countItemsInShelf = entry.getValue().stored.get(name);
                double distance = entry.getKey().getDistance(myPositionCord);
                sortedByDistances.put(distance, entry.getKey());

                System.out.println("name: " + name);
                System.out.println("x: " + x);
                System.out.println("y: " + y);
                System.out.println("shelf count: " + countItemsInShelf);
                System.out.println("distance: " + distance);

                System.out.println("---------------------------");
                System.out.print("POCET POLOZIEK NA SKLADE: ");
                if (found.size() == 1) { //polozka je iba v 1 Shelfe
                    if (RequiredItemsAmount <= countItemsInShelf) {
                        trolley.addItem(name, count);
                        System.out.println("CISLO POLOZKY V TROLLEY LISTE: "+(trolley.itemsAmount() - 1));
                        System.out.println("OK");
                    } else {    //ERROR - chcem viac ks ako mame na sklade
                        System.out.println("!!!!!!! ERROR: POZADOVANE MNOZSTVI NENI SKLADEM !!!!!!!!!");
                    }
                } else {    //polozka je vo viacerych shelf-och
                    System.out.println("Polozka je vo viacerich shelf-och TODO:NENI implementovano");
                    trolley.addItem(name, count);
                    System.out.println("CISLO POLOZKY V TROLLEY LISTE: "+(trolley.itemsAmount() - 1));
                    //TODO: vybrat najblizsi regal + zistit ci postacuje mnozstvo na nom alebo pridat aj dalsi regal
                }
                System.out.println("---------------------------");
            }


            if (sortedByDistances.size() != 0) {
                Cordinates lowestDisanceCord = sortedByDistances.firstEntry().getValue(); //min distance Cordinates value
                PathNode vysl2 = findPath.aStar(myPositionCord, lowestDisanceCord);

                int moveCount = 0;
                //System.out.println("POCET POLOZEK NA VOZIKU: " + trolley.itemsAmount());
                while (vysl2 != null) {
                    System.out.println("ZAPISUJEM SURADNICE PRE POHYB VOZIKA: " + vysl2.self.x + ":" + vysl2.self.y);
                    trolley.addNewPosition(trolley.itemsAmount() - 1, moveCount, vysl2.self.x, vysl2.self.y);
                    //System.out.println("ZAPISUJEM SURADNICE POLOZKY: " + (trolley.itemsAmount() - 1));
                    vysl2 = vysl2.parent;
                    moveCount++;
                }
            }
            System.out.println("************************* NEXT ITEM *****************************************");
        });

        Boolean running = true;
        while (running) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Create empty map
            String[][] map = new String[maxX][maxY];
            for (String[] row : map) {
                Arrays.fill(row, " ");
            }

            //put all shelf into map
            jsonParser.getAllGoods().forEach((cordinates, tmp) -> {
                map[cordinates.y][cordinates.x] = "X";
            });
            //put all trolley into map
            map[finalJsonParser.getTrolleys().get(0).y][finalJsonParser.getTrolleys().get(0).x] = "T";

            Trolley trolley = finalJsonParser.getTrolleys().get(0); //TODO: ID vozikov

            for (int itemNumber = trolley.itemsAmount() - 1; itemNumber >= 0; itemNumber--){
                System.out.println("-----------------------------------------------------------------------------------");
                System.out.println("*********** TROLLEY ROUTE from start to item number: ********** " + itemNumber);
                //Trolley route from start position to Shelf
                for (int moveCount = trolley.newPosition[itemNumber].length - 1; moveCount >= 0; moveCount--) {
                    if (trolley.newPosition[itemNumber][moveCount][0] != null) {
                        //map[trolley.y][trolley.x] = " ";  //mazanie cesty vozika
                        changeTrolleyPosition(trolley, itemNumber, moveCount, map);
                        printMap(map);
                        System.out.println("--------- MAP UPDATE -----------------");
                    }
                }
                System.out.println("*********** TROLLEY ROUTE BACK to START **********");
                //Trolley route from Shelf to start
                for (int moveCount = 0; moveCount <= trolley.newPosition[itemNumber].length - 1; moveCount++) {
                    if (trolley.newPosition[itemNumber][moveCount][0] != null) {
                        map[trolley.y][trolley.x] = " ";  //mazanie cesty vozika
                        changeTrolleyPosition(trolley, itemNumber, moveCount, map);
                        printMap(map);
                        System.out.println("--------- MAP UPDATE -----------------");
                    }
                }
            }
            running = false;
        }
    }
}