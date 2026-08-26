package edu.uees.tutorias.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class AvailabilitySlot {

    private final String id;
    private final LocalDateTime start;
    private final LocalDateTime end;

    private boolean available = true;

    public AvailabilitySlot(String id, LocalDateTime start, LocalDateTime end) {
        this.id = Objects.requireNonNull(id, "Slot id is required");
        this.start = Objects.requireNonNull(start, "Start date is required");
        this.end = Objects.requireNonNull(end, "End date is required");

        if (id.isBlank()) {
            throw new IllegalArgumentException("Slot id is required");
        }

        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
    }


    // A slot can only be reserved once.
    public void reserve() {
        if (!available) {
            throw new IllegalStateException("The slot is not available");
        }

        available = false;
    }

    public void release() {
        available = true;
    }


    public String getId() {
        return id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public boolean isAvailable() {
        return available;
    }
}
