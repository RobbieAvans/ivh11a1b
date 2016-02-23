package edu.avans.hartigehap.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CancelledState extends ReservationState{
	private static final long serialVersionUID = 1L;
	private String description = "Cancelled state";
	
	@Override
	public void doAction(HallReservation hallReservation) {
		hallReservation.setState(this);
		
	}
	
	public String toString(){
		return description;
	}
}
