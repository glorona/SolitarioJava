/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javafx.scene.image.ImageView;

/**
 *
 * @author Usuario
 */
public class Carta {
    private String palo;
    private String valor;
    private boolean seleccion;
    private String rutaimg;
    private ImageView imgcart;

    public ImageView getImgcart() {
        return imgcart;
    }

    public void setImgcart(ImageView imgcart) {
        this.imgcart = imgcart;
    }

    
    
    public String getRutaimg() {
        return rutaimg;
    }

    public void setRutaimg(String rutaimg) {
        this.rutaimg = rutaimg;
    }

    
    public String getPalo() {
        return palo;
    }

    public void setPalo(String palo) {
        this.palo = palo;
    }

    public boolean isSeleccion() {
        return seleccion;
    }

    public void setSeleccion(boolean seleccion) {
        this.seleccion = seleccion;
    }
    
    
@Override
        public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Carta other = (Carta) obj;
        if (!this.valor.equals(other.valor)) {
            return false;
        }
        return true;
    }
        
     public static ArrayList<Carta> obtenerCartas(String ruta){
         ArrayList<Carta> mazo = new ArrayList<>();
         try(InputStream input = Carta.class.getClassLoader().getResourceAsStream(ruta)){
             BufferedReader lector = new BufferedReader(new InputStreamReader(input));
             String linea = null;
             while((linea = lector.readLine())!= null){
                 String[] datos = linea.split(",");
                 String value = datos[0];
                 String palo = datos[1];
                 String rutaimg =  datos[2];
                 mazo.add(new Carta(value,palo,rutaimg));
                 
             }
         }
         catch(IOException ex){
             System.out.println(ex.getMessage());
             
         }
         return mazo;
     }
     
    
    

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }


    public Carta( String valor, String palo, String rutaimg) {
        this.palo = palo;
        this.valor = valor;
        this.rutaimg = rutaimg;
        this.seleccion = false;
    }

    @Override
    public String toString() {
        return "Carta{" + "palo=" + palo + ", valor=" + valor + '}';
    }
    
    
    
    
}
