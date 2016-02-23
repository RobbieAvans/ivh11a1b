package edu.avans.hartigehap.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
public class Mailer extends Observer{
	private HallReservation hallreservation;
	/**
	 * 
	 */
	public Mailer(HallReservation hallreservation){
		this.hallreservation = hallreservation;
	}
	
	
	private static final long serialVersionUID = 1L;

	@Override
	public void Notify() {
		System.out.println("Nieuwe Status: "+ hallreservation.getState().toString());
		
	}

}
