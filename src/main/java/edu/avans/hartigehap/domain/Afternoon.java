package edu.avans.hartigehap.domain;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Afternoon extends PartOfDay {

    private static final long serialVersionUID = 1L;

    private static final int STARTTIMEHOUR = 13;
    private static final int ENDTIMEHOUR = 18;

    public Afternoon(){
    }
    
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
        setDescription("Afternoon");
    }

    @Override
    public boolean canAddAfter(PartOfDay after) {
        return getEndTime().equals(after.getStartTime());
    }
}
