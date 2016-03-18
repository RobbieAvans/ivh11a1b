package edu.avans.hartigehap.domain.hallreservation.state;

import edu.avans.hartigehap.domain.hallreservation.HallReservation;

public interface HallReservationStateOperations {

    public void submit(HallReservation hallReservation);

    public void pay(HallReservation hallReservation);

    public void cancel(HallReservation hallReservation);
}