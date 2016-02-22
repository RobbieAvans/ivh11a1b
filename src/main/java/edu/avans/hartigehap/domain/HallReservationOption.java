package edu.avans.hartigehap.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class HallReservationOption extends HallReservationDecorator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Transient
	private HallReservation hallReservation;
	
	@ManyToOne
	private HallOption hallOption;
	
	public HallReservationOption(HallReservation hallReservation, HallOption hallOption)
	{
		this.hallReservation = hallReservation;
		this.hallOption = hallOption;
	}
	
	@Override
	public Double getPrice() {
		return hallOption.getPrice() + hallReservation.getPrice();
	}

}
