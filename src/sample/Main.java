package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        //launch(args);
        JsonParser xx = null;
        try {
            xx = new JsonParser("sample.json");
        } catch (Exception e) {
            System.err.println("chyba parser: sample.json");
            System.exit(1);
        }

        // requirements.json parser
        RequirementsParser yy = null;
        try {
            yy = new RequirementsParser("requirements.json");
        } catch (Exception e) {
            System.err.println("chyba parser: requirements.json");
            System.exit(2);
        }

        /*xx.storedObjects.forEach((cordinates, tmp) -> {
            System.out.println(cordinates.x+" : "+ cordinates.y);
        });*/

        Node.aStar(new Cordinates(1, 1), new Cordinates(1, 2), xx.storedObjects);

        System.exit(0);
    }
}
