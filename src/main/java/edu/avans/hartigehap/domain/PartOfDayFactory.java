package edu.avans.hartigehap.domain;

import java.util.Date;

public class PartOfDayFactory {

    public PartOfDay makePartOfDay(String part, Date date) {
        if (part == "" || date == null) {
            return null;
        }
        if ("MORNING".equalsIgnoreCase(part)) {
            return new Morning(date);
        } else if ("AFTERNOON".equalsIgnoreCase(part)) {
            return new Afternoon(date);
        } else if ("EVENING".equalsIgnoreCase(part)) {
            return new Evening(date);
        }
        return null;
    }
}
