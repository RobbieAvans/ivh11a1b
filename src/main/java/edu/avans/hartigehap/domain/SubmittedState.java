package edu.avans.hartigehap.domain;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SubmittedState extends ReservationState {
    private static final long serialVersionUID = 1L;

    private String state = "SubmittedState";

    public SubmittedState(HallReservation hallReservation) {
        super(hallReservation);
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
        getHallReservation().setState(getHallReservation().getPaidState());
        getHallReservation().notifyAllObservers();
    }

    @Override
    public void cancelReservation() {
        getHallReservation().setState(getHallReservation().getCancelledState());
        getHallReservation().notifyAllObservers();
    }
}
