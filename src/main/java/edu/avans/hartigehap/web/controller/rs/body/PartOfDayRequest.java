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
    
    public PartOfDay getPartOfDay() throws InvalidJsonRequestException {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            PartOfDay partOfDayObject = factory.makePartOfDay(partOfDay, format.parse(date));
            if (partOfDayObject != null) {
                return partOfDayObject;
            }
            throw new InvalidJsonRequestException("partofday_invalid");
        } catch (ParseException e) {
            throw new InvalidJsonRequestException("invalid_date_format");
        }
    }
}
