package edu.avans.hartigehap.domain.agenda;

import java.util.Date;

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
        // TODO
        return new Date();
    }

    @Override
    public Date getEndDate() {
        // TODO
        return new Date();
    }

    @Override
    public String getDescription() {
        return reservation.getDescription();
    }
}
