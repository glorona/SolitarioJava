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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLDecoder;
import java.time.LocalDate;
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



public class PartidaController implements Initializable, Serializable {
    
    
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
    
    public static String nombreJugador;
    
    private ArrayList<Carta> mazo;
    
    private ArrayList<Carta> manoJ;
    
    private ArrayList<Carta> manoC;
    
    private ArrayList<Carta> mesa;
    
    private ArrayList<Carta> pilaJ;
    
    private ArrayList<Carta> pilaC;
    

    private int cantErr;
    
    private String ruta;
    
    
    public static int pilasRobadas; //se asume que es un valor por los dos
    
    public static int puntosJ;
    
    public static int puntosC;
    
    public static int diferencia;
    
    
    
    
    

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
            System.out.println("carta robapila" + c.toString());
            pilaJ.addAll(pilaC); //robo todo digitalmente
            sj.getChildren().addAll(sc.getChildren()); //me llevo toda la pila
            sc.getChildren().clear(); //chao pila de rival
            sj.getChildren().add(cardSelect.getImgcart()); //agrego la ultima carta
            containerplayer.getChildren().remove(cardSelect.getImgcart()); //la saco de la mano del que juega la carta
            manoJ.remove(cardSelect);
            pilasRobadas++;

        }
        else{
            
            System.out.println("carta robapila" + c.toString());
            pilaC.addAll(pilaJ); //robo todo digitalmente
            sc.getChildren().addAll(sj.getChildren()); //me llevo toda la pila
            sj.getChildren().clear(); //chao pila de rival
            sc.getChildren().add(cardSelect.getImgcart()); //agrego la ultima carta
            containerCPU.getChildren().remove(cardSelect.getImgcart()); //la saco de la mano del que juega la carta
            manoC.remove(cardSelect);
            pilasRobadas++;
            
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
    
    private void calcularPuntos(ArrayList<Carta> pilaJug, ArrayList<Carta> pilaCom){
        puntosC = pilaCom.size();
        puntosJ = pilaJug.size();
        diferencia = puntosJ - puntosC;
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
        else if( cardSelect != null &&(iv.getParent().getId().equals(stackCartasJ.getId()) || iv.getParent().getId().equals(stackCartasC.getId()))){
            cardSelect2 = c;
            System.out.println("Roba pila");
            cardSelect.getImgcart().setEffect(null);
            cardSelect2.getImgcart().setEffect(null);
            if(cardSelect.getValor().equals(cardSelect2.getValor())){
                boolean llamada = (cardSelect.getImgcart().getParent().getId().equals(containerplayer.getId()));
                System.out.println("Se hace llamada a robar pila");
                robaPila( c, pilaJ, pilaC, stackCartasJ, stackCartasC, llamada);
                
                cardSelect = null;
                cardSelect2 = null;
                turnoComputadora(manoC,pilaC,mesa,containerCPU);
                
            }
        }
        
        
        
        
        System.out.println(c.toString() + " " +  iv.getParent().getId());
    }
    
    private void generarVistaCarta(ImageView iv,Carta c, String tipo, boolean cubierta ){
        if(App.cartaSeleccion == 1){
            tipo = "Poker";
        }
        else{
            tipo = "Spain";
        }
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
        boolean confirmarRobo = false;
        Carta cr = null;
        //si la carta aleatoria seleccionada por la maquina se juega a la pila
//Tiene que jugar carta a Para aumentar su pila
        do{
            //crear random
            Random rand = new Random();
            //buscar carta encima de la pila del jugador
            if(pilaJ.size() != 0){
                cr = pilaJ.get(pilaJ.size() - 1); //carta encima de la pila jugador
                System.out.println(cr.toString());
   
            }
            
            //selecciona carta a robar
            for(Carta c: mano){
                if(cr != null){
                if(cr.getValor().equals(c.getValor())){
                    cardSelect = c;
                    cardSelect2 = cr;
                    System.out.println("CARTA COMPUTADORA AL ROBO" + cardSelect.toString());
                    System.out.println("CARTA COMPUTADORA 2 AL ROBO" + cardSelect2.toString());
                    confirmarRobo = true;
                    

                }
                }
                for(Carta c2: mesa){
                    if(c.getValor().equals(c2.getValor()) && cardSelect == null){
                        cardSelect = c;
                        cardSelect2 = c2;
                        System.out.println("CARTA COMPUTADORA A LA PILA" + cardSelect.toString());
                        System.out.println("CARTA COMPUTADORA 2 A LA PILA" + cardSelect2.toString());
                        
                    }
                    
                }
                
            }
            
            if(cardSelect == null){ //jugar cualquier cosa a la mesa
            Carta c = mano.get(rand.nextInt(mano.size()));
            //selecciona carta aleatoria de mesa
            //Carta c2 = mesa.get(rand.nextInt(mesa.size()));
            cardSelect = c;
            //cardSelect2 = c2;
            jugarCartaMesa(cardSelect,mano,contenedor);
            generarVistaCarta(cardSelect.getImgcart(),cardSelect,"Poker", false);
            confirmacionTurno = false;
            cardSelect = null;
            cardSelect2 = null;
            }
            else if(confirmarRobo){ //robando pila
                 generarVistaCarta(cardSelect.getImgcart(),cardSelect,"Poker", false);
                 robaPila( cardSelect2, pilaJ, pilaC, stackCartasJ, stackCartasC, false);
                cardSelect = null;
                cardSelect2 = null;
                confirmacionTurno = false;
                
                
            }
            else{ //robar mesa
                confirmacionPila = jugarCartaPila(cardSelect,cardSelect2,mano,pila);
                
            if (confirmacionPila){
                System.out.println("CARTA COMPUTADORA A LA PILA" + cardSelect.toString());
                System.out.println("CARTA COMPUTADORA 2 A LA PILA" + cardSelect2.toString());
                generarVistaCarta(cardSelect.getImgcart(),cardSelect,"Poker", false);
                containerCardsMesa.getChildren().remove(cardSelect2.getImgcart());
                // sacar la carta de la mano del jugador
                containerCPU.getChildren().remove(cardSelect.getImgcart());
                stackCartasC.getChildren().addAll(cardSelect2.getImgcart(), cardSelect.getImgcart());
                cardSelect = null;
                cardSelect2 = null;
                confirmacionTurno = false;
            }
                
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
        //genera las rutas 
        try{
            if(App.cartaSeleccion == 1){
                ruta = URLDecoder.decode(App.class.getResource(App.rutaArchivoPoker).getPath(),"UTF-8");
                System.out.println(ruta);
            }
            else{
                ruta = URLDecoder.decode(App.class.getResource(App.rutaArchivoSpain).getPath(),"UTF-8");
                System.out.println(ruta);
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            
        }
        mazo = Carta.obtenerCartas(ruta);
        Partida.barajarMazo(mazo);
         for(int i=0; i<4;i++){ //repartir cartas de mesa
            ImageView iv = new ImageView();
            
            try{
               Carta  c = mazo.get(0);
               generarVistaCarta(iv,c,"Poker", false);
                mesa.add(c);
                mazo.remove(0);
                
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
                generarVistaCarta(iv,c,"Poker", true);
                
                stackCartasMazo.setOnMouseClicked(ev -> {
                   
                    
                if(manoJ.size() == 0 && manoC.size() ==0 && mazo.size() != 0){
                    repartirMesa();
                    System.out.println("Cartas restantes: " + mazo.size());
        }
                else if(mazo.size() == 0 && manoJ.size() == 0 && manoC.size() ==0 ){
                    try{
                        System.out.println("Fin de la partida!");
                        //calcularPuntos
                        calcularPuntos(pilaJ,pilaC);
                        //llamar al metodo escribirpartida
                        guardarPartida();
                    App.cambioScene("FinPartida");
                    }
                    catch (IOException ex ){
                        System.out.println(ex.getMessage());
                    }
                }
                
            });
                
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
            }
            
            
            
            stackCartasMazo.getChildren().add(iv);
            
        }
        repartirMesa();
        
    }
    
   
    
    private void repartirMesa(){
   
            
        for(int i=0; i<3;i++){ //repartir cartas de jugador
            System.out.println("Creando imagen....");
            ImageView ivJ = new ImageView(); //imageview
            
            try{
                Carta c = mazo.get(0);
                generarVistaCarta(ivJ,c,"Poker", false);
                manoJ.add(c);
                mazo.remove(0);
                
                
                ivJ.setOnMouseClicked(ev -> {
                    if (cardSelect != null){
                        cardSelect.getImgcart().setEffect(null);
                    }
                    
                    clickCarta(c.getImgcart(),c, "a");

                });
                
                cartaMesa.setOnMouseClicked(ev ->{
                    
                    if(cardSelect != null){ //borre una variable global que ya no se usa, si se cae es culpa de ella
                        jugarCartaMesa(cardSelect, manoJ, containerplayer);
                        cardSelect = null;
                        cardSelect2 = null;
                        turnoComputadora(manoC,pilaC,mesa,containerCPU);
                    }

                    else{
                        cardSelect = null;
                        cardSelect2 = null;
                    }

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
                Carta c = mazo.get(0);
                generarVistaCarta(ivc,c,"Poker", true);
                manoC.add(c);
                mazo.remove(0);
                
                
                ivc.setOnMouseClicked(ev -> {
                    
                    clickCarta(c.getImgcart(),c, "a");
           
                });
                
                
                
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
            }
            
            containerCPU.getChildren().add(ivc);
            
            
            
            
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

        public int getSec() {
            return sec;
        }

        public int getMin() {
            return min;
        }

        @Override
        public String toString() {
            return  min + ":" + sec;
        }
    
    
    }
    

    //@FXML
    private void guardarPartida() {
        ArrayList<Partida> listaPartidas = Partida.cargarInfo(App.rutaArchivoPart);//cargar la lista del archivo
         java.util.Date date=new java.util.Date();  
        System.out.println("Guardando partida");
        Partida p = new Partida(date, nombreJugador, puntosJ, puntosC, diferencia, time.toString(), pilasRobadas);
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
    
    

    
}
