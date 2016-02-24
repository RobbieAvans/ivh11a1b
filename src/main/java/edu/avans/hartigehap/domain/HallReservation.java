package edu.avans.hartigehap.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Tom GIesbergen
 */

@Entity
//optional
@Table(name = "HALLRESERVATION")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = {"description"})
public abstract class HallReservation extends DomainObject {
	
    private static final long 	serialVersionUID = 1L;
    private String 				description;
    private Double				price;
    private ReservationState 	state;
    
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "hallReservation")
    //private List<HallReservationSpecifications> hallReservationSpecifications = new ArrayList<>();
    
    @OneToMany
    private List<Observer> Observers;
    
    @OneToMany
	private List<PartOfDay> partOfDays = new ArrayList<>();
    
    public HallReservation(){
    	Observers = new ArrayList<>();
    	Observers.add(new Mailer(this));
    }
    
    public void AddPartOfDay(PartOfDay partOfDay){
    	partOfDays.add(partOfDay);
    }
    
    public void NotifyAll(){
    	for (Observer observer : Observers) {
			observer.Notify();
		}
    }
}
