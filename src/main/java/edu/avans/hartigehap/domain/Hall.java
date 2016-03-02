package edu.avans.hartigehap.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Hall extends DomainObject {
	private static final long serialVersionUID = 1L;

	private int numberOfSeats;
	private String description;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "hall")
	private Collection<HallReservation> reservations = new ArrayList<HallReservation>();

	public Hall(String description, int numberOfSeats) {
		this.description = description;
		this.numberOfSeats = numberOfSeats;
	}

	public Hall addReservation(HallReservation hallReservation) {
		reservations.add(hallReservation);

		return this;
	}
}
