package edu.uees.tutorias.service;

import edu.uees.tutorias.domain.AvailabilitySlot;
import edu.uees.tutorias.domain.Student;
import edu.uees.tutorias.domain.Teacher;
import edu.uees.tutorias.domain.TutoringReservation;
import edu.uees.tutorias.notification.Notifier;
import edu.uees.tutorias.repository.ReservationRepository;

import java.util.UUID;

public class ReservationService {

    private final ReservationRepository repository;
    private final Notifier notifier;

    public ReservationService(
            ReservationRepository repository,
            Notifier notifier
    ) {
        this.repository = repository;
        this.notifier = notifier;
    }


    public TutoringReservation createReservation(
            Student student,
            Teacher teacher,
            String slotId
    ) {
        if (student == null || teacher == null) {
            throw new IllegalArgumentException("Student and teacher are required");
        }

        if (!teacher.isSlotAvailable(slotId)) {
            throw new IllegalStateException("The selected slot is not available");
        }

        AvailabilitySlot slot = teacher.getSlot(slotId);

        TutoringReservation reservation = new TutoringReservation(
                UUID.randomUUID().toString(),
                student,
                teacher,
                slot
        );

        repository.save(reservation);

        notifier.notify(student, "Your tutoring reservation was created");
        notifier.notify(teacher, "A student reserved one of your schedules");

        return reservation;
    }

    public void confirmReservation(String reservationId) {
        TutoringReservation reservation = getReservation(reservationId);

        reservation.confirm();
        repository.save(reservation);

        notifier.notify(reservation.getStudent(), "Your tutoring reservation was confirmed");
        notifier.notify(reservation.getTeacher(), "The tutoring reservation was confirmed");
    }

    public void cancelReservation(String reservationId) {
        TutoringReservation reservation = getReservation(reservationId);

        reservation.cancel();
        repository.save(reservation);

        notifier.notify(reservation.getStudent(), "Your tutoring reservation was cancelled");
        notifier.notify(reservation.getTeacher(), "The tutoring reservation was cancelled");
    }

    public void rescheduleReservation(String reservationId, String newSlotId) {
        TutoringReservation reservation = getReservation(reservationId);
        Teacher teacher = reservation.getTeacher();

        if (!teacher.isSlotAvailable(newSlotId)) {
            throw new IllegalStateException("The new slot is not available");
        }

        AvailabilitySlot newSlot = teacher.getSlot(newSlotId);

        reservation.reschedule(newSlot);
        repository.save(reservation);

        notifier.notify(reservation.getStudent(), "Your tutoring reservation was rescheduled");
        notifier.notify(reservation.getTeacher(), "The tutoring reservation was rescheduled");
    }


    private TutoringReservation getReservation(String reservationId) {
        return repository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
    }
}
