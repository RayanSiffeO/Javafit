/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.javafit.model;

import java.util.ArrayList;
import java.io.*;
import java.time.LocalDate;
import java.util.stream.*;
import java.util.Map;
import java.util.*;

/**
 * Gestor principal del gimnasio JavaFit que centraliza la logica del negocio.
 * Se encarga de administrar las listas globales de actividades, reservas,
 * monitores y socios. Tambien maneja las busquedas avanzadas mediante streams,
 * las reglas de validacion para reservas e inscripciones, y la persistencia
 * en archivos del estado general del gimnasio.
 *
 * @author RAYANs
 */
public class GymManager {

    private ArrayList<Actividades> actividades;
    private ArrayList<Reserva> reservas;
    private ArrayList<Monitor> monitores;
    private ArrayList<Socios> socios;

    /**
     * Constructor por defecto que inicializa todas las listas de datos vacias.
     */
    public GymManager() {
        actividades = new ArrayList<>();
        reservas = new ArrayList<>();
        monitores = new ArrayList<>();
        socios = new ArrayList<>();
    }

    /**
     * Constructor que permite cargar colecciones de datos ya existentes.
     *
     * @param actividades Coleccion inicial de actividades.
     * @param reservas    Coleccion inicial de reservas.
     * @param monitores   Coleccion inicial de monitores.
     * @param socios      Coleccion inicial de socios.
     */
    public GymManager(ArrayList<Actividades> actividades, ArrayList<Reserva> reservas,
            ArrayList<Monitor> monitores, ArrayList<Socios> socios) {
        this.actividades = actividades;
        this.reservas = reservas;
        this.monitores = monitores;
        this.socios = socios;
    }

    /**
     * Obtiene la lista de actividades.
     *
     * @return El listado con todas las actividades.
     */
    public ArrayList<Actividades> getActividades() {
        return actividades;
    }

    /**
     * Modifica la lista de actividades.
     *
     * @param actividades La nueva lista de actividades.
     */
    public void setActividades(ArrayList<Actividades> actividades) {
        this.actividades = actividades;
    }

    /**
     * Obtiene la lista de reservas.
     *
     * @return El listado de reservas generales.
     */
    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    /**
     * Modifica la lista de reservas.
     *
     * @param reservas La nueva lista de reservas.
     */
    public void setReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }

    /**
     * Obtiene la lista de monitores.
     *
     * @return El listado del personal tecnico.
     */
    public ArrayList<Monitor> getMonitores() {
        return monitores;
    }

    /**
     * Modifica la lista de monitores.
     *
     * @param monitores La nueva lista de monitores.
     */
    public void setMonitores(ArrayList<Monitor> monitores) {
        this.monitores = monitores;
    }

    /**
     * Obtiene la lista de socios.
     *
     * @return El listado de socios registrados.
     */
    public ArrayList<Socios> getSocios() {
        return socios;
    }

    /**
     * Modifica la lista de socios.
     *
     * @param socios La nueva lista de socios.
     */
    public void setSocios(ArrayList<Socios> socios) {
        this.socios = socios;
    }

    /**
     * Filtra las actividades segun la modalidad deportiva indicada.
     *
     * @param tipo El tipo de actividad a buscar.
     * @return Una lista con las actividades que coinciden con el tipo.
     */
    public List<Actividades> buscarPorTipo(TipoActividad tipo) {
        return actividades.stream()
                .filter(a -> a.getTipo() == tipo)
                .collect(Collectors.toList());
    }

    /**
     * Filtra las actividades asignadas a un monitor concreto.
     *
     * @param m El monitor encargado de la sesion.
     * @return Una lista de actividades guiadas por dicho monitor.
     */
    public List<Actividades> buscarPorMonitor(Monitor m) {
        return actividades.stream()
                .filter(a -> a.getMonitor().equals(m))
                .collect(Collectors.toList());
    }

    /**
     * Filtra las actividades planificadas para un dia especifico de la semana.
     *
     * @param dia El dia de la semana a consultar.
     * @return Una lista de actividades que se imparten ese dia.
     */
    public List<Actividades> buscarPorDia(Horario.Dia dia) {
        return actividades.stream()
                .filter(a -> a.getHorario().getDia() == dia)
                .collect(Collectors.toList());
    }

    /**
     * Realiza una busqueda combinada de actividades aplicando filtros opcionales.
     *
     * @param tipo El tipo de actividad (puede ser null para omitir el filtro).
     * @param dia  El dia de la semana (puede ser null para omitir el filtro).
     * @return Una lista filtrada con las actividades que cumplan los criterios.
     */
    public List<Actividades> buscarActividades(TipoActividad tipo, Horario.Dia dia) {
        return actividades.stream()
                .filter(a -> tipo == null || a.getTipo() == tipo)
                .filter(a -> dia == null || a.getHorario().getDia() == dia)
                .collect(Collectors.toList());
    }

    /**
     * Busca un socio registrado usando su correo electronico como clave unica.
     *
     * @param correo El correo electronico a buscar.
     * @return El objeto Socio encontrado, o null si no existe coincidencia.
     */
    public Socios buscarSocioPorCorreo(String correo) {
        return socios.stream()
                .filter(s -> s.getCorreo().equals(correo))
                .findFirst()
                .orElse(null);
    }

    /**
     * Comprueba de manera privada si una actividad todavia tiene plazas libres.
     *
     * @param actividad La actividad que se quiere verificar.
     * @return true si las reservas actuales no superan el aforo de la sala; false de lo contrario.
     */
    private boolean hayAforo(Actividades actividad) {
        long ocupadas = reservas.stream()
                .filter(r -> r.getActividad().equals(actividad))
                .count();
        return ocupadas < actividad.getSala().getMaxaforo();
    }

    /**
     * Procesa la solicitud de reserva de un socio para una actividad.
     * <p>
     * Valida que quede aforo, que el horario este configurado, que no sea una
     * reserva duplicada para el mismo dia y que la tarjeta de pago no este caducada
     * en caso de que la sesion conlleve algun coste.
     *
     * @param socio     El socio que desea reservar.
     * @param actividad La actividad seleccionada.
     * @return true si la reserva supera los filtros y se guarda con exito; false si es rechazada.
     */
    public boolean reservar(Socios socio, Actividades actividad) {
        if (!hayAforo(actividad)) {
            return false;
        }
        if (actividad.getHorario() == null) {
            return false;
        }

        boolean duplicado = reservas.stream()
                .anyMatch(r -> r.getSocio().getCorreo().equals(socio.getCorreo())
                && r.getActividad().getNombre().equals(actividad.getNombre())
                && r.getFecha().equals(LocalDate.now()));
        if (duplicado) {
            return false;
        }

        double precio = socio.calcularPrecio(actividad);

        if (precio > 0 && socio.getTarjeta() != null && socio.getTarjeta().estaCaducada()) {
            return false;
        }

        Reserva r = new Reserva(socio, actividad, actividad.getHorario(), precio, LocalDate.now());
        reservas.add(r);
        return true;
    }

    /**
     * Obtiene el historial de reservas que pertenecen a un socio en concreto.
     *
     * @param socio El socio del que se quieren recuperar los datos.
     * @return Una lista con todas sus reservas vinculadas.
     */
    public List<Reserva> getReservasDeSocio(Socios socio) {
        return reservas.stream()
                .filter(r -> r.getSocio().equals(socio))
                .collect(Collectors.toList());
    }

    /**
     * Cancela y elimina del sistema una reserva dada.
     *
     * @param r La reserva que se va a dar de baja.
     * @return true si la reserva existia y fue borrada con exito; false en caso contrario.
     */
    public boolean cancelarReserva(Reserva r) {
        return reservas.remove(r);
    }

    /**
     * Devuelve todas las reservas ordenadas cronologicamente por su fecha.
     *
     * @return Lista de reservas ordenadas de mas antiguas a mas recientes.
     */
    public List<Reserva> getReservasOrdenadasPorFecha() {
        return reservas.stream()
                .sorted((r1, r2) -> r1.getFecha().compareTo(r2.getFecha()))
                .collect(Collectors.toList());
    }

    /**
     * Recupera las reservas realizadas a partir de una fecha especifica en adelante.
     *
     * @param desde La fecha de corte para el filtro.
     * @return Lista de reservas filtradas y ordenadas por fecha.
     */
    public List<Reserva> getReservasDesde(LocalDate desde) {
        return reservas.stream()
                .filter(r -> !r.getFecha().isBefore(desde))
                .sorted((r1, r2) -> r1.getFecha().compareTo(r2.getFecha()))
                .collect(Collectors.toList());
    }

    /**
     * Calcula la suma de los importes de todas las reservas registradas.
     *
     * @return El monto total recaudado por el gimnasio como valor numerico continuo.
     */
    public double getTotalRecaudado() {
        return reservas.stream()
                .mapToDouble(Reserva::getPreciofinal)
                .sum();
    }

    /**
     * Identifica cual es el socio que cuenta con mayor numero de reservas guardadas.
     *
     * @return El objeto Socio con mas actividad en el centro, o null si no hay reservas.
     */
    public Socios socioConMasServicios() {
        return reservas.stream()
                .collect(Collectors.groupingBy(Reserva::getSocio, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    /**
     * Añade una actividad al catálogo si no existe otra registrada con el mismo nombre.
     *
     * @param a La nueva actividad a dar de alta.
     */
    public void añadirActividad(Actividades a) {
        boolean existe = actividades.stream()
                .anyMatch(act -> act.getNombre().equalsIgnoreCase(a.getNombre()));
        if (!existe) {
            actividades.add(a);
        }
    }

    /**
     * Elimina una actividad del catalogo general.
     *
     * @param a La actividad que se desea dar de baja.
     */
    public void eliminarActividad(Actividades a) {
        actividades.remove(a);
    }

    /**
     * Añade un socio de forma directa controlando que su correo no este repetido.
     *
     * @param s El socio que se va a registrar.
     */
    public void añadirSocio(Socios s) {
        if (buscarSocioPorCorreo(s.getCorreo()) == null) {
            socios.add(s);
        }
    }

    /**
     * Añade un socio al sistema aplicando reglas estrictas de validacion de datos.
     * <p>
     * Comprueba la duplicidad del correo, que el telefono tenga exactamente 9 digitos
     * y que los digitos de la tarjeta oscilen en un rango valido de longitud sin estar caducada.
     *
     * @param s El socio con los datos listos para validar.
     * @return true si cumple todos los requisitos y se añade al sistema; false si se rechaza.
     */
    public boolean añadirSocioValidado(Socios s) {
        if (buscarSocioPorCorreo(s.getCorreo()) != null) {
            return false;
        }

        if (String.valueOf(s.getTelefono()).length() != 9) {
            return false;
        }

        if (s.getTarjeta() != null) {
            int digitos = String.valueOf(s.getTarjeta().getNumero()).length();
            if (digitos < 13 || digitos > 19) {
                return false;
            }
            if (s.getTarjeta().estaCaducada()) {
                return false;
            }
        }

        socios.add(s);
        return true;
    }

    /**
     * Valida las credenciales de acceso para permitir la entrada al sistema.
     * <p>
     * Comprueba en primera instancia si coincide con la cuenta fija del administrador,
     * y si no, busca en la lista de socios registrados.
     *
     * @param correo El correo electronico introducido.
     * @param clave  La contraseña introducida.
     * @return El objeto Usuarios correspondiente (Administradores o Socios), o null si falla.
     */
    public Usuarios login(String correo, String clave) {
        if (correo.equals("admin@javafit.com") && clave.equals("admin")) {
            return new Administradores(correo, clave);
        }
        return socios.stream()
                .filter(s -> s.getCorreo().equals(correo) && s.getClave().equals(clave))
                .findFirst()
                .orElse(null);
    }

    /**
     * Guarda el estado actual de las cuatro listas mediante la serializacion de objetos.
     * <p>
     * Almacena las colecciones completas en un archivo binario llamado gymdata.dat.
     */
    public void guardarDatos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("gymdata.dat"))) {
            oos.writeObject(actividades);
            oos.writeObject(socios);
            oos.writeObject(reservas);
            oos.writeObject(monitores);
            System.out.println("Datos guardados correctamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
        }
    }

    /**
     * Recupera el estado de las listas leyendo el archivo serializado binario.
     * <p>
     * Si el archivo gymdata.dat no existe, captura la excepcion e inicia con listas vacias.
     */
    @SuppressWarnings("unchecked")
    public void cargarDatos() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("gymdata.dat"))) {
            actividades = (ArrayList<Actividades>) ois.readObject();
            socios = (ArrayList<Socios>) ois.readObject();
            reservas = (ArrayList<Reserva>) ois.readObject();
            monitores = (ArrayList<Monitor>) ois.readObject();
            System.out.println("Datos cargados correctamente.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No hay datos previos, empezando desde cero.");
        }
    }

    /**
     * Genera un justificante legible en formato de texto plano y lo añade al archivo final.
     * <p>
     * Escribe la informacion del socio, de la sesion, coste y datos de tarjeta de la reserva
     * de forma acumulativa usando el modo de escritura append.
     *
     * @param r La reserva de la que se quiere emitir el recibo en texto.
     */
    public void generarRecibo(Reserva r) {
        try (FileWriter fw = new FileWriter("recibos.txt", true)) {
            fw.write("SOCIO: " + r.getSocio().getNombre() + "\n");
            fw.write("ACTIVIDAD: " + r.getActividad().getNombre() + "\n");
            fw.write("HORARIO: " + r.getActividad().getHorario().getDia() + "\n");
            fw.write("PRECIO: " + r.getPreciofinal() + "\n");
            fw.write("FECHA: " + r.getFecha() + "\n");
            if (r.getSocio().getTarjeta() != null) {
                fw.write("TARJETA: " + r.getSocio().getTarjeta() + "\n");
            }
            fw.write("----------------------\n");
        } catch (IOException e) {
            System.err.println("Error al generar recibo: " + e.getMessage());
        }
    }

    /**
     * Obtiene de forma rapida el ultimo registro de reserva añadido a la coleccion.
     *
     * @return El objeto Reserva mas reciente de la lista.
     */
    public Reserva getUltimaReserva() {
        return reservas.get(reservas.size() - 1);
    }
}
