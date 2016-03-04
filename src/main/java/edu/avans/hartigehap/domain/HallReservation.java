package edu.avans.hartigehap.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
//optional
@Table(name = "HALLRESERVATION")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = {"description"})
@NoArgsConstructor
public abstract class HallReservation extends DomainObject {
	
    private static final long 	serialVersionUID = 1L;
    private String 				description;
    private ReservationState 	state;
    
    @ManyToOne
    private Customer customer;
    
    @ManyToOne
    private HallOption hallOption;
    
    @ManyToOne
    private Hall hall;
    
    @Transient
    private List<Observer> observers  = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hallReservation")
	private List<PartOfDay> partOfDays = new ArrayList<>();
    
    public HallReservation(HallOption hallOption){
    	this.hallOption = hallOption;
    }
    
    public void addObserver(Observer observer){
    	observers.add(observer);
    }
    
    public void addPartOfDay(PartOfDay partOfDay){
    	partOfDays.add(partOfDay);
    }
    
    public void notifyAllObservers(){
    	for (Observer observer : observers) {
			observer.notifyAllObservers(this);
		}
    }
    
    @Transient
    public Double getPrice() {
    	return this.hallOption.getPrice();
    }
    
    /**
     * TODO: Should return whether the reservation is active or not.
     * e.g. is in the future and has not the CancelledState
     * 
     * @return
     */
    public boolean isActive() {
    	return true;
    }
}
