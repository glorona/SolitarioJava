/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectopoo2g2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author gabri
 */
public class ReporteController implements Initializable {
    
    
    @FXML
    private Button btnRegresar;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
        @FXML
    private void switchToMenu() throws IOException {
        System.out.println("Cambiando escena");
        App.cambioScene("menu");
    }


    
    
}
