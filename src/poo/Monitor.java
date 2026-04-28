/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit;
import java.util.ArrayList;
/**
 *
 * @author RAYANs
 */
public class Monitor {
    
    private String nombre;
    private TipoActividad especialidad;
    private ArrayList<Horario> disponibilidad;
    
    public Monitor(String nombre, TipoActividad especialidad, ArrayList<Horario> disponibilidad) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.disponibilidad = disponibilidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoActividad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(TipoActividad especialidad) {
        this.especialidad = especialidad;
    }

    public ArrayList<Horario> getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(ArrayList<Horario> disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
    
}
