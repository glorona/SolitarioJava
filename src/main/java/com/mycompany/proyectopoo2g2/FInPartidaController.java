package com.mycompany.proyectopoo2g2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author gabri
 */
public class FinPartidaController implements Initializable {
    
    @FXML
    private VBox finpar;
     @FXML
    private Button volverMenu;
     @FXML
    private Label labelJugador;
     @FXML
    private Label labelPuntos;
     @FXML
    private Label labelPuntosO;
     @FXML
    private Label pilas;
     @FXML
    private Label ganador;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void switchToMenu() throws IOException {
        System.out.println("Volviendo al Menu Principal...");
        App.cambioScene("menu");
    }
    
    
}
