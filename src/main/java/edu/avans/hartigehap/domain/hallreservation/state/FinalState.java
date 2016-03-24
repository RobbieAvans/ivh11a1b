package edu.avans.hartigehap.domain.hallreservation.state;

import edu.avans.hartigehap.domain.hallreservation.HallReservation;

public class FinalState extends AbstractHallReservationStateOperations {

    @Override
    public String strMailBody() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String strMailSubject() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void pay(HallReservation hallReservation) {
        hallReservation.setState(HallReservationState.PAID);
    }
}
