package edu.avans.hartigehap.domain.hallreservation.state;

import edu.avans.hartigehap.domain.hallreservation.HallReservation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PaidState extends AbstractHallReservationStateOperations {

    public String strMailSubject() {
        return "Reservering is betaald";
    }

    public String strMailBody() {
        return "Beste %voornaam%, de reservering is betaald";
    }

    @Override
    public void submit(HallReservation hallReservation) {
        log.debug("Je bent al betaald, submitten gaat niet meer");
    }

    @Override
    public void pay(HallReservation hallReservation) {
        log.debug("Je bent al betaald, nogmaals betalen gaat niet meer");
    }

    @Override
    public void cancel(HallReservation hallReservation) {
        log.debug("Je bent al betaald, cancellen gaat niet meer");
    }

}
