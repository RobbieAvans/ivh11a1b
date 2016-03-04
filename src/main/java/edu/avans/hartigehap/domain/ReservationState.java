package edu.avans.hartigehap.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
public abstract class ReservationState extends DomainObject {
	private static final long serialVersionUID = 1L;
	private static String state;
	
	public abstract String strMailBody();
	public abstract String strMailSubject();
	
	
	public abstract void submitReservation();
	public abstract void payReservation();
	public abstract void cancelReservation();
	public String getState() {
		return state;
	}

}
