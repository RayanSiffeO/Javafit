/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit.model;

import java.time.LocalDate;
import java.io.Serializable;
/**
 * Representa el registro de una reserva realizada dentro del sistema JavaFit.
 * Esta clase actua como una entidad de asociacion que vincula de forma directa
 * a un socio con la actividad que desea tomar, almacenando parametros clave
 * como el horario de la sesion, el coste final aplicado y la fecha del registro.
 *
 * @author RAYANs
 */
public class Reserva implements Serializable {

    /** Identificador unico para el control de versiones de la serializacion. */
    private static final long serialVersionUID = 1L;
    
    private Socios socio;
    private Actividades actividad;
    private Horario horario;
    private double preciofinal;
    private LocalDate fecha;

    /**
     * Crea una nueva reserva con todos los datos de la operacion e inscripcion.
     *
     * @param socio       El socio que se inscribe.
     * @param actividad   La actividad seleccionada.
     * @param horario     El horario de la sesion.
     * @param preciofinal El importe final calculado para la transaccion.
     * @param fecha       La fecha en la que se genera el registro.
     */
    
    public Reserva(Socios socio, Actividades actividad, Horario horario, double preciofinal, LocalDate fecha) {
        this.socio = socio;
        this.actividad = actividad;
        this.horario = horario;
        this.preciofinal = preciofinal;
        this.fecha = fecha;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Socios getSocio() {
        return socio;
    }

    public void setSocio(Socios socio) {
        this.socio = socio;
    }

    public Actividades getActividad() {
        return actividad;
    }

    public void setActividad(Actividades actividad) {
        this.actividad = actividad;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public double getPreciofinal() {
        return preciofinal;
    }
    
    public void setPreciofinal(double preciofinal) {
        this.preciofinal = preciofinal;
    }
    
}
