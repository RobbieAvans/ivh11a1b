package edu.avans.hartigehap.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * 
 * @author Tom GIesbergen
 */

@Entity
//optional
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
public class HallReservationOption extends HallDecorator {
	private static final long serialVersionUID = 1L;
	
	@Transient 
	HallReservation 	hallReservation;
	
	HallOption			hallOption;
	
	public HallReservationOption(HallReservation hallReservation, HallOption hallOption){
		this.hallReservation 	= hallReservation;
		this.hallOption 		= hallOption;
	}
	
	public Double cost(){
		hallReservation.setPrice(hallOption.getPrice());
		return hallReservation.cost();
	}
}
