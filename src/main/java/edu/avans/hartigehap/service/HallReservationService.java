package edu.avans.hartigehap.service;

import java.util.List;

import edu.avans.hartigehap.domain.HallReservation;

public interface HallReservationService {
    List<HallReservation> findAll();

    HallReservation save(HallReservation hallReservation);

    void delete(HallReservation hallReservation);
}
