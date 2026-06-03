/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit.model;
import java.time.LocalTime;
import java.io.Serializable;
/**
 * Representa la franja horaria y el dia en el que se realiza una actividad.
 * Se encarga de almacenar el dia de la semana, la hora de apertura y la hora
 * de cierre de la sesion, asegurando de forma interna que los rangos de tiempo
 * introducidos sean coherentes.
 *
 * @author RAYANs
 */
public class Horario implements Serializable {
    /**
     * Enumeracion interna para los dias de la semana.
     */
    public enum Dia {
    LUNES,
    MARTES,
    MIERCOLES,
    JUEVES,
    VIERNES,
    SABADO,
    DOMINGO
    }
    
    private Dia dia;
    private LocalTime inicio;
    private LocalTime fin;
    
    /**
     * Crea una nueva franja horaria controlando que las las horas coincidan.
     *
     * @param dia    El dia de la semana seleccionado.
     * @param inicio La hora de comienzo de la actividad.
     * @param fin    La hora de finalizacion de la actividad.
     * @throws IllegalArgumentException si la hora de fin es previa a la hora de inicio.
     */
    public Horario(Dia dia, LocalTime inicio, LocalTime fin) {
        
        if (fin.isBefore(inicio)) {
            throw new IllegalArgumentException("La hora fin no puede ser anterior a la de inicio");
        }
        
        this.dia = dia;
        this.inicio = inicio;
        this.fin = fin;
    }

    public Dia getDia() {
        return dia;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public LocalTime getFin() {
        return fin;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }

    public void setFin(LocalTime fin) {
        this.fin = fin;
    }
    
    /**
     * Devuelve una representacion legible del rango horario.
     *
     * @return Una cadena de texto con el formato de las horas de inicio y fin.
     */
    
    @Override
    public String toString() {
        return "Horario de " + inicio + " - " + fin;
    }
}
