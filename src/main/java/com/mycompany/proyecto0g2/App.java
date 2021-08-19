package com.mycompany.proyecto0g2;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

   private static Stage stageapi;
    private static Scene scene;
    
    
    //cargar fxml
    public static Parent loadFXML(String fxml) throws IOException {
        File f = new File("src/main/java/FXMLC/" + fxml + ".fxml");
        return FXMLLoader.load(f.toURI().toURL());
    }
    
    //cambiar escena
   public static void cambioScene(String fxml) throws IOException {
       Parent root = loadFXML(fxml);
       Scene escena_new = new Scene(root);
       stageapi.setScene(escena_new);
       
   }


    @Override
    public void start(Stage stage) throws IOException {
        stageapi = stage;
        scene = new Scene(loadFXML("menu"), 640, 480);
        stageapi.setScene(scene);
        stageapi.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}