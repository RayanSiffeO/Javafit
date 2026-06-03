/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit.model;

import java.io.Serializable;

/**
 * Representa la clase abstracta base para todos los usuarios del sistema
 * JavaFit. Esta clase define los atributos comunes de autenticación (correo
 * electrónico y clave) y obliga a las subclases a especificar su tipo de rol
 * mediante un método abstracto. Al implementar {@link Serializable}, permite
 * que el estado de los usuarios sea persistido en archivos de datos.
 *
 * @author RAYANs
 */
public abstract class Usuarios implements Serializable {

    /**
     * El correo electrónico del usuario, utilizado como identificador único.
     */
    private String correo;
    /**
     * La contraseña de acceso al sistema del usuario.
     */
    private String clave;

    /**
     * Crea una nueva instancia de un usuario con sus credenciales de acceso.
     *
     * @param correo El correo electrónico de identificación del usuario.
     * @param clave La contraseña asociada a la cuenta del usuario.
     */
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
    /**
     * Devuelve una cadena de texto que identifica el rol o tipo específico del usuario.
     * <p>
     * Este método debe ser implementado por las subclases concretas (por ejemplo, Socios,
     * Administradores, etc.) para definir el comportamiento de permisos según el rol.
     *
     * @return Un {@link String} que describe la categoría o tipo de usuario.
     */
    public abstract String tipoUsuario();

}
