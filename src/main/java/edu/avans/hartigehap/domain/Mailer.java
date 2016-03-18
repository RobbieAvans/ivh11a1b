package edu.avans.hartigehap.domain;

import edu.avans.hartigehap.domain.hallreservation.HallReservation;

public class Mailer implements Observer {
    private static Mailer instance;

    private Mailer() {}

    public static Mailer getInstance() {
        if (instance == null) {
            instance = new Mailer();
        }
        return instance;
    }

    @Override
    public void notify(HallReservation hallReservation) {
//        SimpleMail sendMail = new SimpleMail();
//        sendMail.prepareMail(hallReservation.getCustomer().getEmail(), hallReservation.getState().strMailSubject(),
//                hallReservation.getState().strMailBody(), hallReservation.getCustomer().getFirstName());

    }
}