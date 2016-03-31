package edu.avans.hartigehap.domain.hallreservation.state;

import edu.avans.hartigehap.domain.StateException;
import edu.avans.hartigehap.domain.hallreservation.HallReservation;

/**
 * http://www.nurkiewicz.com/2009/09/state-pattern-introducing-domain-driven.html
 * 
 * @author robbie
 *
 */
public enum HallReservationState {

    CONCEPT(new ConceptState()),

    FINAL(new FinalState()),

    PAID(new PaidState()),

    CANCELLED(new CancelledState());
    
    private final HallReservationStateOperations operations;
    
    HallReservationState(HallReservationStateOperations operations) {
       this.operations = operations;
    }

    public void confirm(HallReservation hallReservation) throws StateException {
        operations.confirm(hallReservation);
    }

    public void pay(HallReservation hallReservation) throws StateException {
        operations.pay(hallReservation);
    }

    public void cancel(HallReservation hallReservation) throws StateException {
        operations.cancel(hallReservation);
    }
    
    public void undo(HallReservation hallReservation) throws StateException {
        operations.undo(hallReservation);
    }
    
    public String[] getPossibleActions() {
    	return operations.possibleActions();
    }
}