package edu.avans.hartigehap.domain.hallreservation.state;

import edu.avans.hartigehap.domain.hallreservation.HallReservation;

public class CancelledState extends AbstractHallReservationStateOperations {

    @Override
    public void undo(HallReservation hallReservation) {
        hallReservation.setState(HallReservationState.CONCEPT);
        hallReservation.save();
    }

    @Override
    public void confirm(HallReservation hallReservation) {
        hallReservation.delete();
    }

    public String[] possibleActions() {
        return new String[] { "confirm", "undo" };
    }
}
