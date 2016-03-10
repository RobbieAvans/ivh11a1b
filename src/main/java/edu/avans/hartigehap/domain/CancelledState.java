package edu.avans.hartigehap.domain;

import javax.persistence.Entity;
import lombok.NoArgsConstructor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@NoArgsConstructor
public class CancelledState extends HallReservationState {
    private static final long serialVersionUID = 1L;

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
        log.debug("Je bent al gecancelled, submitten gaat niet meer.");
    }

    @Override
    public void cancelReservation() {
    	log.debug("Je bent al gecancelled, nogmaals cancellen gaat niet meer.");
    }
    
    @Override
    public void payReservation(){
    	log.debug("Je bent al gecancelled, betalen gaat niet meer.");
    }
}
