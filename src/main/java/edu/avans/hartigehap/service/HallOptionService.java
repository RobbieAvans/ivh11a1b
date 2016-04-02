package edu.avans.hartigehap.service;

import java.util.List;

import edu.avans.hartigehap.domain.HallOption;

public interface HallOptionService {
    List<HallOption> findAll();

    List<HallOption> findByIds(List<Long> hallOptionIds);
    
    HallOption findById(Long hallOptionId);

    HallOption save(HallOption hallOption);

    boolean deleteById(long id);

    boolean delete(HallOption hallOption);
    
    boolean hasHallReservations(HallOption hallOption);
}