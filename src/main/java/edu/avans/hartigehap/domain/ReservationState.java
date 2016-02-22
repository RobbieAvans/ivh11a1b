package edu.avans.hartigehap.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
//optional
@Table(name = "RESERVATIONSTATE")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true)

public abstract class ReservationState extends DomainObject {
	private static final long 	serialVersionUID = 1L;
	
	public abstract void doAction(HallReservation hallReservation);
	
}
