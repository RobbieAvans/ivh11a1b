package edu.avans.hartigehap.domain;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CancelledState extends ReservationState {
    private static final long serialVersionUID = 1L;

    private String state = "CancelledState";

    public CancelledState(HallReservation hallReservation) {
        super(hallReservation);
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
        getHallReservation().setState(getHallReservation().getPaidState());
        getHallReservation().notifyAllObservers();
    }

    @Override
    public void cancelReservation() {
        System.out.println("Je bent al gecancelled, nogmaals cancellen gaat niet meer");
    }

}
