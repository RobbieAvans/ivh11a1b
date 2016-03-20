package edu.avans.hartigehap.service;

import java.util.List;

import edu.avans.hartigehap.domain.hallreservation.HallReservation;
import edu.avans.hartigehap.web.controller.rs.body.HallReservationRequest;

public interface HallReservationService {
    List<HallReservation> findAll();

    HallReservation findById(long id);

    void delete(HallReservation hallReservation);

    HallReservation update(HallReservation hallReservation, HallReservationRequest hallReservationRequest) throws Exception;
}
