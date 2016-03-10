package edu.avans.hartigehap.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mailer extends Observer {
    private static final long serialVersionUID = 1L;
    private static Mailer instance;

    private Mailer() {

    }

    public static Mailer getInstance() {
        if (instance == null) {
            instance = new Mailer();
        }
        return instance;
    }

    @Override
    public void notifyAllObservers(HallReservation hallReservation) {
        SimpleMail sendMail = new SimpleMail();
        sendMail.prepareMail(hallReservation.getCustomer().getEmail(), hallReservation.getState().strMailSubject(),
                hallReservation.getState().strMailBody(), hallReservation.getCustomer().getFirstName());

    }

}
