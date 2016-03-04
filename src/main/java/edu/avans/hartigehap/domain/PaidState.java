package edu.avans.hartigehap.domain;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PaidState extends ReservationState {
    private static final long serialVersionUID = 1L;

    private String state = "PaidState";

    public PaidState(HallReservation hallReservation) {
        super(hallReservation);
    }

    @Override
    public String strMailBody() {
        return "Beste %voornaam%, de reservering is betaald";
    }

    @Override
    public String strMailSubject() {
        return "Reservering is Betaald";
    }

    @Override
    public void submitReservation() {
        System.out.println("Je bent al betaald, submitten gaat niet meer");
    }

    @Override
    public void payReservation() {
        System.out.println("Je bent al betaald, nogmaals betalen gaat niet meer");
    }

    @Override
    public void cancelReservation() {
        getHallReservation().setState(getHallReservation().getCancelledState());
        getHallReservation().notifyAllObservers();

    }
}
