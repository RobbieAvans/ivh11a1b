package edu.avans.hartigehap.domain.agenda;

import java.util.Date;

public interface AgendaItem {

    /**
     * Get the startDate of the agendaItem
     * 
     * @return Date
     */
    public Date getStartDate();

    /**
     * Get the endDate of the agendaItem
     * 
     * @return Date
     */
    public Date getEndDate();

    /**
     * Get the description of the agendaItem
     * 
     * @return String
     */
    public String getDescription();
}
