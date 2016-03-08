package edu.avans.hartigehap.domain;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CancelledState extends HallReservationState {
    private static final long serialVersionUID = 1L;

    public CancelledState() {
        setStateAsId();
    }
    
    public CancelledState(HallReservation hallReservation) {
        super(hallReservation);
        setStateAsId();
    }

    private void setStateAsId() {
        setId("CancelledState");
    }
    
    @Override
    public String strMailBody() {
        return "Beste %voornaam%, de reservering is geannuleerd";
    }

    @Override
    public String strMailSubject() {
        return "Annulering van reservering";
    }

    @Override
    public void submitReservation() {
        System.out.println("Je bent al gecancelled, submitten gaat niet meer");
    }

    @Override
    public void payReservation() {
        getCurrentHallReservation().setState(getCurrentHallReservation().getPaidState());
        getCurrentHallReservation().notifyAllObservers();
    }

    @Override
    public void cancelReservation() {
        System.out.println("Je bent al gecancelled, nogmaals cancellen gaat niet meer");
    }
}
