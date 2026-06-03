/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit.model;

import java.io.Serializable;

/**
 *
 * @author RAYANs
 */
public class Administradores extends Usuarios implements Serializable {

    /**
     * Identificador para el control de versiones de la serializacion.
     */
    private static final long serialVersionUID = 2L;

    /**
     * Crea un nuevo administrador con su correo y clave de acceso
     * correspondientes.
     *
     * @param correo El correo electronico de acceso del administrador.
     * @param clave La contraseña de seguridad para el inicio de sesion.
     */
    public Administradores(String correo, String clave) {
        super(correo, clave);
    }

    /**
     * Devuelve el tipo especifico de este usuario.
     *
     * @return Una cadena de texto fija con el valor {@code "Administrador"}.
     */

    @Override
    public String tipoUsuario() {
        return "Administrador";
    }
}
