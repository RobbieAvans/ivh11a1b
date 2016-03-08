package edu.avans.hartigehap.domain;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class SubmittedState extends ReservationState {
	private static final long serialVersionUID = 1L;
	private HallReservation hallReservation;
	private String state = "SubmittedState";

	public SubmittedState(HallReservation hallReservation){
			this.hallReservation = hallReservation;
	}
	
	public String strMailBody() {
		return "Beste %voornaam%, de reservering is aangemaakt";
	}

	public String strMailSubject() {
		return "Reservering is aangemaakt";
	}

	@Override
	public void submitReservation() {
		log.debug("Je bent al gesubmitted, nogmaals submitten gaat niet meer");
	}

	@Override
	public void payReservation() {
		hallReservation.setState(hallReservation.getPaidState());
		hallReservation.notifyAllObservers();
	}

	@Override
	public void cancelReservation() {
		hallReservation.setState(hallReservation.getCancelledState());
		hallReservation.notifyAllObservers();
	}
}
