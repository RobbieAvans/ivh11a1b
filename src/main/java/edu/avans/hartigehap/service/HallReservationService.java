package edu.avans.hartigehap.service;

import java.util.List;

import edu.avans.hartigehap.domain.HallReservation;
import edu.avans.hartigehap.domain.HallReservationOption;

public interface HallReservationService {
	List<HallReservation> findAll();
	HallReservationOption findById(Long hallReservationId);
	HallReservation save(HallReservation hallReservation);
	void delete (HallReservation hallReservation);
}
