package edu.avans.hartigehap.domain.hallreservation.state;

import edu.avans.hartigehap.domain.hallreservation.HallReservation;

public class FinalState extends AbstractHallReservationStateOperations {
    
    @Override
    public void pay(HallReservation hallReservation) {
        hallReservation.setState(HallReservationState.PAID);
        hallReservation.save();
    }
    
    @Override
    public String[] possibleActions() {
    	return new String[] { "pay" } ;
    }
}
