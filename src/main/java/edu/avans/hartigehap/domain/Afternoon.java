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
    
    public Afternoon(Date date) {
        setTime(date,STARTTIMEHOUR ,ENDTIMEHOUR);
        setDescription("Afternoon");
    }

    @Override
    public boolean canAddAfter(PartOfDay after) {
        return getEndTime().equals(after.getStartTime());
    }
}
