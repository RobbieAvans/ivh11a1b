package edu.avans.hartigehap.domain;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SubmittedState extends HallReservationState {
    private static final long serialVersionUID = 1L;

    public SubmittedState() {
        setStateAsId();
    }
    
    public SubmittedState(HallReservation hallReservation) {
        super(hallReservation);
        setStateAsId();
    }

    private void setStateAsId() {
        setId("SubmittedState");
    }
    
    @Override
    public String strMailBody() {
        return "Beste %voornaam%, de reservering is aangemaakt";
    }

    @Override
    public String strMailSubject() {
        return "Reservering is aangemaakt";
    }

    @Override
    public void submitReservation() {
        System.out.println("Je bent al gesubmitted, nogmaals submitten gaat niet meer");
    }

    @Override
    public void payReservation() {
        getCurrentHallReservation().setState(getCurrentHallReservation().getPaidState());
        getCurrentHallReservation().notifyAllObservers();
    }

    @Override
    public void cancelReservation() {
        getCurrentHallReservation().setState(getCurrentHallReservation().getCancelledState());
        getCurrentHallReservation().notifyAllObservers();
    }
}
