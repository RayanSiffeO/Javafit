/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit;

import java.time.LocalDate;
import java.io.Serializable;
/**
 *
 * @author RAYANs
 */
public class Reserva implements Serializable {

    private Socios socio;
    private Actividades actividad;
    private Horario horario;
    private double preciofinal;
    private LocalDate fecha;

    public Reserva(Socios socio, Actividades actividad, Horario horario, double preciofinal, LocalDate fecha) {
        this.socio = socio;
        this.actividad = actividad;
        this.horario = horario;
        this.preciofinal = preciofinal;
        this.fecha = LocalDate.now();
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
