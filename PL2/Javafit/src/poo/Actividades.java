/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit;

import java.io.Serializable;

/**
 *
 * @author RAYANs
 */
public abstract class Actividades implements Serializable {
    
    private String nombre;
    private TipoActividad tipo;
    private Sala sala;
    private Horario horario;
    private String imagen;
    private Monitor monitor;

    public Actividades(String nombre, TipoActividad tipo, Sala sala, Horario horario, String imagen, Monitor monitor) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.sala = sala;
        this.horario = horario;
        this.imagen = imagen;
        this.monitor = monitor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoActividad getTipo() {
        return tipo;
    }

    public void setTipo(TipoActividad tipo) {
        this.tipo = tipo;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }
    
    public abstract String tipoCategoria();
    public abstract double getPrecio();
}
