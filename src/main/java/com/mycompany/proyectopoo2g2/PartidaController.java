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
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    private Label errores;
    @FXML
    private StackPane stackCartasMazo;
    @FXML
    private StackPane stackCartasJ;
    @FXML
    private StackPane stackCartasC;
    @FXML 
    private Button cartaMesa;
    
    private Carta cardSelect;
    
    private Carta cardSelect2;
    
    private String nombreJugador;
    
    private ArrayList<Carta> mazo;
    
    private ArrayList<Carta> manoJ;
    
    private ArrayList<Carta> manoC;
    
    private ArrayList<Carta> mesa;
    
    private ArrayList<Carta> pilaJ;
    
    private ArrayList<Carta> pilaC;
    

    private int cantErr;
    
    

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
        cantErr = 0;
        manoJ = new ArrayList<>();
        manoC = new ArrayList<>();
        mesa = new ArrayList<>();
        pilaJ = new ArrayList<>();
        pilaC = new ArrayList<>();
        
        
        time = new cuentaTiempo();
        Thread timeStart = new Thread(time);
        timeStart.start();
        iniciarPartida();
        
        
    }
   
    @FXML
    private void switchToMenu() throws IOException {
        System.out.println("Volviendo al Menu Principal...");
        App.cambioScene("menu");
    }
    
    private void robaPila(Carta c,ArrayList<Carta> pilaJ, ArrayList<Carta> pilaC, StackPane sj, StackPane sc, boolean turno){
        if(turno){
            pilaJ.addAll(pilaC); //robo todo digitalmente
            sj.getChildren().addAll(sc.getChildren()); //me llevo toda la pila
            sc.getChildren().clear(); //chao pila de rival
            sj.getChildren().add(c.getImgcart()); //agrego la ultima carta
            containerplayer.getChildren().remove(c.getImgcart()); //la saco de la mano del que juega la carta
            
        }
        if(turno){
            pilaC.addAll(pilaJ); //robo todo digitalmente
            sc.getChildren().addAll(sj.getChildren()); //me llevo toda la pila
            sj.getChildren().clear(); //chao pila de rival
            sc.getChildren().add(c.getImgcart()); //agrego la ultima carta
            containerCPU.getChildren().remove(c.getImgcart()); //la saco de la mano del que juega la carta
        }
        
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
        System.out.println(conf);
        return conf;
 
    }
    
    private boolean jugarCartaMesa(Carta cardSelect, ArrayList<Carta> mano, HBox contenedor){
            boolean confM = false;
            System.out.println("Juega carta a la mesa");
            //eliminar carta del mazo del jugador
            mano.remove(cardSelect);
            cardSelect.getImgcart().setEffect(null);
            contenedor.getChildren().remove(cardSelect.getImgcart());
            containerCardsMesa.getChildren().add(cardSelect.getImgcart());
            //eliminar carta de la mesa
            mesa.add(cardSelect);
            //si esto se termina exitosamente, se retornara booleano de exito
            
            confM = true;
            
        
        return confM;
 
    }
    
   
    private void clickCarta(ImageView iv,Carta c, String fxid){
        if(iv.getParent().getId().equals(containerplayer.getId())){
            cardSelect = c;
            System.out.println("Carta seleccionada: " + c.getValor());
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setSaturation(0.8);
            iv.setEffect(colorAdjust);
        }
        else if(iv.getParent().getId().equals(containerCardsMesa.getId()) && cardSelect != null){
            cardSelect2 = c;
            ColorAdjust colorAdjust = new ColorAdjust();
                        colorAdjust.setSaturation(0.8);
                         iv.setEffect(colorAdjust);
                        boolean conf = jugarCartaPila(cardSelect, cardSelect2, manoJ, pilaJ);
                        System.out.println("Se jugo carta");
                        if(conf){
                            //sacar la carta del contenedor de mesa
                            iv.setEffect(null);
                            containerCardsMesa.getChildren().remove(iv);
                            // sacar la carta de la mano del jugador
                            containerplayer.getChildren().remove(cardSelect.getImgcart());
                            stackCartasJ.getChildren().add(cardSelect.getImgcart());
                            stackCartasJ.getChildren().add(iv);
                            cardSelect = null;
                            cardSelect2 = null;
                            turnoComputadora(manoC,pilaC,mesa,containerCPU);
                        
                        }
                        else{
                        cardSelect.getImgcart().setEffect(null);
                        iv.setEffect(null);
                        cardSelect = null;
                        cardSelect2 = null;
                        Alert alert = new Alert(Alert.AlertType.ERROR);

                      alert.setTitle("Error!");
                       alert.setHeaderText("Notificacion");
                       alert.setContentText("No puede jugar carta por que no coincide con el valor. ");
                        alert.showAndWait();
                        cantErr++;
                        errores.setText("Errores: " + cantErr);
                        
                    } 
                    
        }
        else if( cardSelect != null &&(iv.getParent().getId().equals(stackCartasJ.getParent().getId()) || iv.getParent().getId().equals(stackCartasC.getParent().getId()))){
            cardSelect2 = c;
            if(cardSelect.getValor().equals(cardSelect2.getValor())){
                boolean llamada = (cardSelect.getImgcart().getParent().getId().equals(containerplayer.getId()));
                robaPila( c, pilaJ, pilaC, stackCartasJ, stackCartasC, llamada);
            }
        }
        
        
        
        
        System.out.println(c.toString() + " " +  iv.getParent().getId());
    }
    
    private void generarVistaCarta(ImageView iv,Carta c, String tipo, boolean cubierta ){
        //cargar la foto
        try{
            String rutaimg;
        if(cubierta){
            rutaimg =URLDecoder.decode(App.class.getResource("/" + tipo + "/" + "back.png").getPath(),"UTF-8");
        
        }
        else{
           rutaimg =URLDecoder.decode(App.class.getResource("/" + tipo + "/" + c.getRutaimg()).getPath(),"UTF-8");
        }
        FileInputStream ins = new FileInputStream(rutaimg);
        Image imcard = new Image(ins, 80,100,false,false);
        iv.setImage(imcard);
        c.setImgcart(iv);
        
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
            
        }
        
    }
    
    private void turnoComputadora(ArrayList<Carta> mano, ArrayList<Carta> pila, ArrayList<Carta> mesa, HBox contenedor){
        boolean confirmacionTurno = true;
        boolean confirmacionPila = false;
        //si la carta aleatoria seleccionada por la maquina se juega a la pila
//Tiene que jugar carta a Para aumentar su pila
        do{
            //crear random
            Random rand = new Random();
            //selecciona carta a robar
            for(Carta c: mano){
                for(Carta c2: mesa){
                    if(c.getValor().equals(c2.getValor()) && cardSelect == null){
                        cardSelect = c;
                        cardSelect2 = c2;
                        
                    }
                    
                }
            }
            if(cardSelect == null){
            Carta c = mano.get(rand.nextInt(mano.size()));
            //selecciona carta aleatoria de mesa
            Carta c2 = mesa.get(rand.nextInt(mesa.size()));
            cardSelect = c;
            cardSelect2 = c2;
            jugarCartaMesa(cardSelect,mano,contenedor);
            generarVistaCarta(cardSelect.getImgcart(),cardSelect,"Poker", false);
            confirmacionTurno = false;
            }
            confirmacionPila = jugarCartaPila(cardSelect,cardSelect2,mano,pila);
            if (confirmacionPila){
                containerCardsMesa.getChildren().remove(cardSelect.getImgcart());
                // sacar la carta de la mano del jugador
                containerCPU.getChildren().remove(cardSelect.getImgcart());
                generarVistaCarta(cardSelect.getImgcart(),cardSelect,"Poker", false);
                stackCartasC.getChildren().add(cardSelect2.getImgcart());
                stackCartasC.getChildren().add(cardSelect.getImgcart());
                
                cardSelect = null;
                cardSelect2 = null;
                confirmacionTurno = false;
            }
            //setear imagen boca arriba en el iv de la carta a jugar de la maq
            
            
        }while(confirmacionTurno);
        cardSelect = null;
        cardSelect2 = null;
 
    }
    
    private void iniciarPartida(){
        containerplayer.getChildren().clear();
        containerCPU.getChildren().clear();
        containerCardsMesa.getChildren().clear();
        stackCartasMazo.getChildren().clear();
        stackCartasJ.getChildren().clear();
        stackCartasC.getChildren().clear();
        
        jugador.setText("Jugador: " + nombreJugador);
        errores.setText("Errores: " + cantErr);
        mazo = Carta.obtenerCartas(App.rutaArchivoPoker);
        Partida.barajarMazo(mazo);
         for(int i=0; i<4;i++){ //repartir cartas de mesa
            ImageView iv = new ImageView();
            
            try{
               Carta  c = mazo.get(i);
               generarVistaCarta(iv,c,"Poker", false);
                mesa.add(c);
                mazo.remove(i);
                
                iv.setOnMouseClicked(ev -> {
                    
                    if (cardSelect2 != null){
                        cardSelect2.getImgcart().setEffect(null);
                    }
                    cardSelect2= c;
                    clickCarta(cardSelect2.getImgcart(),cardSelect2, "a");
                    
                    System.out.println("Carta seleccionada: " + c.getValor());
                    
                    
                });
                
                
                
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
            }
            
            containerCardsMesa.getChildren().add(iv);
            
        }
         
        for (Carta c: mazo){ //anadir al stackpane
            ImageView iv = new ImageView();
            
            try{
                String rutaimg = URLDecoder.decode(App.class.getResource("/Cubierta/" + "back1.png").getPath(),"UTF-8");
                FileInputStream ins = new FileInputStream(rutaimg);
                Image imcard = new Image(ins, 120,150,false,false);
                iv.setImage(imcard);
                
                stackCartasMazo.setOnMouseClicked(ev -> {
                if(manoJ.size() == 0 && manoC.size() ==0 && mazo.size() != 0){
                    repartirMesa();
        }
                
            });
                
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
            
            
            
            stackCartasMazo.getChildren().add(iv);
            
        }
        repartirMesa();
        
    }
    
   
    
    private void repartirMesa(){
   
        if(App.cartaSeleccion == 1){ //poker
            
        
        for(int i=0; i<3;i++){ //repartir cartas de jugador
            System.out.println("Creando imagen....");
            ImageView ivJ = new ImageView(); //imageview
            
            try{
                Carta c = mazo.get(i);
                generarVistaCarta(ivJ,c,"Poker", false);
                manoJ.add(c);
                mazo.remove(i);
                
                
                ivJ.setOnMouseClicked(ev -> {
                    if (cardSelect != null){
                        cardSelect.getImgcart().setEffect(null);
                    }
                    
                    clickCarta(c.getImgcart(),c, "a");
                    /*
                    System.out.println("Carta seleccionada: " + c.getValor());
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setSaturation(0.8);
                    ivJ.setEffect(colorAdjust);
                    */
                });
                
                cartaMesa.setOnMouseClicked(ev ->{
                    
                    if(cardSelect != null){ //borre una variable global que ya no se usa, si se cae es culpa de ella
                        jugarCartaMesa(cardSelect, manoJ, containerplayer);
                        cardSelect = null;
                        turnoComputadora(manoC,pilaC,mesa,containerCPU);
                    }

                    else{
                        cardSelect = null;
                        
                    }

                });
                
                stackCartasJ.setOnMouseClicked(ev ->{
                    clickCarta(c.getImgcart(),c, "a");

                });
                
                
                
                
                
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
            }
            
            containerplayer.getChildren().add(ivJ);
            
        }
            
            for(int z=0; z<3;z++){ //repartir cartas de cpu
            
            ImageView ivc = new ImageView();
            
            try{
                Carta c = mazo.get(z);
                generarVistaCarta(ivc,c,"Poker", true);
                manoC.add(c);
                mazo.remove(z);
                
                
                ivc.setOnMouseClicked(ev -> {
                    
                    clickCarta(c.getImgcart(),c, "a");
           
                });
                
                
                
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
            }
            
            containerCPU.getChildren().add(ivc);
            
            
            
            
        }
            
          
            
        }
        
        if(App.cartaSeleccion == 0){ //espaniolas
            
        ArrayList<Carta> mazo = Carta.obtenerCartas(App.rutaArchivoSpain);
        Partida.barajarMazo(mazo);
        
        for(int i=0; i<3;i++){ //repartir cartas de jugador
            System.out.println("Creando imagen....");
            ImageView iv = new ImageView();
            
            try{
                String rutaimg =URLDecoder.decode(App.class.getResource("/Spain/" + mazo.get(i).getRutaimg()).getPath(),"UTF-8");
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
                String rutaimg = URLDecoder.decode(App.class.getResource("/Cubierta/" + "back2.png").getPath(),"UTF-8");
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
                String rutaimg =URLDecoder.decode(App.class.getResource("/Spain/" + mazo.get(i).getRutaimg()).getPath(),"UTF-8");
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
                String rutaimg = URLDecoder.decode(App.class.getResource("/Cubierta/" + "back2.png").getPath(),"UTF-8");
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
    
    /*
    //@FXML
    private void guardarPartida() {
        ArrayList<Partida> listaPartidas = Partida.cargarInfo(App.rutaArchivoPart);//cargar la lista del archivo
     
        System.out.println("Guardando partida");
        Partida p = new Partida(Date fecha, nombreJugador, int puntos, int puntosOponente, int diferenciaPuntaje, String tiempoPartida, int pilasRobadas);
        listaPartidas.add(p);//agregar partida a la lista
        System.out.println("Nuevo Partida:" + p);
        
        //serializar la lista
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(App.rutaArchivoPart))){
          
            out.writeObject(listaPartidas);
            out.flush();
            

        } catch (IOException ex) {
            System.out.println("IOException:" + ex.getMessage());
        } 

    }
    
    
    */
    
}
