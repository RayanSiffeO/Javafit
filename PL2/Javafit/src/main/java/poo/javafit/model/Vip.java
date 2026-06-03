/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit.model;

/**
* Representa a un socio con tarifa de tipo Vip en el sistema JavaFit.
 * Los socios vip cuentan con beneficios comerciales exclusivos, aplicandose
 * de forma automatica un descuento directo del diez por ciento sobre el coste
 * total de cualquier actividad especial en la que se inscriban.
 *
 * @author RAYANs
 */
public class Vip extends Socios {

    /**
     * Crea un nuevo socio vip con toda su informacion personal, de contacto y de facturacion.
     *
     * @param direccion La direccion de residencia del socio.
     * @param tarjeta   La tarjeta bancaria asociada para los cobros.
     * @param telefono  El numero de telefono de contacto.
     * @param nombre    El nombre completo del socio.
     * @param correo    El correo electronico que usa como identificador.
     * @param clave     La contraseña de acceso al sistema.
     */
    
    public Vip(String direccion, TarjetaBancaria tarjeta, int telefono, String nombre, String correo, String clave) {
        super(direccion, tarjeta, telefono, nombre, correo, clave);
    }
    /**
     * Calcula el precio final con descuento que debe abonar este socio por una actividad
     * Al tratarse de un socio de categoria vip, el sistema descuenta un diez por ciento
     *
     * @param actividad La actividad de la que se quiere comprobar el coste.
     * @return El precio de la actividad tras aplicarle la rebaja correspondiente por nivel vip.
     */
    @Override
    public double calcularPrecio(Actividades actividad) {
        return actividad.getPrecio() * 0.9;
    }
    /**
     * Devuelve una cadena de texto que identifica el rol vip y el nombre de este usuario.
     *
     * @return El tipo de usuario socio vip concatenado con su nombre propio.
     */
    @Override
    public String tipoUsuario() {
        return "Socio VIP -" + getNombre();
    }
    
}
