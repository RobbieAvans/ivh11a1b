package edu.avans.hartigehap.domain;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Evening extends PartOfDay {
    private static final long serialVersionUID = 1L;
    private static final int STARTTIMEHOUR = 13;
    private static final int ENDTIMEHOUR = 18;
    private static final Double PRICEFACTOR = 1.5;

    @SuppressWarnings("deprecation")
    public Evening(Date date) {

        date.setMinutes(0);
        date.setSeconds(0);

        Date startTime = (Date) date.clone();
        Date endTime = (Date) date.clone();

        startTime.setHours(STARTTIMEHOUR);
        endTime.setHours(ENDTIMEHOUR);

        setStartTime(startTime);
        setEndTime(endTime);
        setPriceFactor(PRICEFACTOR);
        setDescription("Evening");
    }
}
