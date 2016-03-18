package edu.avans.hartigehap.domain;

public interface HallReservationStateOperations {

    public void submit(HallReservation hallReservation);

    public void pay(HallReservation hallReservation);

    public void cancel(HallReservation hallReservation);
}