/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit;

/**
 *
 * @author RAYANs
 */
public class Especial extends Actividades {
    
    private double precio;
    private String descripcion;
    
     public Especial(String nombre,TipoActividad tipo,Sala sala,Horario horario,Monitor monitor, String imagen, double precio, String descripcion) {
        super(nombre, tipo, sala, horario, imagen, monitor);

        this.precio = precio;
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }
     
     
    
    @Override
     public String tipoCategoria() {
        return "Especial";
    }
}
