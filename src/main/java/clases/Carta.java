/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author Usuario
 */
public class Carta {
    private String nombre;
    private String palo;
    private int valor;
    private int rutaImg;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPalo() {
        return palo;
    }

    public void setPalo(String palo) {
        this.palo = palo;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getRutaImg() {
        return rutaImg;
    }

    public void setRutaImg(int rutaImg) {
        this.rutaImg = rutaImg;
    }

    public Carta(String nombre, String palo, int valor, int rutaImg) {
        this.nombre = nombre;
        this.palo = palo;
        this.valor = valor;
        this.rutaImg = rutaImg;
    }
    
    
}
