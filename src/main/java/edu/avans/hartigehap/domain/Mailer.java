package edu.avans.hartigehap.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class Mailer extends Observer {
    private static final long serialVersionUID = 1L;
    private static Mailer instance = new Mailer();

    public static Mailer getInstance() {
        return instance;
    }

    @Override
    public void notifyAllObservers(HallReservation hallReservation) {
        SimpleMail sendMail = new SimpleMail();
        sendMail.prepareMail(hallReservation.getCustomer().getEmail(), hallReservation.getState().strMailSubject(),
                hallReservation.getState().strMailBody(), hallReservation.getCustomer().getFirstName());

    }

}
