package edu.avans.hartigehap.domain;

/**
 * http://www.nurkiewicz.com/2009/09/state-pattern-introducing-domain-driven.html
 * 
 * @author robbie
 *
 */
public enum HallReservationState implements HallReservationStateOperations {

    SUBMITTED(new SubmittedState()),

    PAID(new PaidState()),

    CANCELLED(new CancelledState());
    
    private final HallReservationStateOperations operations;
    
    HallReservationState(HallReservationStateOperations operations) {
       this.operations = operations;
    }

    @Override
    public void submit(HallReservation hallReservation) {
        operations.submit(hallReservation);
    }

    @Override
    public void pay(HallReservation hallReservation) {
        operations.pay(hallReservation);
    }

    @Override
    public void cancel(HallReservation hallReservation) {
        operations.cancel(hallReservation);
    }
}