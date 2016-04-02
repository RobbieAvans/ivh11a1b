package edu.avans.hartigehap.domain;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Morning extends PartOfDay {
    private static final long serialVersionUID = 1L;
    private static final int STARTTIMEHOUR = 8;
    private static final int ENDTIMEHOUR = 13;

    public Morning(){
    }
    
    @SuppressWarnings("deprecation")
    public Morning(Date date) {
        setTime(date,STARTTIMEHOUR ,ENDTIMEHOUR);
        setDescription("Morning");
    }

    @Override
    public boolean canAddAfter(PartOfDay after) {
        return getEndTime().equals(after.getStartTime());
    }
}
