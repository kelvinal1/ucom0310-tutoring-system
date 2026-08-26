package edu.uees.tutorias.app;

import edu.uees.tutorias.domain.AvailabilitySlot;
import edu.uees.tutorias.domain.Student;
import edu.uees.tutorias.domain.Teacher;
import edu.uees.tutorias.domain.TutoringReservation;
import edu.uees.tutorias.notification.ConsoleNotifier;
import edu.uees.tutorias.repository.InMemoryReservationRepository;
import edu.uees.tutorias.service.ReservationService;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

        Student student = new Student(
                "ST-001",
                "Kevin Aguilar",
                "student@uees.edu.ec"
        );

        Teacher teacher = new Teacher(
                "TE-001",
                "Jaime Sayago",
                "teacher@uees.edu.ec"
        );


        teacher.addAvailability(
                new AvailabilitySlot(
                        "SLOT-001",
                        LocalDateTime.of(2026, 8, 27, 10, 0),
                        LocalDateTime.of(2026, 8, 27, 11, 0)
                )
        );

        teacher.addAvailability(
                new AvailabilitySlot(
                        "SLOT-002",
                        LocalDateTime.of(2026, 8, 28, 14, 0),
                        LocalDateTime.of(2026, 8, 28, 15, 0)
                )
        );


        ReservationService reservationService = new ReservationService(
                new InMemoryReservationRepository(),
                new ConsoleNotifier()
        );

        TutoringReservation reservation = reservationService.createReservation(
                student,
                teacher,
                "SLOT-001"
        );

        reservationService.confirmReservation(reservation.getId());

        System.out.println();
        System.out.println("Final reservation status: " + reservation.getStatus());
    }
}
