/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectopoo2g2;

import juego.Carta;
import juego.Partida;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;



public class PartidaController implements Initializable {
    
    
    private cuentaTiempo time;
    
    @FXML
    private Label tiempo;
    @FXML
    private HBox containerplayer;
    @FXML
    private HBox containerCPU;
    @FXML
    private FlowPane containerCardsMesa;
    @FXML
    private Label jugador;
    @FXML
    private StackPane stackCartasMazo;
    @FXML
    private StackPane stackCartasJ;
    @FXML
    private StackPane stackCartasC;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        time = new cuentaTiempo();
        Thread timeStart = new Thread(time);
        timeStart.start();
        repartirMesa();
        
        
    }
   
    @FXML
    private void switchToMenu() throws IOException {
        System.out.println("Volviendo al Menu Principal...");
        App.cambioScene("menu");
    }
    
    private void repartirMesa(){
        containerplayer.getChildren().clear();
        containerCPU.getChildren().clear();
        containerCardsMesa.getChildren().clear();
        stackCartasMazo.getChildren().clear();
        stackCartasJ.getChildren().clear();
        stackCartasC.getChildren().clear();
        
        if(App.cartaSeleccion == 1){
            
        ArrayList<Carta> mazo = Carta.obtenerCartas(App.rutaArchivoPoker);
        Partida.barajarMazo(mazo);
        
        for(int i=0; i<3;i++){ //repartir cartas de jugador
            System.out.println("Creando imagen....");
            ImageView iv = new ImageView();
            
            try{
                String rutaimg = App.class.getResource( "/Poker/" + mazo.get(i).getRutaimg()).getPath();
                FileInputStream ins = new FileInputStream(rutaimg);
                Image imcard = new Image(ins, 80,100,false,false);
                iv.setImage(imcard);
                mazo.remove(i);
                
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
            
            containerplayer.getChildren().add(iv);
            
        }
            
            for(int z=0; z<3;z++){ //repartir cartas de cpu
            
            ImageView ivc = new ImageView();
            
            try{
                String rutaimg = App.class.getResource("/Cubierta/" + "back1.png").getPath();
                FileInputStream ins = new FileInputStream(rutaimg);
                Image imcard = new Image(ins, 80,100,false,false);
                ivc.setImage(imcard);
                mazo.remove(z);
                
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
            
            containerCPU.getChildren().add(ivc);
            
            
            
            
        }
        }
        
        
    }
    
    private void dibujaCartas(){
        
    }
    
   

    private class cuentaTiempo implements Runnable {
        private int sec;
        private int min;
        
        private boolean ex = true;
        
        public void run(){
            try{
                while(ex){
                    Platform.runLater(()->{
                    tiempo.setText(agregaTiempo());
                });
                    sec++;
                    Thread.sleep(1000);
                }
                
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    
    
    private String agregaTiempo(){
        if(sec >= 60){
            min++;
            sec=0;
        }
        return String.format("Tiempo Transcurrido: %02d:%02d", min,sec);
    }
    }
    
}
