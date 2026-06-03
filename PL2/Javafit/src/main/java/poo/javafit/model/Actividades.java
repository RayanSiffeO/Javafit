/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit.model;

import java.io.Serializable;

/**
 * Clase abstracta base que representa una actividad deportiva en el gimnasio
 * JavaFit.
 * <p>
 * Define los componentes estructurales de cualquier clase o sesión (sala,
 * horario, monitor) y obliga a las subclases a especificar sus políticas de
 * precios y categorías.
 *
 * @author RAYANs
 */
public abstract class Actividades implements Serializable {

    /**
     * El nombre descriptivo de la actividad.
     */
    private String nombre;
    /**
     * El tipo o modalidad deportiva de la actividad.
     */
    private TipoActividad tipo;
    /**
     * La sala del gimnasio asignada para la sesión.
     */
    private Sala sala;
    /**
     * El día y rango de horas en el que se imparte.
     */
    private Horario horario;
    /**
     * La ruta o nombre del archivo de imagen publicitaria asociada.
     */
    private String imagen;
    /**
     * El monitor o entrenador encargado de dirigir la sesión.
     */
    private Monitor monitor;

    /**
     * Crea una nueva instancia de una actividad con toda su configuración
     * inicial.
     *
     * @param nombre El nombre de la actividad.
     * @param tipo El tipo de modalidad deportiva.
     * @param sala La sala donde se realiza.
     * @param horario El horario asignado.
     * @param imagen La ruta de la imagen promocional.
     * @param monitor El monitor responsable.
     */
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

    /**
     * Devuelve el coste base de la actividad antes de aplicar descuentos por
     * socio.
     *
     * @return El precio base de la sesión como un valor numérico continuo.
     */
    public abstract double getPrecio();

    /**
     * Devuelve la categoría o clasificación específica del tipo de actividad.
     *
     * @return Un {@link String} que describe la categoría (ej. "Normal",
     * "Especial").
     */
    public abstract String tipoCategoria();
}
