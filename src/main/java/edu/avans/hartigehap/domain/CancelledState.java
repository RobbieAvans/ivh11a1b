package edu.avans.hartigehap.domain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CancelledState extends AbstractHallReservationStateOperations {

    public String strMailBody() {
        return "Beste %voornaam%, de reservering is geannuleerd";
    }

    public String strMailSubject() {
        return "Annulering van reservering";
    }

    @Override
    public void submit(HallReservation hallReservation) {
        log.debug("Je bent al gecancelled, submitten gaat niet meer.");
    }

    @Override
    public void cancel(HallReservation hallReservation) {
        log.debug("Je bent al gecancelled, nogmaals cancellen gaat niet meer.");
    }

    @Override
    public void pay(HallReservation hallReservation) {
        log.debug("Je bent al gecancelled, betalen gaat niet meer.");
    }
}
