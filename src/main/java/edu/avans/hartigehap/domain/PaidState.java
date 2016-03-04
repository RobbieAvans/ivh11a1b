package edu.avans.hartigehap.domain;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

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
		System.out.println("Je bent al betaald, submitten gaat niet meer");
	}

	@Override
	public void payReservation() {
		System.out.println("Je bent al betaald, nogmaals betalen gaat niet meer");
	}

	@Override
	public void cancelReservation() {
		hallReservation.setState(hallReservation.getCancelledState());
		hallReservation.notifyAllObservers();
		
	}
}
