package edu.avans.hartigehap.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaidState extends ReservationState {
	private static final long serialVersionUID = 1L;
	private String description = "Paid state";

	public void doAction(HallReservation hallReservation){
		hallReservation.setState(this);
		hallReservation.notifyAllObservers();
	}
	
	public String toString(){
		return description;
	}
}
