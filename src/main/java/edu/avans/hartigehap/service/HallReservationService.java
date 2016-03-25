package edu.avans.hartigehap.service;

import java.util.Date;
import java.util.List;

import edu.avans.hartigehap.domain.hallreservation.HallReservation;
import edu.avans.hartigehap.web.controller.rs.body.HallReservationRequest;
import edu.avans.hartigehap.web.controller.rs.body.InvalidJsonRequestException;

public interface HallReservationService {
    List<HallReservation> findAll();

    HallReservation findById(long id);
    
    List<HallReservation> findBetween(Date startDate, Date endDate);

    void delete(HallReservation hallReservation);

    HallReservation update(HallReservation hallReservation, HallReservationRequest hallReservationRequest) throws InvalidJsonRequestException;
}
