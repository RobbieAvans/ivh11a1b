package edu.avans.hartigehap.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import edu.avans.hartigehap.domain.HallReservation;

public interface HallReservationRepository extends PagingAndSortingRepository<HallReservation, Long> {

}