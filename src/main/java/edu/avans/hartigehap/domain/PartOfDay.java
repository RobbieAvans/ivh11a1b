package edu.avans.hartigehap.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public abstract class PartOfDay extends DomainObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date startTimeHour;
	private Date endTimeHour;
	private String description;

	@ManyToOne()
	private HallReservation hallReservation;

	public PartOfDay(Date date) {

	}

}
