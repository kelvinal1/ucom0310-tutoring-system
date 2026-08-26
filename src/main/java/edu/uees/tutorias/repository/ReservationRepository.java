package edu.uees.tutorias.repository;

import edu.uees.tutorias.domain.TutoringReservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    void save(TutoringReservation reservation);

    Optional<TutoringReservation> findById(String id);

    List<TutoringReservation> findAll();
}
