package edu.uees.tutorias.app;

import edu.uees.tutorias.domain.Docente;
import edu.uees.tutorias.domain.EstadoReserva;
import edu.uees.tutorias.domain.Estudiante;
import edu.uees.tutorias.domain.HorarioDisponible;
import edu.uees.tutorias.domain.ReservaTutoria;
import edu.uees.tutorias.notification.NotificadorConsola;
import edu.uees.tutorias.repository.RepositorioReservasEnMemoria;
import edu.uees.tutorias.service.ServicioReservas;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

        Estudiante estudiante = new Estudiante(
                "ST-001",
                "Kevin Aguilar",
                "student@uees.edu.ec"
        );

        Docente docente = new Docente(
                "TE-001",
                "Jaime Sayago",
                "teacher@uees.edu.ec"
        );


        docente.agregarHorario(
                new HorarioDisponible(
                        "SLOT-001",
                        LocalDateTime.of(2026, 8, 27, 10, 0),
                        LocalDateTime.of(2026, 8, 27, 11, 0)
                )
        );

        docente.agregarHorario(
                new HorarioDisponible(
                        "SLOT-002",
                        LocalDateTime.of(2026, 8, 28, 14, 0),
                        LocalDateTime.of(2026, 8, 28, 15, 0)
                )
        );


        ServicioReservas servicioReservas = new ServicioReservas(
                new RepositorioReservasEnMemoria(),
                new NotificadorConsola()
        );

        ReservaTutoria reserva = servicioReservas.crearReserva(
                estudiante,
                docente,
                "SLOT-001"
        );

        servicioReservas.confirmarReserva(reserva.getId());

        System.out.println();
        System.out.println("Estado final de la reserva: " + convertirEstado(reserva.getEstado()));
    }

    private static String convertirEstado(EstadoReserva estado) {
        return switch (estado) {
            case PENDIENTE -> "PENDIENTE";
            case CONFIRMADA -> "CONFIRMADA";
            case CANCELADA -> "CANCELADA";
        };
    }
}
