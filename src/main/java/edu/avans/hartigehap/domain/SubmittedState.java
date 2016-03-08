package edu.avans.hartigehap.domain;

import javax.persistence.Entity;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class SubmittedState extends HallReservationState {
    private static final long serialVersionUID = 1L;
    
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
}
