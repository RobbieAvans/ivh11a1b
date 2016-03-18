package edu.avans.hartigehap.domain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubmittedState extends AbstractHallReservationStateOperations {

    public String strMailBody() {
        return "Beste %voornaam%, de reservering is aangemaakt";
    }

    public String strMailSubject() {
        return "Reservering is aangemaakt";
    }

    @Override
    public void submit(HallReservation hallReservation) {
        log.debug("Je bent al gesubmitted, nogmaals submitten gaat niet meer");
    }
}
