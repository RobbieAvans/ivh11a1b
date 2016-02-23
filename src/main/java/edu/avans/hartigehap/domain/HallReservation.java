package edu.avans.hartigehap.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Tom GIesbergen
 */

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = {})
@NoArgsConstructor
public abstract class HallReservation extends DomainObject {
	private static final long serialVersionUID = 1L;
	private String description;
	private ReservationState state;

	@ManyToOne
	private HallOption hallOption;

	public HallReservation(HallOption hallOption) {
		this.hallOption = hallOption;
	}

	@Transient
	public Double getPrice() {
		return hallOption.getPrice();
	}
}