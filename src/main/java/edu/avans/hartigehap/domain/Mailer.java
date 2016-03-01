package edu.avans.hartigehap.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mailer extends Observer{
	private HallReservation hallreservation;
	/**
	 * 
	 */
	public Mailer(HallReservation hallreservation){
		this.hallreservation = hallreservation;
	}
	
	
	private static final long serialVersionUID = 1L;

	@Override
	public void notifyAllObservers() {
		if(hallreservation.getState() instanceof PaidState){
			System.out.println("Ik ben betaal, jippie! Stuur e-mail");
		}else if(hallreservation.getState() instanceof CreatedState){
			SubmittedMail submittedMail = new SubmittedMail();
			submittedMail.prepareMail("tomgiesbergen@live.nl","Reservering aangemaakt","Beste klant, de reservering is aangemaakt. We zien u graag verschijnen.");
		}else{
			System.out.println(hallreservation.getState().toString() + " is mijn status.");
		}
		
	}

}
