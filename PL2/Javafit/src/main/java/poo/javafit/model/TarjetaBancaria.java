/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit.model;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * Representa la informacion de una tarjeta de pago asociada a un socio en JavaFit.
 * Permite gestionar de manera segura los datos bancarios del cliente para la tramitacion
 * de cuotas e inscripciones, incluyendo metodos internos de comprobacion temporal
 * para determinar si el plastico sigue vigente.
 *
 * @author RAYANs
 */
public class TarjetaBancaria implements Serializable {

    private String tipoTarjeta;
    private long numero;
    private LocalDate fechaCaducidad;

    /**
     * Crea una nueva tarjeta bancaria con su tipo, numero y fecha de vencimiento.
     *
     * @param tipoTarjeta    La marca o red emisora de la tarjeta.
     * @param numero         Los digitos numericos impresos del plastico.
     * @param fechaCaducidad El mes y año de expiracion de la cuenta.
     */
    
    public TarjetaBancaria(String tipoTarjeta, long numero, LocalDate fechaCaducidad) {
        this.tipoTarjeta = tipoTarjeta;
        this.numero = numero;
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public boolean estaCaducada() {
        return fechaCaducidad.isBefore(LocalDate.now());
    }

    /**
     * Genera una cadena alfabetica con la informacion agrupada de la tarjeta.
     *
     * @return Un texto descriptivo con el tipo, la numeracion y la caducidad del elemento.
     */
    
    @Override
    public String toString() {
        return "Tipo= " + tipoTarjeta + " Numero= " + numero + " Caducidad= " + fechaCaducidad;
    }

}
