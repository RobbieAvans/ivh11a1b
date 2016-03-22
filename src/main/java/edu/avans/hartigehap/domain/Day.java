package edu.avans.hartigehap.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.avans.hartigehap.domain.hallreservation.HallReservation;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Day{
    private String description;
    private String date;
    private List<DayPart> dayParts;
    
    public Day(String description){
        this.description = description;
        dayParts = new ArrayList<>();
    }
    
    public void addDayPart(DayPart part){
        dayParts.add(part);
        
    }
 }
