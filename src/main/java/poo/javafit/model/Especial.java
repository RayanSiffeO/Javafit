/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit.model;

/**
 * Representa una actividad de categoria Especial en el gimnasio JavaFit. Estas
 * actividades se diferencian de las normales porque tienen un coste fijo
 * especifico independiente de la tarifa base general y una descripcion
 * detallada de lo que se realiza en la sesion.
 *
 * @author RAYANs
 */
public class Especial extends Actividades {
    /** El precio especifico fijado para esta actividad especial. */
    private final double precio;
    /** Breve descripcion con el enfoque o los detalles de la sesion. */
    private final String descripcion;
    /**
     * Crea una nueva actividad especial con sus caracteristicas, coste y detalles.
     *
     * @param nombre      El nombre de la actividad.
     * @param tipo        El tipo de modalidad deportiva.
     * @param sala        La sala donde se realiza.
     * @param horario     El horario asignado.
     * @param monitor     El monitor responsable.
     * @param imagen      La ruta de la imagen promocional.
     * @param precio      El coste fijo de la sesion.
     * @param descripcion Detalles explicativos sobre la actividad.
     */
    public Especial(String nombre, TipoActividad tipo, Sala sala, Horario horario, Monitor monitor, String imagen, double precio, String descripcion) {
        super(nombre, tipo, sala, horario, imagen, monitor);

        this.precio = precio;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el precio fijo configurado para esta actividad especial.
     *
     * @return El coste de la sesion como valor numerico continuo.
     */
    @Override
    public double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Devuelve la categoria especifica a la que pertenece esta actividad.
     *
     * @return Una cadena de texto fija con el valor {@code "Especial"}.
     */
    @Override
    public String tipoCategoria() {
        return "Especial";
    }
}
