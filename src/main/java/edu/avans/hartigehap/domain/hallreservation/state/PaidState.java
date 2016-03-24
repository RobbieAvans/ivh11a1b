package edu.avans.hartigehap.domain.hallreservation.state;

public class PaidState extends AbstractHallReservationStateOperations {

    public String strMailSubject() {
        return "Reservering is betaald";
    }

    public String strMailBody() {
        return "Beste %voornaam%, de reservering is betaald";
    }
}
