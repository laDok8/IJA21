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

import javax.swing.*;
import java.util.*;

public class Main{


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
                        System.out.println("OK");
                        //TODO: delete_item + poslat vozik k regalu + mnozstvi polozek ve voziku++
                    } else {    //ERROR - chcem viac ks ako mame na sklade
                        System.out.println("!!!!!!! ERROR: POZADOVANE MNOZSTVI NENI SKLADEM !!!!!!!!!");
                    }
                } else {    //polozka je vo viacerych shelf-och
                    System.out.println("Polozka je vo viacerich shelf-och TODO:NENI implementovano");
                    //TODO: vybrat najblizsi regal + zistit ci postacuje mnozstvo na nom alebo pridat aj dalsi regal
                }
                System.out.println("---------------------------");
            }


            if (sortedByDistances.size() != 0) {
                Cordinates lowestDisanceCord = sortedByDistances.firstEntry().getValue(); //min distance Cordinates value
                System.out.println("(CHECKING): Vybrana kratsia vzdialenost ma suradnice: x:" + lowestDisanceCord.x + " y:" + lowestDisanceCord.y);
                PathNode vysl2 = findPath.aStar(myPositionCord, lowestDisanceCord);



                while (vysl2 != null) {
                    //System.out.print(vysl2 + "->");
                    Trolley trolley = finalJsonParser.getTrolleys().get(0);
                    System.out.println("Vozik ma suradnice: x:" + myPositionCord.x + " y:" +myPositionCord.y);
                    //System.out.println("NOVE X,Y: " + vysl2.self.x + ":" + vysl2.self.y );

                    //naopak
                    int trolleyMove[] = new int[2];
                    PathNode vysl3 = vysl2;
                    while (vysl3.parent.self != myPositionCord){
                        trolleyMove[0] = vysl3.self.x;
                        trolleyMove[1] = vysl3.self.y;

                        System.out.println("NOVE X,Y: " +trolleyMove[0] + ":" + trolleyMove[1] );

                        if (vysl3.self.x > myPositionCord.x){
                            trolley.moveX(1);
                        } else  if (vysl3.self.x < myPositionCord.x){
                            trolley.moveX(-1);
                        }
                        if (vysl3.self.y > myPositionCord.y){
                            trolley.moveY(1);
                        } else  if (vysl3.self.y < myPositionCord.y){
                            trolley.moveY(-1);
                        }
                        vysl3 = vysl3.parent;
                    }

                    System.out.println("Vozik ma NOVE suradnice: x:" + myPositionCord.x + " y:" +myPositionCord.y);
                    System.out.println("--------------------------------------------------------");

                    //Trolley trolley = finalJsonParser.getTrolleys().get(0);
                    //trolley.moveX(trolleyMove[0]);
                    //System.out.println("Vozik ma suradnice: x:" + myPositionCord.x + " y:" +myPositionCord.y);
                    //System.out.println("MOVE TROLEY: " +trolleyMove[0] + ":" + trolleyMove[1]);

                    vysl2 = vysl2.parent;
                }
                //System.out.println("PATH_END");
                //System.out.println("ASTAR OUTPUT: " + vysl2);
            }
            System.out.println();
            System.out.println("************************* NEXT ITEM *****************************************");
            System.out.println();
        });

        Boolean running = true;
        while(running) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //define map
            String[][] map = new String[maxX][maxY];
            for (String[] row: map){
                Arrays.fill(row, "  ");
            }

            //put shelf into map
            jsonParser.getAllGoods().forEach((cordinates, tmp) -> {
                map[cordinates.y][cordinates.x] = "X";
            });

            //put trolley into map
            //finalJsonParser.getTrolleys().forEach((id, co) -> {
            map[finalJsonParser.getTrolleys().get(0).x][finalJsonParser.getTrolleys().get(0).y] = "O";
            //});

            //print map
            for (int row = 0; row < map.length; row++) {
                for (int col = 0; col < map[row].length; col++) {
                    System.out.print(map[row][col]);
                }
                System.out.println("");
            }
            System.out.println("--------- NEXT MAP UPDATE -----------------");
        }

    }
}
