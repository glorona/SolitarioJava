/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyecto0g2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;



public class PartidaController implements Initializable {
    
    
    private cuentaTiempo time;
    
    @FXML
    private Label tiempo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        time = new cuentaTiempo();
        Thread timeStart = new Thread(time);
        timeStart.start();
        
    }
    
    @FXML
    private void switchToMenu() throws IOException {
        System.out.println("Volviendo al Menu Principal...");
        App.cambioScene("menu");
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
