/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit;

/**
 *
 * @author RAYANs
 */
public class Administradores extends Usuarios{

    public Administradores(String correo, String clave) {
    super(correo, clave);
    }
    
        
    @Override
     public String tipoUsuario() {
        return "Administrador";
    }
}
