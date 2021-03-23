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
        jsonParser xx = null;
        try {
            xx = new jsonParser("sample.json");
        } catch (Exception e) {
            System.err.println("chyba parser");
            System.exit(1);
        }
        for(var x : xx.storedObjects){
            System.out.println(x.x+" "+x.y+" ");
        }




        System.exit(0);
    }
}
