package edu.avans.hartigehap.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;


import edu.avans.hartigehap.domain.HallReservation;

public interface HallReservationRepository extends PagingAndSortingRepository<HallReservation, Long> {
	
	 
}