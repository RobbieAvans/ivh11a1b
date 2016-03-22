package edu.avans.hartigehap.domain;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Morning extends PartOfDay {
    private static final long serialVersionUID = 1L;
    private static final int STARTTIMEHOUR = 8;
    private static final int ENDTIMEHOUR = 13;
    private static final Double PRICEFACTOR = 1.0;

    public Morning(){}
    
    @SuppressWarnings("deprecation")
    public Morning(Date date) {

        date.setMinutes(0);
        date.setSeconds(0);

        Date startTime = (Date) date.clone();
        Date endTime = (Date) date.clone();

        startTime.setHours(STARTTIMEHOUR);
        endTime.setHours(ENDTIMEHOUR);

        setStartTime(startTime);
        setEndTime(endTime);
        setPriceFactor(PRICEFACTOR);
        setDescription("Morning");
    }

}
