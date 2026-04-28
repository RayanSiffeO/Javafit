/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit;

import java.util.ArrayList;
import java.io.*;
import java.time.LocalDate;

/**
 *
 * @author RAYANs
 */
public class GymManager {

    private ArrayList<Actividades> actividades;
    private ArrayList<Reserva> reservas;
    private ArrayList<Monitor> monitores;
    private ArrayList<Socios> socios;

    public GymManager(ArrayList<Actividades> actividades, ArrayList<Reserva> reservas, ArrayList<Monitor> monitores, ArrayList<Socios> socios) {
        this.actividades = actividades;
        this.reservas = reservas;
        this.monitores = monitores;
        this.socios = socios;
    }

    public void setActividades(ArrayList<Actividades> actividades) {
        this.actividades = actividades;
    }

    public void setReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }

    public ArrayList<Monitor> getMonitores() {
        return monitores;
    }

    public void setMonitores(ArrayList<Monitor> monitores) {
        this.monitores = monitores;
    }

    public ArrayList<Socios> getSocios() {
        return socios;
    }

    public void setSocios(ArrayList<Socios> socios) {
        this.socios = socios;
    }

    public GymManager() {
        actividades = new ArrayList<>();
        reservas = new ArrayList<>();
        monitores = new ArrayList<>();
        socios = new ArrayList<>();
    }

    public ArrayList<Actividades> buscarPorTipo(TipoActividad tipo) {

        ArrayList<Actividades> resultado = new ArrayList<>();

        for (Actividades a : actividades) {
            if (a.getTipo() == tipo) {
                resultado.add(a);
            }
        }
        return resultado;
    }

    public ArrayList<Actividades> buscarPorMonitor(Monitor m) {

        ArrayList<Actividades> resultado = new ArrayList<>();

        for (Actividades a : actividades) {
            if (a.getMonitor().equals(m)) {
                resultado.add(a);
            }
        }

        return resultado;
    }

    public ArrayList<Actividades> buscarPorDia(Horario.Dia dia) {

        ArrayList<Actividades> resultado = new ArrayList<>();

        for (Actividades a : actividades) {
            if (a.getHorario().getDia() == dia) {
                resultado.add(a);
            }
        }

        return resultado;
    }

    public Socios buscarSocioPorCorreo(String correo) {
        for (Socios s : socios) {
            if (s.getCorreo().equals(correo)) {
                return s;
            }
        }
        return null;
    }

    private boolean hayAforo(Actividades actividad) {

        int ocupadas = 0;

        for (Reserva r : reservas) {
            if (r.getActividad().equals(actividad)) {
                ocupadas++;
            }
        }

        return ocupadas < actividad.getSala().getMaxaforo();
    }

    public boolean reservar(Socios socio, Actividades actividad) {

        if (!hayAforo(actividad)) {
            return false;
        }

        if (actividad.getHorario() == null) {
            return false;
        }

        double precio = socio.calcularPrecio(actividad);

        Reserva r = new Reserva(socio, actividad, actividad.getHorario(), precio, LocalDate.now());

        reservas.add(r);

        generarRecibo(r);

        return true;
    }

    public ArrayList<Reserva> getReservasDeSocio(Socios socio) {
        ArrayList<Reserva> resultado = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getSocio().equals(socio)) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    private void generarRecibo(Reserva r) {

        try (FileWriter fw = new FileWriter("recibos.txt", true)) {

            fw.write("SOCIO: " + r.getSocio().getNombre() + "\n");
            fw.write("ACTIVIDAD: " + r.getActividad().getNombre() + "\n");
            fw.write("HORARIO: " + r.getActividad().getHorario().getDia() + "\n");
            fw.write("PRECIO: " + r.getPreciofinal() + "\n");
            fw.write("----------------------\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void añadirActividad(Actividades a) {
        actividades.add(a);
    }

    public void eliminarActividad(Actividades a) {
        actividades.remove(a);
    }

    public void añadirSocio(Socios s) {
        socios.add(s);
    }

    public ArrayList<Actividades> getActividades() {
        return actividades;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public boolean cancelarReserva(Reserva r) {
        return reservas.remove(r);
    }

    public ArrayList<Reserva> getReservasOrdenadasPorFecha() {
        ArrayList<Reserva> ordenadas = new ArrayList<>(reservas);
        ordenadas.sort((r1, r2) -> r1.getFecha().compareTo(r2.getFecha()));
        return ordenadas;
    }

    public Usuarios login(String correo, String clave) {
        if (correo.equals("admin@javafit.com") && clave.equals("admin")) {
            return new Administradores(correo, clave);
        }

        for (Socios s : socios) {
            if (s.getCorreo().equals(correo) && s.getClave().equals(clave)) {
                return s;
            }
        }
        return null;
    }

}
