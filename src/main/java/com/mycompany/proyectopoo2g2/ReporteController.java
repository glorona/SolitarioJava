/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectopoo2g2;

import juego.Partida;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author gabri
 */
public class ReporteController implements Initializable {
    
    private SimpleStringProperty fch,nom;
    
    @FXML 
    private TableView<Partida> tvInfo;
    
    @FXML
    private TableColumn<Partida,Date> fecha;
    @FXML
    private TableColumn<Partida,String>nombre;
    @FXML
    private TableColumn <Partida,String>puntosJ;
    @FXML
    private TableColumn <Partida,String>puntosOp;
    @FXML
    private TableColumn <Partida,String> difPuntaje;
    @FXML
    private TableColumn <Partida,String>tiempo;
    @FXML 
    private TableColumn <Partida,String>pilasRobadas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombre.setCellValueFactory(
                new PropertyValueFactory<Partida, String>("nombreP"));
        fecha.setCellValueFactory(
                new PropertyValueFactory<Partida, Date>("fecha"));
        
        puntosJ.setCellValueFactory(
                new PropertyValueFactory<Partida, String>("puntos"));
        puntosOp.setCellValueFactory(
                new PropertyValueFactory<Partida, String>("puntosOponente"));
        difPuntaje.setCellValueFactory(
                new PropertyValueFactory<Partida, String>("diferenciaPuntaje"));
        tiempo.setCellValueFactory(
                new PropertyValueFactory<Partida, String>("tiempoPartida"));
        pilasRobadas.setCellValueFactory(
                new PropertyValueFactory<Partida, String>("pilasRobadas"));
       llenarTableView();
    }
    
        @FXML
    private void switchToMenu() throws IOException {
        System.out.println("Cambiando escena");
        App.cambioScene("menu");
    }
    
    
    public void llenarTableView(){
        ObservableList<Partida> data = FXCollections.observableArrayList(Partida.cargarInfo(App.rutaArchivoPart));

        tvInfo.setItems(data);
        
        //tvInfo.getItems().setAll(Partida.cargarInfo(App.rutaArchivoPart));
    }

    
    
}
