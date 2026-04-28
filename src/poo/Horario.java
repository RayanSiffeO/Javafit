/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit;
import java.time.LocalTime;
/**
 *
 * @author RAYANs
 */
public class Horario {
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
    
    
}
