package edu.avans.hartigehap.domain.hallreservation.state;

import edu.avans.hartigehap.domain.StateException;
import edu.avans.hartigehap.domain.hallreservation.HallReservation;

public interface HallReservationStateOperations {

    public void confirm(HallReservation hallReservation)  throws StateException;

    public void pay(HallReservation hallReservation) throws StateException;

    public void cancel(HallReservation hallReservation) throws StateException;
    
    public void undo(HallReservation hallReservation) throws StateException;
}