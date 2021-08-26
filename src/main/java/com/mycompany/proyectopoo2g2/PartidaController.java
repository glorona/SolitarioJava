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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;



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
    
    private Carta cardSelect;
    
    private Carta cardSelect2;
    
    private String nombreJugador;
    
    private ArrayList<Carta> mazo;
    
    private ArrayList<Carta> manoJ;
    
    private ArrayList<Carta> manoC;
    
    private ArrayList<Carta> mesa;
    
    private ArrayList<Carta> pilaJ;
    
    private ArrayList<Carta> pilaC;
    
    private boolean confirmaJugada;
    private boolean confMesa;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
        TextInputDialog alert = new TextInputDialog();
        alert.setTitle("Iniciar Nuevo Juego");
        alert.setHeaderText("Ingresar Nombre de Jugador");
        alert.setContentText("Ingrese nombre de jugador para empezar el juego");
        Optional<String> result = alert.showAndWait();
        nombreJugador = alert.getResult();   
        
        manoJ = new ArrayList<>();
        manoC = new ArrayList<>();
        mesa = new ArrayList<>();
        pilaJ = new ArrayList<>();
        pilaC = new ArrayList<>();
        
        
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
    
    
    private boolean jugarCartaPila(Carta cardSelect, Carta cardSelect2, ArrayList<Carta> mano, ArrayList<Carta> pila ){
        boolean conf = false;
        if(cardSelect.getValor().equals(cardSelect2.getValor())){
            System.out.println("Juega carta");
            //eliminar carta del mazo del jugador
            mano.remove(cardSelect);
            //eliminar carta de la mesa
            mesa.remove(cardSelect2);
            //agregar ambas cartas a la pila del jugador
            pila.add(cardSelect);
            pila.add(cardSelect2);
            //si esto se termina exitosamente, se retornara booleano de exito
            conf = true;
        }
        return conf;
 
    }
    
    private boolean jugarCartaMesa(Carta cardSelect, ArrayList<Carta> mano){
            boolean confM = false;
            System.out.println("Juega carta a la mesa");
            //eliminar carta del mazo del jugador
            mano.remove(cardSelect);
            //eliminar carta de la mesa
            mesa.add(cardSelect);
            //si esto se termina exitosamente, se retornara booleano de exito
            confM = true;
            
        
        return confM;
 
    }
    
    private void turnoComputadora(ArrayList<Carta> mano, ArrayList<Carta> pila, ArrayList<Carta> mesa){
        boolean confirmacionJugada = false;
        for (Carta c: mano){
            cardSelect = c;
            for(Carta c2 : mesa){
                cardSelect2 = c2;
                confirmacionJugada = jugarCartaPila(c,c2,mano,pila);
                while(!confirmacionJugada){
                    
                }
                
            }
        }
        
    }
    
   
    
    private void repartirMesa(){
        containerplayer.getChildren().clear();
        containerCPU.getChildren().clear();
        containerCardsMesa.getChildren().clear();
        stackCartasMazo.getChildren().clear();
        stackCartasJ.getChildren().clear();
        stackCartasC.getChildren().clear();
        
        jugador.setText("Jugador: " + nombreJugador);
        
        if(App.cartaSeleccion == 1){ //poker
            
        mazo = Carta.obtenerCartas(App.rutaArchivoPoker);
        Partida.barajarMazo(mazo);
        
        for(int i=0; i<3;i++){ //repartir cartas de jugador
            System.out.println("Creando imagen....");
            ImageView ivJ = new ImageView();
            
            try{
                Carta c = mazo.get(i);
                String rutaimg = App.class.getResource( "/Poker/" + mazo.get(i).getRutaimg()).getPath();
                FileInputStream ins = new FileInputStream(rutaimg);
                Image imcard = new Image(ins, 80,100,false,false);
                ivJ.setImage(imcard);
                manoJ.add(c);
                mazo.remove(i);
                
                
                ivJ.setOnMouseClicked(ev -> {
                    cardSelect = c;
                    System.out.println("Carta seleccionada: " + c.getValor());
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setSaturation(0.8);
                    ivJ.setEffect(colorAdjust);
                    if(confirmaJugada){
                        
                        ivJ.setEffect(null);
                        containerplayer.getChildren().remove(ivJ);
                        stackCartasJ.getChildren().add(ivJ);
                        confirmaJugada = false;
                    }
                    if(confMesa){
                        ivJ.setEffect(null);
                        containerplayer.getChildren().remove(ivJ);
                        containerCardsMesa.getChildren().add(ivJ);
                        confMesa = false;
                    }
                    
                });
                
                ivJ.setOnMouseEntered(ev ->{
                    
                    
                });
                
                
                
                
                
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
            
            containerplayer.getChildren().add(ivJ);
            
        }
            
            for(int z=0; z<3;z++){ //repartir cartas de cpu
            
            ImageView ivc = new ImageView();
            
            try{
                Carta c = mazo.get(z);
                String rutaimg = App.class.getResource("/Cubierta/" + "back1.png").getPath();
                FileInputStream ins = new FileInputStream(rutaimg);
                Image imcard = new Image(ins, 80,100,false,false);
                ivc.setImage(imcard);
                manoC.add(c);
                mazo.remove(z);
                
                
                
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
            
            containerCPU.getChildren().add(ivc);
            
            
            
            
        }
            
            for(int i=0; i<4;i++){ //repartir cartas de mesa
            ImageView iv = new ImageView();
            
            try{
               Carta  c = mazo.get(i);
                String rutaimg = App.class.getResource( "/Poker/" + mazo.get(i).getRutaimg()).getPath();
                FileInputStream ins = new FileInputStream(rutaimg);
                Image imcard = new Image(ins, 80,100,false,false);
                iv.setImage(imcard);
                mesa.add(c);
                mazo.remove(i);
                
                iv.setOnMouseClicked(ev -> {
                    cardSelect2= c;
                    System.out.println("Carta seleccionada: " + c.getValor());
                    if(cardSelect != null){
                        ColorAdjust colorAdjust = new ColorAdjust();
                        colorAdjust.setSaturation(0.8);
                         iv.setEffect(colorAdjust);
                        boolean conf = jugarCartaPila(cardSelect, cardSelect2, manoJ, pilaJ);
                        System.out.println("Se jugo carta");
                        if(conf){
                            //sacar la carta del contenedor de mesa
                            iv.setEffect(null);
                            containerCardsMesa.getChildren().remove(iv);
                            confirmaJugada = true;
                            stackCartasJ.getChildren().add(iv);
                            cardSelect = null;
                            cardSelect2 = null;
                        
                        }
                    }
                    else{
                        ColorAdjust colorAdjust = new ColorAdjust();
                        iv.setEffect(null);
                        cardSelect = null;
                        cardSelect2 = null;
                        
                    }
                    
                });
                
                containerCardsMesa.setOnMouseClicked(ev ->{
                    if(cardSelect != null){
                        boolean confMesaJ = jugarCartaMesa(cardSelect, manoJ);
                        if (confMesaJ){
                            confMesa = true;
                            
                            
                            
                        }
                    }
                    
                });
                
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
            
            containerCardsMesa.getChildren().add(iv);
            
        }
         
        for (Carta c: mazo){ //anadir al stackpane
            ImageView iv = new ImageView();
            
            try{
                String rutaimg = App.class.getResource( "/Cubierta/" + "back1.png").getPath();
                FileInputStream ins = new FileInputStream(rutaimg);
                Image imcard = new Image(ins, 120,150,false,false);
                iv.setImage(imcard);
                
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
            
            stackCartasMazo.getChildren().add(iv);
            
        }
            
        }
        
        if(App.cartaSeleccion == 0){ //espaniolas
            
        ArrayList<Carta> mazo = Carta.obtenerCartas(App.rutaArchivoSpain);
        Partida.barajarMazo(mazo);
        
        for(int i=0; i<3;i++){ //repartir cartas de jugador
            System.out.println("Creando imagen....");
            ImageView iv = new ImageView();
            
            try{
                String rutaimg = App.class.getResource( "/Spain/" + mazo.get(i).getRutaimg()).getPath();
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
                String rutaimg = App.class.getResource("/Cubierta/" + "back2.png").getPath();
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
            
            for(int i=0; i<4;i++){ //repartir cartas de mesa
            ImageView iv = new ImageView();
            
            try{
                String rutaimg = App.class.getResource( "/Spain/" + mazo.get(i).getRutaimg()).getPath();
                FileInputStream ins = new FileInputStream(rutaimg);
                Image imcard = new Image(ins, 120,150,false,false);
                iv.setImage(imcard);
                mazo.remove(i);
                
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
            
            containerCardsMesa.getChildren().add(iv);
            
        }
            
            for (Carta c: mazo){ //anadir al stackpane
            ImageView iv = new ImageView();
            
            try{
                String rutaimg = App.class.getResource( "/Cubierta/" + "back2.png").getPath();
                FileInputStream ins = new FileInputStream(rutaimg);
                Image imcard = new Image(ins, 120,150,false,false);
                iv.setImage(imcard);
                
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
            
            stackCartasMazo.getChildren().add(iv);
            
        }
            
        }
        System.out.println("Jugador");
        for(int i=0; i<manoJ.size();i++){ //repartir cartas de jugador
            System.out.print(manoJ.get(i).getValor()+" ");
            System.out.println(manoJ.get(i).getPalo());
        }
        System.out.println("cpu");
        for(int i=0; i<manoC.size();i++){ 
            System.out.print(manoC.get(i).getValor()+" ");
            System.out.println(manoC.get(i).getPalo());
        }
        System.out.println("Mesa");
        for(int i=0; i<mesa.size();i++){ //repartir cartas de jugador
            System.out.print(mesa.get(i).getValor()+" ");
            System.out.println(mesa.get(i).getPalo());
        }
        System.out.println("stackpane");
        for(int i=0; i<mazo.size();i++){ //repartir cartas de jugador
            System.out.print(mazo.get(i).getValor()+" ");
            System.out.println(mazo.get(i).getPalo());
        }
        
        
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
