/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;
//import clases.Carta;
import java.util.ArrayList;
/**
 *
 * @author Usuario
 */
public class Baraja {
    private ArrayList<Carta> cartas;
    private String tipo;

    public Baraja(ArrayList<Carta> cartas, String tipo) {
        this.cartas = cartas;
        this.tipo = tipo;
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(ArrayList<Carta> cartas) {
        this.cartas = cartas;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
}
