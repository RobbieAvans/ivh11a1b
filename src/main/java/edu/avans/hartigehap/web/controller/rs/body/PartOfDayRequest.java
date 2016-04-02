package edu.avans.hartigehap.web.controller.rs.body;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.PartOfDay;
import edu.avans.hartigehap.domain.PartOfDayFactory;
import edu.avans.hartigehap.domain.PartOfDayFactoryImlp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@NoArgsConstructor
public class PartOfDayRequest {
    
    @JsonIgnore
    // Dependency injection?
    private PartOfDayFactory factory = new PartOfDayFactoryImlp();
    
    private String date;
    private String partOfDay;
    
    public PartOfDayRequest(String date, String partOfDay) {
        this.date = date;
        this.partOfDay = partOfDay;
    }
    
    @JsonIgnore
    public PartOfDay getPartOfDayObject() throws InvalidJsonRequestException {
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
