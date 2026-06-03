/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package poo.javafit.JavaFit;

import poo.javafit.ui.*;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.ArrayList;
import poo.javafit.model.Basico;
import poo.javafit.model.Especial;
import poo.javafit.model.GymManager;
import poo.javafit.model.Horario;
import poo.javafit.model.Monitor;
import poo.javafit.model.Normal;
import poo.javafit.model.Sala;
import poo.javafit.model.TarjetaBancaria;
import poo.javafit.model.TipoActividad;
import poo.javafit.model.Vip;

public class JavaFit {

    public static void main(String[] args) {

        GymManager gym = new GymManager();
        java.io.File archivo = new java.io.File("recibos.txt");
        boolean datosNuevos = !new java.io.File("gymdata.dat").exists();
        gym.cargarDatos();
        if (datosNuevos) {
            // ── SALAS ────────────────────────────────────────────
            Sala sala1 = new Sala(20, "Sala Yoga");
            Sala sala2 = new Sala(15, "Sala Cardio");
            Sala sala3 = new Sala(30, "Sala Musculacion");

            // ── HORARIOS ─────────────────────────────────────────
            Horario h1 = new Horario(Horario.Dia.LUNES,
                    LocalTime.of(9, 0), LocalTime.of(10, 0));
            Horario h2 = new Horario(Horario.Dia.MARTES,
                    LocalTime.of(10, 0), LocalTime.of(11, 0));
            Horario h3 = new Horario(Horario.Dia.MIERCOLES,
                    LocalTime.of(11, 0), LocalTime.of(12, 0));
            Horario h4 = new Horario(Horario.Dia.JUEVES,
                    LocalTime.of(17, 0), LocalTime.of(18, 0));
            Horario h5 = new Horario(Horario.Dia.VIERNES,
                    LocalTime.of(18, 0), LocalTime.of(19, 0));
            Horario h6 = new Horario(Horario.Dia.SABADO,
                    LocalTime.of(10, 0), LocalTime.of(11, 30));

            // ── MONITORES ────────────────────────────────────────
            ArrayList<Horario> dispCarlos = new ArrayList<>();
            dispCarlos.add(h1);
            dispCarlos.add(h2);
            Monitor carlos = new Monitor("Carlos Garcia",
                    TipoActividad.YOGA, dispCarlos);

            ArrayList<Horario> dispLaura = new ArrayList<>();
            dispLaura.add(h3);
            dispLaura.add(h4);
            Monitor laura = new Monitor("Laura Martinez",
                    TipoActividad.CARDIO, dispLaura);

            ArrayList<Horario> dispMiguel = new ArrayList<>();
            dispMiguel.add(h5);
            dispMiguel.add(h6);
            Monitor miguel = new Monitor("Miguel Lopez",
                    TipoActividad.MUSCULACION, dispMiguel);

            // ── ACTIVIDADES (mínimo 6) ────────────────────────────
            Normal yoga = new Normal("Yoga Matutino",
                    TipoActividad.YOGA, sala1, h1, "yoga.jpg", carlos);

            Normal cardio = new Normal("Cardio Intensivo",
                    TipoActividad.CARDIO, sala2, h2, "cardio.jpg", laura);

            Normal musculacion = new Normal("Musculacion Basica",
                    TipoActividad.MUSCULACION, sala3, h3, "musculacion.jpg", miguel);

            Especial yogaVip = new Especial("Yoga VIP Premium",
                    TipoActividad.YOGA, sala1, h4, carlos, "yogavip.jpg",
                    29.99, "Sesion exclusiva de yoga con instructor certificado");

            Especial crossfit = new Especial("Crossfit HIIT",
                    TipoActividad.CARDIO, sala2, h5, laura, "crossfit.jpg",
                    19.99, "Entrenamiento de alta intensidad por intervalos");

            Especial natacion = new Especial("Natacion Avanzada",
                    TipoActividad.NATACION, sala1, h6, miguel, "natacion.jpg",
                    24.99, "Tecnica y resistencia en natacion");

            gym.añadirActividad(yoga);
            gym.añadirActividad(cardio);
            gym.añadirActividad(musculacion);
            gym.añadirActividad(yogaVip);
            gym.añadirActividad(crossfit);
            gym.añadirActividad(natacion);

            TarjetaBancaria tarjeta1 = new TarjetaBancaria("Visa", 1234567890123456L, LocalDate.of(2029, 12, 31));
            TarjetaBancaria tarjeta2 = new TarjetaBancaria("Mastercard", 9876543210987654L, LocalDate.of(2028, 5, 15));
            TarjetaBancaria tarjeta3 = new TarjetaBancaria("Visa", 5555444433332222L, LocalDate.of(2030, 8, 20));
            TarjetaBancaria tarjeta4 = new TarjetaBancaria("American Express", 4444333322221111L, LocalDate.of(2027, 11, 1));

            Basico socio1 = new Basico("Calle Falsa 123", tarjeta1, 600111222, "Carlos Gómez", "carlos@email.com", "pass123");
            Basico socio2 = new Basico("Av. de la Constitución 45", tarjeta2, 600333444, "Ana López", "ana@email.com", "ana2026");

            Vip socio3 = new Vip("Paseo del Prado 12", tarjeta3, 600555666, "Rayan", "rayan@email.com", "adminVip");
            Vip socio4 = new Vip("Calle Mayor 8", tarjeta4, 600777888, "Laura Martínez", "laura@email.com", "secureClave");

            gym.añadirSocio(socio1);
            gym.añadirSocio(socio2);
            gym.añadirSocio(socio3);
            gym.añadirSocio(socio4);

            // ── RESERVAS ──────────────────────────────────────────
            gym.reservar(socio1, yoga);
            gym.reservar(socio1, crossfit);
            gym.reservar(socio2, cardio);
            gym.reservar(socio2, yogaVip);
            gym.reservar(socio3, yogaVip);
            gym.reservar(socio3, natacion);
            gym.reservar(socio4, crossfit);

            

            gym.guardarDatos();
        }

        if (!archivo.exists()) {
            for (poo.javafit.model.Reserva r : gym.getReservas()) {
                gym.generarRecibo(r);
            }
        }

        // ── ARRANCAR SWING ────────────────────────────────────
        java.awt.EventQueue.invokeLater(() -> new Login2(gym).setVisible(true));

    }
}
