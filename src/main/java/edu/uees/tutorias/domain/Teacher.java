package edu.uees.tutorias.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Teacher extends User {

    private final List<AvailabilitySlot> availableSlots = new ArrayList<>();

    public Teacher(String id, String name, String email) {
        super(id, name, email);
    }


    // The teacher manages his own available schedules.
    public void addAvailability(AvailabilitySlot slot) {
        if (slot == null) {
            throw new IllegalArgumentException("Availability slot is required");
        }

        boolean duplicated = availableSlots.stream()
                .anyMatch(current -> current.getId().equals(slot.getId()));

        if (duplicated) {
            throw new IllegalArgumentException("The availability slot already exists");
        }

        availableSlots.add(slot);
    }

    public void removeAvailability(String slotId) {
        AvailabilitySlot slot = findSlot(slotId);

        if (!slot.isAvailable()) {
            throw new IllegalStateException("A reserved slot cannot be removed");
        }

        availableSlots.remove(slot);
    }

    public boolean isSlotAvailable(String slotId) {
        return availableSlots.stream()
                .anyMatch(slot -> slot.getId().equals(slotId) && slot.isAvailable());
    }

    public AvailabilitySlot getSlot(String slotId) {
        return findSlot(slotId);
    }

    public List<AvailabilitySlot> getAvailableSlots() {
        return Collections.unmodifiableList(availableSlots);
    }


    private AvailabilitySlot findSlot(String slotId) {
        return availableSlots.stream()
                .filter(slot -> slot.getId().equals(slotId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Availability slot not found"));
    }
}
