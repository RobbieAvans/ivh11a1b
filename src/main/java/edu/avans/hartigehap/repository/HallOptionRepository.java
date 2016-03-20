package edu.avans.hartigehap.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import edu.avans.hartigehap.domain.HallOption;

public interface HallOptionRepository extends PagingAndSortingRepository<HallOption, Long> {
    List<HallOption> findByIdIn(List<Long> hallOptionIds);
}
