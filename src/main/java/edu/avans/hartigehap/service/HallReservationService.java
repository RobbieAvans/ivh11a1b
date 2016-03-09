package edu.avans.hartigehap.service;

import java.util.List;

import edu.avans.hartigehap.domain.HallReservation;

public interface HallReservationService {
	List<HallReservation> findAll();
	HallReservation findById (long id);
	void delete (HallReservation hallReservation);
	//HallReservation update (HallReservationAPIWrapper newReservation, HallReservation oldReservation);
}
