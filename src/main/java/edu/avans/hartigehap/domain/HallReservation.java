package edu.avans.hartigehap.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    @Enumerated(EnumType.STRING)
    private HallReservationState state;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Hall hall;

    @Transient
    private List<Observer> observers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hallReservation")
    private List<PartOfDay> partOfDays = new ArrayList<>();

    public HallReservation(Hall hall) {
        this.hall = hall;

        // Default is submittedState ??
        this.state = HallReservationState.SUBMITTED;
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void addPartOfDay(PartOfDay partOfDay) {
        partOfDays.add(partOfDay);
    }

    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.notify(this);
        }
    }

    public void setState(HallReservationState state) {
        this.state = state;
        notifyAllObservers();
    }

    public void submitReservation() {
        state.submit(this);
    }

    public void payReservation() {
        state.pay(this);
    }

    public void cancelReservation() {
        state.cancel(this);
    }

    @Transient
    public Double getPrice() {
        Double price = 0.0;

        if (hall != null) {
            for (PartOfDay partOfDay : partOfDays) {
                price += (getHall().getPrice() * partOfDay.getPriceFactor());
            }
        }

        return price;
    }

    @Transient
    public List<HallOption> getHallOptions() {
        return new ArrayList<>();
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
