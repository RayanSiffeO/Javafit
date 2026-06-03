/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit.model;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * Representa a un monitor o entrenador deportivo del gimnasio JavaFit.
 * Almacena la informacion basica del profesional, su area de especializacion
 * dentro de las modalidades deportivas y el conjunto de franjas horarias en
 * las que se encuentra disponible para impartir las clases.
 *
 * @author RAYANs
 */
public class Monitor implements Serializable {
    
    private String nombre;
    private TipoActividad especialidad;
    private ArrayList<Horario> disponibilidad;
    
    /**
     * Crea una nueva instancia de un monitor con sus datos y horarios de trabajo.
     *
     * @param nombre         El nombre completo del entrenador.
     * @param especialidad   El tipo de actividad en el que esta especializado.
     * @param disponibilidad La lista de horarios asignados o disponibles para trabajar.
     */
    
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
