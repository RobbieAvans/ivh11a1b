package edu.avans.hartigehap.web.controller.rs.body;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DayPartResponse{
    private String description;
    private boolean available;
    
    public DayPartResponse(String description){
        this.description = description;
        available = true;
    }
 }
