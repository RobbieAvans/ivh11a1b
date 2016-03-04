package edu.avans.hartigehap.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	
	@Transient
    ReservationState cancelledState;
	@Transient
    ReservationState paidState;
	@Transient
    ReservationState submitedState;
	
    private static final long 	serialVersionUID 	= 1L;
    private String 				description;
    private String				strState;
    
    @Transient
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
    	cancelledState 	= new CancelledState(this);
    	submitedState 	= new SubmittedState(this);
    	paidState 		= new PaidState(this);
    	state 			= submitedState;
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
    
	public void submitReservation() {
		state.submitReservation();
		this.strState = state.getState();
	}

	public void payReservation() {
		state.payReservation();
		this.strState = state.getState();
	}

	public void cancelReservation() {
		state.cancelReservation();
		this.strState = state.getState();
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
