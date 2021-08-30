package com.mycompany.proyectopoo2g2;

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
    public static  String rutaSpain = "Spain/";
    public static String rutaPoker = "Poker/";
    public static String rutaCubierta = "src/main/resources/Cubierta/";
    public static String rutaArchivoPoker = "archivosCartas/cartas.txt";
    public static String rutaArchivoSpain = "archivosCartas/CartasEspañolas.txt";
    public static int cartaSeleccion;
    public static int mostraSugerencia;
    
    public static String rutaArchivoPart = "archivos/partidas.ser" ;
    
    
    //cargar fxml
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    //cambiar escena
   public static void cambioScene(String fxml) throws IOException {
       Parent root = loadFXML(fxml);
       Scene escena_new = new Scene(root);
       stageapi.setScene(escena_new);
       
   }


    @Override
    public void start(Stage stage) throws IOException {
        cartaSeleccion = 1; //poker por defecto
        mostraSugerencia = 1; //si se muestran sugerencias por defecto
        stageapi = stage;
        scene = new Scene(loadFXML("menu"), 640, 480);
        stageapi.setScene(scene);
        stageapi.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}