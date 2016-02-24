package edu.avans.hartigehap.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import edu.avans.hartigehap.domain.Hall;

public interface HallRepository extends PagingAndSortingRepository<Hall, Long> {

}
