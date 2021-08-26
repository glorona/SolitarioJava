/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import juego.Carta;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Humano extends Jugador {
    private String nombreH;

    public Humano(String nombreH, ArrayList<Carta> listaCartas, ArrayList<Carta> pilaCartas, int puntos) {
        super(listaCartas, pilaCartas, puntos);
        this.nombreH = nombreH;
    }

    public String getNombreH() {
        return nombreH;
    }

    public void setNombreH(String nombreH) {
        this.nombreH = nombreH;
    }
    
    
    
}
