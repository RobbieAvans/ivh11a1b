package edu.avans.hartigehap.domain;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class HallWithWifi extends HallReservationDecorator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Transient
	private HallReservation hallReservation;
	
	public HallWithWifi(HallReservation hallReservation)
	{
		this.hallReservation = hallReservation;
	}
	
	@Override
	public Double getPrice() {
		return 5.00 + hallReservation.getPrice();
	}

}
