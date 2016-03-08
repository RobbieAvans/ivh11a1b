package edu.avans.hartigehap.domain;

import javax.persistence.Entity;


import lombok.Getter;
import lombok.Setter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Getter @Setter
public class CancelledState extends ReservationState{
	private static final long serialVersionUID = 1L;
	private HallReservation hallReservation;
	private String state = "CancelledState";

	public CancelledState(HallReservation hallReservation){
			this.hallReservation = hallReservation;
	}
	
	public String strMailBody() {
		return "Beste %voornaam%, de reservering is geannuleerd";
	}

	public String strMailSubject() {
		return "Annulering van reservering";
	}

	@Override
	public void submitReservation() {
		log.debug("Je bent al gecancelled, submitten gaat niet meer");
	}

	@Override
	public void payReservation() {
		hallReservation.setState(hallReservation.getPaidState());
		hallReservation.notifyAllObservers();
	}

	@Override
	public void cancelReservation() {
		log.debug("Je bent al gecancelled, nogmaals cancellen gaat niet meer");
	}


}
