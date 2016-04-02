package edu.avans.hartigehap.domain;

import java.util.Date;

public interface PartOfDayFactory {
    public PartOfDay makePartOfDay(String part, Date date);
}
