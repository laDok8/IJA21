package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.*;

public class Main extends Application {


    Pane root;

    @Override
    public void start(Stage primaryStage){

        Button btn = new Button();
        btn.setText("Say 'Hello World'");

        /*btn.setOnAction(actionEvent -> {
            btn.setLayoutX(btn.getLayoutX() + 10);
            btn.setLayoutY(btn.getLayoutY() + 10);
        });*/

        root = new Pane();
        /*root.getChildren().add(btn);
        btn.setLayoutX(10);
        btn.setLayoutY(10);*/

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
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

        // requirements.json parser
        RequirementsParser reqParser = null;
        try {
            reqParser = new RequirementsParser("requirements.json");
        } catch (Exception e) {
            System.err.println("chyba parser: requirements.json");
            System.exit(2);
        }

        jsonParser.storedObjects.forEach((cordinates, tmp) -> {
            System.out.println("Stored-Obj: " + cordinates.x + " : " + cordinates.y + " " + tmp);
        });
        System.out.println("----------------------------------------------------------");
        jsonParser.trolleyObjects.forEach((id, co) -> {
            System.out.println("TROLLEY-id: " + id + ", position: " + co.x + " : " + co.y);
        });
        System.out.println("----------------------------------------------------------");
        reqParser.requirements.forEach((name, count) -> {
            System.out.println("REQUIRE-name: " + name + ", require amount: " + count);
        });
        System.out.println();
        System.out.println("**************************************************************************");
        System.out.println("*************************  ITEMS *****************************************");
        System.out.println("**************************************************************************");
        System.out.println();
        System.out.println();

        Node.updatePaths(jsonParser.storedObjects);
        //var vysl = Node.aStar(new Cordinates(1, 1), new Cordinates(2, 1), jsonParser.storedObjects);
        //var xz = jsonParser.findGoods("boty");

        JsonParser finalJsonParser = jsonParser;
        reqParser.requirements.forEach((name, count) -> {
            Cordinates myPositionCord = new Cordinates(1, 1);
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
                var vysl2 = Node.aStar(myPositionCord, lowestDisanceCord);
                while (vysl2 != null) {
                    System.out.print(vysl2 + "->");
                    vysl2 = vysl2.parent;
                }
                System.out.println("PATH_END");
                System.out.println("ASTAR OUTPUT: " + vysl2);
            }
            System.out.println();
            System.out.println("************************* NEXT ITEM *****************************************");
            System.out.println();
        });


        //System.exit(0);
    }
}
