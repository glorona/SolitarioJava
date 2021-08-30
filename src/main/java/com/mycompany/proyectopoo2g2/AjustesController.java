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
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author gabri
 */
public class AjustesController {
    
    @FXML
    ComboBox cmbSugerencias;
    @FXML
    ComboBox cmbCartas;
    


    public void initialize() {
       cmbCartas.getItems().setAll("Españolas", "Poker");
       cmbCartas.getSelectionModel().select(App.cartaSeleccion); //items por defecto
       cmbSugerencias.getItems().setAll("Si", "No");
       cmbSugerencias.getSelectionModel().select(App.mostraSugerencia);
       
       cmbCartas.setOnAction((event) -> {
        App.cartaSeleccion = cmbCartas.getSelectionModel().getSelectedIndex();
  

});
       
       
    }   
    @FXML
    private void switchToMenu() throws IOException {
        App.cambioScene("menu");
    }
    
    
}
