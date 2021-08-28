/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import Modelo.Jugador;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import juego.Carta;
//import clases.Jugador;


/**
 *
 * @author Usuario
 */
public class Partida implements Comparable<Partida>, Serializable{
    private Date fecha;
    private String nombreP;
    private int puntos;
    private int puntosOponente;
    private int diferenciaPuntaje;
    private String tiempoPartida;
    private int pilasRobadas;
    
    private Jugador jugador;

    
    public Partida(Date fecha, String nombreP, int puntos, int puntosOponente, int diferenciaPuntaje, String tiempoPartida, int pilasRobadas) {
        this.fecha = fecha;
        this.nombreP = nombreP;
        this.puntos = puntos;
        this.puntosOponente = puntosOponente;
        this.diferenciaPuntaje = diferenciaPuntaje;
        this.tiempoPartida = tiempoPartida;
        this.pilasRobadas = pilasRobadas;
    }
    
    public static void barajarMazo(ArrayList<Carta> mazoC){
        Collections.shuffle(mazoC);
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombreP() {
        return nombreP;
    }

    public void setNombreP(String nombreP) {
        this.nombreP = nombreP;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getPuntosOponente() {
        return puntosOponente;
    }

    public void setPuntosOponente(int puntosOponente) {
        this.puntosOponente = puntosOponente;
    }

    public int getDiferenciaPuntaje() {
        return diferenciaPuntaje;
    }

    public void setDiferenciaPuntaje(int diferenciaPuntaje) {
        this.diferenciaPuntaje = diferenciaPuntaje;
    }

    public String getTiempoPartida() {
        return tiempoPartida;
    }

    public void setTiempoPartida(String tiempoPartida) {
        this.tiempoPartida = tiempoPartida;
    }

    public int getPilasRobadas() {
        return pilasRobadas;
    }

    public void setPilasRobadas(int pilasRobadas) {
        this.pilasRobadas = pilasRobadas;
    }

    public int compareTo(Partida p){
        int r = this.fecha.compareTo(p.fecha);
        return r;
    }
    
    public static ArrayList<Partida> cargarInfo(String ruta) {
        ArrayList<Partida> listaInfo = new ArrayList<>();

       //leer la lista del archivo serializado
        try (ObjectInputStream oi = new ObjectInputStream(new FileInputStream(ruta))) {
            listaInfo = (ArrayList<Partida>) oi.readObject();
            // System.out.println(listaInfo);
        } catch (FileNotFoundException ex) {
            System.out.println("archivo no existe");
        } catch (IOException   ex) {
            System.out.println("error io:"+ex.getMessage());
        } catch (ClassNotFoundException  ex) {
            System.out.println("error class:"+ex.getMessage());
        } 
        return listaInfo;
    }
    
    
}
