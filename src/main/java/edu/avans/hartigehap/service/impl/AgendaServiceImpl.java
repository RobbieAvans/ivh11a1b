package edu.avans.hartigehap.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.avans.hartigehap.domain.StateException;
import edu.avans.hartigehap.domain.agenda.AgendaCollection;
import edu.avans.hartigehap.domain.agenda.AgendaItem;
import edu.avans.hartigehap.domain.agenda.HallReservationAgendaItemAdapter;
import edu.avans.hartigehap.domain.agenda.Iterator;
import edu.avans.hartigehap.domain.hallreservation.HallReservation;
import edu.avans.hartigehap.service.AgendaService;
import edu.avans.hartigehap.service.HallReservationService;

@Service("agendaService")
@Repository
@Transactional(rollbackFor = StateException.class)
public class AgendaServiceImpl implements AgendaService {

    @Autowired
    private HallReservationService hallReservationService;
    
    @Override
    public Iterator<AgendaItem> getItemsBetween(Date start, Date end) {
        AgendaCollection collection = new AgendaCollection();
        
        List<HallReservation> hallReservations = hallReservationService.findBetween(start, end);
        
        for (HallReservation hallReservation : hallReservations) {
            collection.addItem(new HallReservationAgendaItemAdapter(hallReservation));
        }
        
        return collection.createIterator();
    }

}
