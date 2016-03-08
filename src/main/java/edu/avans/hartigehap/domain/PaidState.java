package edu.avans.hartigehap.domain;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Entity
@Getter
@Setter
public class PaidState extends HallReservationState {
    private static final long serialVersionUID = 1L;

    public PaidState() {
        setStateAsId();
    }

    public PaidState(HallReservation hallReservation) {
        super(hallReservation);
        setStateAsId();
    }

    private void setStateAsId() {
        setId("PaidState");
    }

	@Override
	public String strMailSubject() {
		return "Reservering is betaald";
	}
    
    @Override
    public String strMailBody() {
        return "Beste %voornaam%, de reservering is betaald";
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
