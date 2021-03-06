package edu.avans.hartigehap.domain;

import edu.avans.hartigehap.domain.hallreservation.HallReservation;

public class Mailer implements Observer<HallReservation> {
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
    public void notify(HallReservation hallReservation) {
        SimpleMail sendMail = new SimpleMail();
        sendMail.prepareMail(hallReservation.getCustomer().getEmail(), "Wijziging reserverings-status",
                hallReservation.getState().name(), hallReservation.getCustomer().getFirstName());
    }
}