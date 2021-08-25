/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.Date;
//import clases.Jugador;


/**
 *
 * @author Usuario
 */
public class Partida implements Comparable<Partida>{
    private Date fecha;
    private String nombreP;
    private int puntos;
    private int puntosOponente;
    private int diferenciaPuntaje;
    private String tiempoPartida;
    private int pilasRobadas;
    
    private Jugador jugador;

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

    public Partida(Date fecha, String nombreP, int puntos, int puntosOponente, int diferenciaPuntaje, String tiempoPartida, int pilasRobadas) {
        this.fecha = fecha;
        this.nombreP = nombreP;
        this.puntos = puntos;
        this.puntosOponente = puntosOponente;
        this.diferenciaPuntaje = diferenciaPuntaje;
        this.tiempoPartida = tiempoPartida;
        this.pilasRobadas = pilasRobadas;
    }
    
    public int compareTo(Partida p){
        int r = this.fecha.compareTo(p.fecha);
        return r;
    }
    
    
    
}
