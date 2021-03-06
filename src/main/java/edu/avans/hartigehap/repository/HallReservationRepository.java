package edu.avans.hartigehap.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import edu.avans.hartigehap.domain.hallreservation.HallReservation;

public interface HallReservationRepository extends PagingAndSortingRepository<HallReservation, Long> {

    Long deleteByIdIn(List<Long> ids);
}