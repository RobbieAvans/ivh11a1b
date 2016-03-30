package edu.avans.hartigehap.domain.hallreservation.state;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import edu.avans.hartigehap.domain.hallreservation.HallReservation;

public class CancelledState extends AbstractHallReservationStateOperations {

    public CancelledState() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this); 
    }

    @Override
    public void undo(HallReservation hallReservation) {
        hallReservation.setState(HallReservationState.CONCEPT);
    }
    
    @Override
    public void confirm(HallReservation hallReservation) {
        hallReservation.delete();
    }
}
