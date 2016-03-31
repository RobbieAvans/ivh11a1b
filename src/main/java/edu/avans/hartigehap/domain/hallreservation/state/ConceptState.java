package edu.avans.hartigehap.domain.hallreservation.state;

import edu.avans.hartigehap.domain.hallreservation.HallReservation;

public class ConceptState extends AbstractHallReservationStateOperations {

    @Override
    public void confirm(HallReservation hallReservation) {
        hallReservation.setState(HallReservationState.FINAL);
        hallReservation.save();
    }
    
    @Override
    public void cancel(HallReservation hallReservation) {
        hallReservation.setState(HallReservationState.CANCELLED);
        hallReservation.save();
    }
    
    @Override
    public String[] possibleActions() {
    	return new String[] { "confirm", "cancel" } ;
    }
}
