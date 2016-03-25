package edu.avans.hartigehap.domain.agenda;

import java.util.Date;
import java.util.List;

import edu.avans.hartigehap.domain.PartOfDay;
import edu.avans.hartigehap.domain.hallreservation.HallReservation;

public class HallReservationAgendaItemAdapter implements AgendaItem {

    private HallReservation reservation;
    
    /**
     * Receive a HallReservation and translate it to an AgendaItem
     * 
     * @param reservation
     */
    public HallReservationAgendaItemAdapter(HallReservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public Date getStartDate() {
        return reservation.getPartOfDays().get(0).getStartTime();
    }

    @Override
    public Date getEndDate() {
        List<PartOfDay> partOfDays = reservation.getPartOfDays();
        return partOfDays.get(partOfDays.size() - 1).getEndTime();
    }

    @Override
    public String getDescription() {
        return reservation.getDescription();
    }
}
