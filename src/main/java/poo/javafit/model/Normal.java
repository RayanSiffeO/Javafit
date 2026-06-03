/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit.model;

/**
 * Representa una actividad de categoria Normal en el gimnasio JavaFit.
 * Estas sesiones forman parte del catalogo estandar del gimnasio y se
 * diferencian de las especiales en que no tienen un coste extra directo,
 * devolviendo un precio base de cero ya que se incluyen en las tarifas.
 *
 * @author RAYANs
 */
public class Normal extends Actividades {
    
    /**
     * Crea una nueva actividad de tipo normal compartiendo sus datos basicos con la clase padre.
     *
     * @param nombre  El nombre explicativo de la actividad.
     * @param tipo    El tipo de modalidad deportiva a la que pertenece.
     * @param sala    La sala del centro asignada para la sesion.
     * @param horario El horario y dia de la semana establecido.
     * @param imagen  La ruta del archivo de imagen para la interfaz grafica.
     * @param monitor El monitor encargado de impartir la clase.
     */
    
    public Normal(String nombre, TipoActividad tipo, Sala sala, Horario horario, String imagen, Monitor monitor) {
        super(nombre, tipo, sala, horario, imagen, monitor);
    }
    
    /**
     * Obtiene el precio base fijado para las actividades de tipo normal.
     *
     * @return El valor numerico continuo fijo {@code 0.0}.
     */
    
    @Override
    public double getPrecio() {
        return 0.0;
    }
/**
     * Devuelve la categoria especifica a la que pertenece esta actividad.
     *
     * @return Una cadena de texto con el valor {@code "Normal"}.
     */
    @Override
    public String tipoCategoria() {
        return "Normal";
    }
}
