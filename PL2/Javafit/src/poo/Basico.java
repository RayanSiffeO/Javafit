/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit;

/**
 *
 * @author RAYANs
 */
public class Basico extends Socios {

    public Basico(String nombre, String direccion, String tarjeta, int telefono, String correo, String clave) {
        super(nombre, direccion, tarjeta, telefono, correo, clave);
    }

    @Override
    public double calcularPrecio(Actividades actividad) {
        return actividad.getPrecio(); 
    }
    
    @Override
    public String tipoUsuario() {
        return "Socio basico -" + getNombre();
    }
}
