package edu.avans.hartigehap.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import edu.avans.hartigehap.domain.PartOfDay;
import edu.avans.hartigehap.domain.StateException;
import edu.avans.hartigehap.repository.PartOfDayRepository;
import edu.avans.hartigehap.service.PartOfDayService;

@Service("partOfDayService")
@Repository
@Transactional(rollbackFor = StateException.class)
public class PartOfDayServiceImpl implements PartOfDayService{

    @Autowired
    private PartOfDayRepository partOfDayRepository;
    
    @Override
    public List<PartOfDay> findByWeekAndHall(int hallId, int weekNr) {
        List<PartOfDay> PartOfDays = new ArrayList<>();
        for (PartOfDay partOfDay : partOfDayRepository.findAll()){
            if(partOfDay.getHallReservation().getHall().getId() == hallId && Integer.parseInt(new SimpleDateFormat("w").format(partOfDay.getStartTime())) == weekNr){
                PartOfDays.add(partOfDay);
            }
        }
        return PartOfDays;
    }
    
    @Override
    public List<PartOfDay> findAll() {
        return (List<PartOfDay>) partOfDayRepository.findAll();
    }
    

    @Override
    public PartOfDay save(PartOfDay partOfDay) {
        return partOfDayRepository.save(partOfDay);
    }

    @Override
    public void delete(Long id) {
        partOfDayRepository.delete(id);
        
    }

}
