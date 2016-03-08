package edu.avans.hartigehap.domain;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class PaidState extends ReservationState {
	private static final long serialVersionUID = 1L;
	private HallReservation hallReservation;
	private String state = "PaidState";

	public PaidState(HallReservation hallReservation){
			this.hallReservation = hallReservation;
	}
	
	public String strMailBody() {
		return "Beste %voornaam%, de reservering is betaald";
	}

	public String strMailSubject() {
		return "Reservering is Betaald";
	}

	@Override
	public void submitReservation() {
		log.debug("Je bent al betaald, submitten gaat niet meer");
	}

	@Override
	public void payReservation() {
		log.debug("Je bent al betaald, nogmaals betalen gaat niet meer");
	}

	@Override
	public void cancelReservation() {
		log.debug("Je bent al betaald, cancellen gaat niet meer");
	}
}
