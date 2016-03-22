package edu.avans.hartigehap.service;

import java.util.List;

import edu.avans.hartigehap.domain.PartOfDay;

public interface PartOfDayService {
    List<PartOfDay> findByWeekAndHall(int hallId, int weekNr);
    
    List<PartOfDay> findAll();
    
    PartOfDay save (PartOfDay partOfDay);

    void delete (Long id);
}
