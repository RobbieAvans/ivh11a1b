package edu.avans.hartigehap.domain;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Afternoon extends PartOfDay {

    private static final long serialVersionUID = 1L;

    private static final int STARTTIMEHOUR = 18;
    private static final int ENDTIMEHOUR = 23;
    private static final Double PRICEFACTORNORMAL = 1.0;

    @SuppressWarnings("deprecation")
    public Afternoon(Date date) {

        date.setMinutes(0);
        date.setSeconds(0);

        Date startTime = (Date) date.clone();
        Date endTime = (Date) date.clone();

        startTime.setHours(STARTTIMEHOUR);
        endTime.setHours(ENDTIMEHOUR);

        setStartTime(startTime);
        setEndTime(endTime);
        setPriceFactor(PRICEFACTORNORMAL);
        setDescription("Afternoon");
    }

}
