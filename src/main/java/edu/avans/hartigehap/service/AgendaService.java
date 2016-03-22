package edu.avans.hartigehap.service;

import java.util.Date;

import edu.avans.hartigehap.domain.agenda.AgendaItem;
import edu.avans.hartigehap.domain.agenda.Iterator;

public interface AgendaService {
    Iterator<AgendaItem> getItemsBetween(Date start, Date end);
}
