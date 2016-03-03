package edu.avans.hartigehap.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
			log.debug("Ik ben betaal, jippie! Stuur e-mail");
		} else if (hallReservation.getState() instanceof CreatedState) {
			SubmittedMail submittedMail = new SubmittedMail();
			submittedMail.prepareMail("tomgiesbergen@live.nl", "Reservering aangemaakt",
					"Beste klant, de reservering is aangemaakt. We zien u graag verschijnen.");
		} else {
			log.debug(hallReservation.getState().toString() + " is mijn status.");
		}

	}

}
