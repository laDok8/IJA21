package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
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
            System.err.println("chyba parser");
            System.exit(1);
        }
        xx.storedObjects.forEach((key, value) -> {
            System.out.println(key+" : "+value);
        });

        System.exit(0);
    }
}
