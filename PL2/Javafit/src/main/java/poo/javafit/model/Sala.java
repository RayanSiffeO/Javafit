/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit.model;
import java.io.Serializable;
/**
 * Representa una sala fisica de entrenamiento dentro del gimnasio JavaFit.
 * Cada sala cuenta con un nombre identificativo y un limite maximo de aforo
 * permitido, el cual es utilizado por el sistema para validar la disponibilidad
 * de plazas durante el proceso de reserva de actividades.
 *
 * @author RAYANs
 */
public class Sala implements Serializable {
    
    private int maxaforo;
    private String nombre;

    /**
     * Crea una nueva sala de entrenamiento con su capacidad maxima y nombre.
     *
     * @param maxaforo La capacidad limite de usuarios para la sala.
     * @param nombre   El nombre o codigo de identificacion del espacio.
     */
    
    public Sala(int maxaforo, String nombre) {
        this.maxaforo = maxaforo;
        this.nombre = nombre;
    }

    public int getMaxaforo() {
        return maxaforo;
    }

    public void setMaxaforo(int maxaforo) {
        this.maxaforo = maxaforo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
