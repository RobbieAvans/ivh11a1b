package edu.avans.hartigehap.domain;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatedState extends RestervationState {
	private static final long serialVersionUID = 1L;
	private String description = "Created state";

	public void doAction(HallReservation hallReservation){
		hallReservation.setState(this);
	}
	
	public String toString(){
		return description;
	}

}
