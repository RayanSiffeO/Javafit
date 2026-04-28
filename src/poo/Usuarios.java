/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit;

/**
 *
 * @author RAYANs
 */
public abstract class Usuarios {
    
    private String correo;
    private String clave;

    public Usuarios() {}
    public Usuarios(String correo, String clave) {
        this.correo = correo;
        this.clave = clave;
    }
    
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public abstract String tipoUsuario();
    
}
