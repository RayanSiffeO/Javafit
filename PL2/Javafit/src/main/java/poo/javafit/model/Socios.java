/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit.model;

/**
 * * Clase abstracta que representa la base para todos los socios del gimnasio JavaFit.
 * Modula los datos personales, de contacto y de pago de los clientes, heredando
 * las credenciales de la clase {@link Usuarios}. Ademas, obliga a definir algoritmos
 * especificos para el calculo de tarifas en sus subclases.
 *
 * @author RAYANs
 */
public abstract class Socios extends Usuarios {

    private String direccion;
    private TarjetaBancaria tarjeta;
    private int telefono;
    private String nombre;
    
    /**
     * Crea un nuevo socio realizando validaciones de seguridad sobre sus datos obligatorios.
     *
     * @param direccion La direccion de residencia del socio.
     * @param tarjeta   La tarjeta bancaria asociada para los pagos.
     * @param telefono  El numero de telefono de contacto (debe tener exactamente 9 digitos).
     * @param nombre    El nombre completo del socio (no puede estar vacio).
     * @param correo    El correo electronico de identificacion para el acceso.
     * @param clave     La contraseña de acceso al sistema.
     * @throws IllegalArgumentException si el nombre esta vacio o si el telefono no posee 9 digitos.
     */
    
    public Socios(String direccion, TarjetaBancaria tarjeta, int telefono, String nombre, String correo, String clave) {
        super(correo, clave);
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (String.valueOf(telefono).length() != 9) {
            throw new IllegalArgumentException("El teléfono debe tener exactamente 9 dígitos.");
        }
        
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

    public int getTelefono() {
        return telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public TarjetaBancaria getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(TarjetaBancaria tarjeta) {
        this.tarjeta = tarjeta;
    }
    /**
     * Metodo abstracto destinado a calcular el coste de una actividad segun la tarifa del socio.
     *
     * @param actividad La actividad de la cual se quiere evaluar el coste.
     * @return El precio final neto calculado para dicha sesion.
     */
    public abstract double calcularPrecio(Actividades actividad);
    
    /**
     * Devuelve una cadena de texto que identifica de forma general el rol de este usuario.
     *
     * @return Una cadena fija con el valor {@code "Socio"}.
     */
    @Override
    public String tipoUsuario() {
        return "Socio";
    }
}
