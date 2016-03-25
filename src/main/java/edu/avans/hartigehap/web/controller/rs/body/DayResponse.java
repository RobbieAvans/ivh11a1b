package edu.avans.hartigehap.web.controller.rs.body;

import java.util.ArrayList;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DayResponse{
    private String description;
    private String date;
    private List<DayPartResponse> dayParts;
    
    public DayResponse(String description){
        this.description = description;
        dayParts = new ArrayList<>();
    }
    
    public void addDayPart(DayPartResponse part){
        dayParts.add(part);
        
    }
 }
