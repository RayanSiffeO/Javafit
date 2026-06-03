/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit;

/**
 *
 * @author RAYANs
 */
public abstract class Socios extends Usuarios {
    private String direccion;
    private String tarjeta;
    private int telefono;
    private String nombre;

    public Socios(String nombre, String direccion, String tarjeta, int telefono, String correo, String clave) {
        super(correo, clave);
        this.direccion = direccion;
        this.tarjeta = tarjeta;
        this.telefono = telefono;
        this.nombre = nombre;
    }

     public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
       
    public abstract double calcularPrecio(Actividades actividad);
    
    @Override
     public String tipoUsuario() {
        return "Socio";
    }
}
