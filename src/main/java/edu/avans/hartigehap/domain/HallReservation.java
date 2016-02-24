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
    private HallOption hallOption;
    
    @ManyToOne
    private Hall hall;
    
    @Transient
    private List<Observer> Observers  = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hallReservation")
	private List<PartOfDay> partOfDays = new ArrayList<>();
    
    public HallReservation(HallOption hallOption){
    	Observers.add(new Mailer(this));
    	this.hallOption = hallOption;
    }
    
    public void AddPartOfDay(PartOfDay partOfDay){
    	partOfDays.add(partOfDay);
    }
    
    public void NotifyAll(){
    	for (Observer observer : Observers) {
			observer.Notify();
		}
    }
    @Transient
    public Double getPrice(){
    	return this.hallOption.getPrice();
    }
}
