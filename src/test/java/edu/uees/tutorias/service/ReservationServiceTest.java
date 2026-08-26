package edu.uees.tutorias.service;

import edu.uees.tutorias.domain.AvailabilitySlot;
import edu.uees.tutorias.domain.ReservationStatus;
import edu.uees.tutorias.domain.Student;
import edu.uees.tutorias.domain.Teacher;
import edu.uees.tutorias.domain.TutoringReservation;
import edu.uees.tutorias.notification.Notifier;
import edu.uees.tutorias.repository.InMemoryReservationRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReservationServiceTest {

    @Test
    void shouldCreateAndConfirmReservation() {

        Student student = new Student("S1", "Student", "student@test.com");
        Teacher teacher = new Teacher("T1", "Teacher", "teacher@test.com");

        AvailabilitySlot slot = new AvailabilitySlot(
                "A1",
                LocalDateTime.of(2026, 8, 27, 10, 0),
                LocalDateTime.of(2026, 8, 27, 11, 0)
        );

        teacher.addAvailability(slot);

        Notifier notifier = (user, message) -> {
            // In this test the notification does not need a real implementation.
        };

        ReservationService service = new ReservationService(
                new InMemoryReservationRepository(),
                notifier
        );

        TutoringReservation reservation = service.createReservation(student, teacher, "A1");

        assertEquals(ReservationStatus.PENDING, reservation.getStatus());
        assertFalse(slot.isAvailable());

        service.confirmReservation(reservation.getId());

        assertEquals(ReservationStatus.CONFIRMED, reservation.getStatus());
    }

    @Test
    void shouldReleaseSlotWhenReservationIsCancelled() {

        Student student = new Student("S1", "Student", "student@test.com");
        Teacher teacher = new Teacher("T1", "Teacher", "teacher@test.com");

        AvailabilitySlot slot = new AvailabilitySlot(
                "A1",
                LocalDateTime.of(2026, 8, 27, 10, 0),
                LocalDateTime.of(2026, 8, 27, 11, 0)
        );

        teacher.addAvailability(slot);

        ReservationService service = new ReservationService(
                new InMemoryReservationRepository(),
                (user, message) -> {
                }
        );

        TutoringReservation reservation = service.createReservation(student, teacher, "A1");

        service.cancelReservation(reservation.getId());

        assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
        assertTrue(slot.isAvailable());
    }
}
