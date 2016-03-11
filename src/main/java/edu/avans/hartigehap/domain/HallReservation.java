package edu.avans.hartigehap.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Tom GIesbergen
 */

@Entity
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = { "description" })
@NoArgsConstructor
public abstract class HallReservation extends DomainObject {

    private static final long serialVersionUID = 1L;
    private String description;

    @Transient
    private HallReservationState cancelledState = new CancelledState(this);

    @Transient
    private HallReservationState paidState = new PaidState(this);

    @Transient
    private HallReservationState submittedState = new SubmittedState(this);

    @OneToOne(cascade = CascadeType.ALL)
    private HallReservationState state;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private HallOption hallOption;

    @ManyToOne
    private Hall hall;

    @Transient
    private List<Observer> observers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hallReservation")
    private List<PartOfDay> partOfDays = new ArrayList<>();

    public HallReservation(Hall hall, HallOption hallOption) {
        this.hall = hall;
        this.hallOption = hallOption;

        // Default is submittedState ??
        this.state = submittedState;
    }
    
    public HallReservation(Hall hall) {
        this(hall, null);
    }
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void addPartOfDay(PartOfDay partOfDay) {
        partOfDays.add(partOfDay);
    }

    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.notifyAllObservers(this);
        }
    }

    public void setState(HallReservationState state) {
        this.state = state;
    }

    public void submitReservation() {
        state.submitReservation();
    }

    public void payReservation() {
        state.payReservation();
    }

    public void cancelReservation() {
        state.cancelReservation();
    }

    @Transient
    public Double getPrice() {
        Double price = 0.0;
        
        if (hallOption != null) {
            price += hallOption.getPrice(); 
        }
        
        if (hall != null) {
            price += hall.getPrice();
        }
        
        return price;
    }

    @Transient
    public List<HallOption> getHallOptions() {
        List<HallOption> hallOptions = new ArrayList<>();
        
        // Only add the hallOption when it is set
        if (hallOption != null) {
            hallOptions.add(hallOption); 
        }
        
        return hallOptions;
    }

    /**
     * TODO: Should return whether the reservation is active or not. e.g. is in
     * the future and has not the CancelledState
     * 
     * @return
     */
    public boolean isActive() {
        return true;
    }
}
