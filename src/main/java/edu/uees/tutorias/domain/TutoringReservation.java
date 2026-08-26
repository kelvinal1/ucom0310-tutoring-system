package edu.uees.tutorias.domain;

import java.util.Objects;

public class TutoringReservation {

    private final String id;
    private final Student student;
    private final Teacher teacher;

    private AvailabilitySlot slot;
    private ReservationStatus status;

    public TutoringReservation(
            String id,
            Student student,
            Teacher teacher,
            AvailabilitySlot slot
    ) {
        this.id = Objects.requireNonNull(id, "Reservation id is required");
        this.student = Objects.requireNonNull(student, "Student is required");
        this.teacher = Objects.requireNonNull(teacher, "Teacher is required");
        this.slot = Objects.requireNonNull(slot, "Availability slot is required");

        if (id.isBlank()) {
            throw new IllegalArgumentException("Reservation id is required");
        }

        slot.reserve();
        status = ReservationStatus.PENDING;
    }


    public void confirm() {
        if (status == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("A cancelled reservation cannot be confirmed");
        }

        status = ReservationStatus.CONFIRMED;
    }

    public void cancel() {
        if (status == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("The reservation is already cancelled");
        }

        slot.release();
        status = ReservationStatus.CANCELLED;
    }

    public void reschedule(AvailabilitySlot newSlot) {
        Objects.requireNonNull(newSlot, "New availability slot is required");

        if (status == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("A cancelled reservation cannot be rescheduled");
        }

        if (!newSlot.isAvailable()) {
            throw new IllegalStateException("The new slot is not available");
        }

        slot.release();
        newSlot.reserve();

        slot = newSlot;

        // When it is rescheduled it needs confirmation again.
        status = ReservationStatus.PENDING;
    }


    public String getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public AvailabilitySlot getSlot() {
        return slot;
    }

    public ReservationStatus getStatus() {
        return status;
    }
}
