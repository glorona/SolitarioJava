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
public class Jugador {
    private ArrayList<Carta> listaCartas;
    private ArrayList<Carta> pilaCartas;
    private int puntos;

    public Jugador(ArrayList<Carta> listaCartas, ArrayList<Carta> pilaCartas, int puntos) {
        this.listaCartas = listaCartas;
        this.pilaCartas = pilaCartas;
        this.puntos = puntos;
    }

    public ArrayList<Carta> getListaCartas() {
        return listaCartas;
    }

    public void setListaCartas(ArrayList<Carta> listaCartas) {
        this.listaCartas = listaCartas;
    }

    public ArrayList<Carta> getPilaCartas() {
        return pilaCartas;
    }

    public void setPilaCartas(ArrayList<Carta> pilaCartas) {
        this.pilaCartas = pilaCartas;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
    
}
