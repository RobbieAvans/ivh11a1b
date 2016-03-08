package edu.avans.hartigehap.domain;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import lombok.NoArgsConstructor;



@Slf4j
@Entity
@NoArgsConstructor
public class SubmittedState extends HallReservationState {
    private static final long serialVersionUID = 1L;
    
    public SubmittedState(HallReservation hallReservation) {
        super(hallReservation);
    }
    
    @Override
    public String strMailBody() {
        return "Beste %voornaam%, de reservering is aangemaakt";
    }

    @Override
    public String strMailSubject() {
        return "Reservering is aangemaakt";
    }

    @Override
    public void submitReservation() {
    	log.debug("Je bent al gesubmitted, nogmaals submitten gaat niet meer");
    }
    @Override
	public void payReservation() {
		super.payReservation();
	}

	@Override
	public void cancelReservation() {
		super.cancelReservation();
	}
}
