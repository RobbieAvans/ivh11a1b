package edu.avans.hartigehap.domain.hallreservation.state;

import edu.avans.hartigehap.domain.hallreservation.HallReservation;

public abstract class AbstractHallReservationStateOperations implements HallReservationStateOperations {
    public abstract String strMailBody();

    public abstract String strMailSubject();

    @Override
    public void pay(HallReservation hallReservation) {
        hallReservation.setState(HallReservationState.PAID);
    }

    @Override
    public void cancel(HallReservation hallReservation) {
        hallReservation.setState(HallReservationState.CANCELLED);
    }
}
