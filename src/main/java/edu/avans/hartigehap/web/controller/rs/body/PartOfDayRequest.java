package edu.avans.hartigehap.web.controller.rs.body;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import edu.avans.hartigehap.domain.PartOfDay;
import edu.avans.hartigehap.domain.PartOfDayFactory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartOfDayRequest {
    
    private PartOfDayFactory factory;
    
    private String date;
    private String partOfDay;
    
    public PartOfDayRequest() {
        // Dependency injection?
        factory = new PartOfDayFactory();
    }
    
    public PartOfDay getPartOfDay() {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {            
            return factory.makePartOfDay(partOfDay, format.parse(date));
        } catch (ParseException e) {
            return null;
        }
    }
}
