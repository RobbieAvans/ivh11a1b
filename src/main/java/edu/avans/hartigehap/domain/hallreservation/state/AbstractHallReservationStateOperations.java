package edu.avans.hartigehap.domain.hallreservation.state;

import edu.avans.hartigehap.domain.StateException;
import edu.avans.hartigehap.domain.hallreservation.HallReservation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractHallReservationStateOperations implements HallReservationStateOperations {
	
    @Override
    public void confirm(HallReservation hallReservation) throws StateException {
        log.debug("Bevestigen is niet mogelijk bij deze state " + hallReservation.getState());
        throw new StateException("Invalid state transition");
    }
    
    @Override
    public void pay(HallReservation hallReservation) throws StateException {
        log.debug("Betalen is niet mogelijk bij deze state " + hallReservation.getState());
        throw new StateException("Invalid state transition");
    }

    @Override
    public void cancel(HallReservation hallReservation) throws StateException {
        log.debug("Cancellen is niet mogelijk bij deze state " + hallReservation.getState());
        throw new StateException("Invalid state transition");
    }
    
    @Override
    public void undo(HallReservation hallReservation) throws StateException {
        log.debug("Undo is niet mogelijk bij deze state " + hallReservation.getState());
        throw new StateException("Invalid state transition");
    }
    
    @Override
    public String[] possibleActions() {
    	return new String[] {};
    }
}
