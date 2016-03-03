package edu.avans.hartigehap.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mailer extends Observer {
	private static final long serialVersionUID = 1L;
	private static Mailer instance = new Mailer();
	
	public static Mailer getInstance(){
	      return instance;
	}

	@Override
	public void notifyAllObservers(HallReservation hallReservation) {
		if (hallReservation.getState() instanceof PaidState) {
			System.out.println("Ik ben betaal, jippie! Stuur e-mail");
		} else if (hallReservation.getState() instanceof CreatedState) {
			SubmittedMail submittedMail = new SubmittedMail();
			submittedMail.prepareMail("tomgiesbergen@live.nl", "Reservering aangemaakt",
					"Beste klant, de reservering is aangemaakt. We zien u graag verschijnen.");
		} else {
			System.out.println(hallReservation.getState().toString() + " is mijn status.");
		}

	}

}
