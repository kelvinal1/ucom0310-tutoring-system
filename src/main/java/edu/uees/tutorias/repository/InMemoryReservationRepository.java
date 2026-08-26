package edu.uees.tutorias.repository;

import edu.uees.tutorias.domain.TutoringReservation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryReservationRepository implements ReservationRepository {

    private final Map<String, TutoringReservation> reservations = new LinkedHashMap<>();


    @Override
    public void save(TutoringReservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation is required");
        }

        reservations.put(reservation.getId(), reservation);
    }

    @Override
    public Optional<TutoringReservation> findById(String id) {
        return Optional.ofNullable(reservations.get(id));
    }

    @Override
    public List<TutoringReservation> findAll() {
        return new ArrayList<>(reservations.values());
    }
}
