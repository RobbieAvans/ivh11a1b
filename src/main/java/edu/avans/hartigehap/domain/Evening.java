package edu.avans.hartigehap.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Evening extends PartOfDay {
    private static final long serialVersionUID = 1L;

    private static final int STARTTIMEHOUR = 18;
    private static final int ENDTIMEHOUR = 23;

    public Evening() {
    }

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
        setDescription("Evening");
    }

    @Override
    public boolean canAddAfter(PartOfDay after) {
        // It should be always an morning
        if (after.getDescription() != "Morning") {
            return false;
        }

        // Check if after is one day later
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(getStartTime());
        cal1.add(Calendar.DATE, 1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(after.getStartTime());

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}
