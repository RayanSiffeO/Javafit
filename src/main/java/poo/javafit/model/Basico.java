/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit.model;

/**
 * Representa a un socio con tarifa de tipo Basico en el sistema JavaFit.
 * Los socios basicos pagan el precio estandard completo de las actividades,
 * sin aplicar ningun tipo de descuento especial en la matricula de las sesiones.
 *
 * @author RAYANs
 */
public class Basico extends Socios {

    /**
     * Crea un nuevo socio basico con toda su informacion personal y de pago.
     *
     * @param direccion La direccion de residencia del socio.
     * @param tarjeta   La tarjeta bancaria asociada para los cobros.
     * @param telefono  El numero de telefono de contacto.
     * @param nombre    El nombre completo del socio.
     * @param correo    El correo electronico que usa como identificador.
     * @param clave     La contraseña de acceso al sistema.
     */
    
    public Basico(String direccion, TarjetaBancaria tarjeta, int telefono, String nombre, String correo, String clave) {
        super(direccion, tarjeta, telefono, nombre, correo, clave);
    }
    /**
     * Calcula el precio final que debe pagar este socio por una actividad.
     * Al ser un socio de tipo basico, se devuelve el precio integro de la
     * sesion sin ningun descuento extra.
     *
     * @param actividad La actividad de la que se quiere comprobar el coste.
     * @return El precio total de la actividad sin rebajas.
     */
    @Override
    public double calcularPrecio(Actividades actividad) {
        return actividad.getPrecio(); 
    }
    /**
     * Devuelve una cadena de texto que identifica el rol y nombre de este usuario.
     *
     * @return El tipo de usuario concatenado con su nombre propio.
     */
    @Override
    public String tipoUsuario() {
        return "Socio basico -" + getNombre();
    }
}
