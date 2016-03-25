package edu.avans.hartigehap.service;

import java.util.List;

import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.domain.PartOfDay;

public interface HallService {
    List<Hall> findAll();

    Hall findById(long id);

    Hall save(Hall hall);

    boolean deleteById(long id);

    boolean delete(Hall hall);
    
    /**
     * 
     * @param hall
     * @param partOfDays - should be a sorted ascending list with at least one partOfDay
     * @return
     */
    boolean isAvailableFor(Hall hall, List<PartOfDay> partOfDays);
}
