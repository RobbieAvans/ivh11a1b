package edu.avans.hartigehap.domain;

import java.util.Date;

import edu.avans.hartigehap.domain.hallreservation.HallReservation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DayPart{
    private String description;
    private boolean available;
    
    public DayPart(String description){
        this.description = description;
        available = true;
    }
 }
