package edu.avans.hartigehap.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Observer extends DomainObject{
	private static final long serialVersionUID = 1L;
	public abstract void notifyAllObservers(HallReservation hallReservation);
}
