/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit;

/**
 *
 * @author RAYANs
 */
public class Normal extends Actividades {

    public Normal(String nombre, TipoActividad tipo, Sala sala, Horario horario, String imagen, Monitor monitor) {
        super(nombre, tipo, sala, horario, imagen, monitor);
    }

    @Override
    public double getPrecio() {
        return 0.0;
    }

    @Override
    public String tipoCategoria() {
        return "Normal";
    }
}
