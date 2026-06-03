/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package poo.javafit;

import java.time.LocalTime;
import java.util.ArrayList;

public class JavaFit {

    public static void main(String[] args) {
        
        GymManager gym = new GymManager();
        gym.cargarDatos();

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

        // ── SOCIOS (mínimo 4) ─────────────────────────────────
        Basico socio1 = new Basico("Ana Rodriguez",
                "Calle Mayor 1", "1234567890123456",
                612345678, "ana@gmail.com", "1234");

        Basico socio2 = new Basico("Pedro Sanchez",
                "Calle Luna 5", "9876543210987654",
                698765432, "pedro@gmail.com", "5678");

        Vip socio3 = new Vip("Maria Garcia",
                "Avenida Sol 10", "1111222233334444",
                677112233, "maria@gmail.com", "abcd");

        Vip socio4 = new Vip("Juan Lopez",
                "Calle Rio 3", "5555666677778888",
                655998877, "juan@gmail.com", "wxyz");

        gym.añadirSocio(socio1);
        gym.añadirSocio(socio2);
        gym.añadirSocio(socio3);
        gym.añadirSocio(socio4);

        // ── RESERVAS ──────────────────────────────────────────
        gym.reservar(socio1, yoga);
        gym.reservar(socio1, crossfit);
        gym.reservar(socio2, cardio);
        gym.reservar(socio2, yogaVip);
        gym.reservar(socio3, yogaVip);   // VIP con descuento
        gym.reservar(socio3, natacion);  // VIP con descuento
        gym.reservar(socio4, crossfit);  // VIP con descuento

        gym.guardarDatos();

        // ── ARRANCAR SWING ────────────────────────────────────
        java.awt.EventQueue.invokeLater(() -> new Login(gym).setVisible(true));
    }
}
