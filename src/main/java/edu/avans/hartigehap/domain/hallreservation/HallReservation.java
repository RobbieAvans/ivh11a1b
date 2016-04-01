package edu.avans.hartigehap.domain.hallreservation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.DomainObject;
import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.domain.HallOption;
import edu.avans.hartigehap.domain.Observer;
import edu.avans.hartigehap.domain.PartOfDay;
import edu.avans.hartigehap.domain.StateException;
import edu.avans.hartigehap.domain.hallreservation.state.HallReservationState;
import edu.avans.hartigehap.domain.strategy.HallReservationPriceStrategy;
import edu.avans.hartigehap.service.HallReservationService;
import edu.avans.hartigehap.service.HallService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Tom GIesbergen
 */

@Configurable // Needed to autowire HallReservationService and hallService
@Entity
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = { "description" })
public abstract class HallReservation extends DomainObject {

    private static final long serialVersionUID = 1L;
    
    @Transient
    private HallReservationPriceStrategy strategy;
    
    @Transient
    @Autowired
    private HallReservationService hallReservationService;
    
    @Transient
    @Autowired
    private HallService hallService;
    
    private String description;

    @Enumerated(EnumType.STRING)
    private HallReservationState state;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Customer customer;

    @ManyToOne
    private Hall hall;

    @Transient
    private List<Observer<HallReservation>> observers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hallReservation", orphanRemoval = true)
    private List<PartOfDay> partOfDays = new ArrayList<>();

    public HallReservation() {
        this.state = HallReservationState.CONCEPT;
    }

    public void addObserver(Observer<HallReservation> observer) {
        observers.add(observer);
    }

    public void addPartOfDay(PartOfDay partOfDay) {
        partOfDays.add(partOfDay);
        partOfDay.setHallReservation(this);
    }

    public List<PartOfDay> getPartOfDays() {
        return PartOfDay.orderListAsc(partOfDays);
    }
    
    public void notifyAllObservers() {
        for (Observer<HallReservation> observer : observers) {
            observer.notify(this);
        }
    }

    public void setState(HallReservationState state) {
        this.state = state;
        notifyAllObservers();
    }

    public void confirm() throws StateException {
        state.confirm(this);
     }

    public void pay() throws StateException {
        state.pay(this);
    }

    public void cancel() throws StateException {
        state.cancel(this);
    }

    public void undo() throws StateException {
        state.undo(this);
    }
    
    @Transient
    /**
     * This method should only be called on the last method in the decorater.
     * Otherwise a nullpointerexception might be thrown
     * 
     * @param strategy
     * @return
     */
    public double getPriceInVat() {
        return strategy.calculateInVat(this);
    }

    public double getPriceExVat() {
        return strategy.calculateExVat(this);
    }
    
    @Transient
    public List<HallOption> getHallOptions() {
        return new ArrayList<>();
    }

    public void reset() {
        description = null;
        customer = null;
        hall = null;
        partOfDays.clear();
    }
    
    /**
     * This method is necessary because we want to be able to delete a reservation
     * from a hallReservationState. And it is not possible to autowire the service
     * into the state class because the state class is created with new. Also 
     * aspectj weaving is not possible because the states are enum?
     * 
     * https://eclipse.org/aspectj/doc/released/adk15notebook/enums-in-aspectj5.html
     * http://www.nurkiewicz.com/2009/10/ddd-in-spring-made-easy-with-aspectj.html
     * 
     */
    public void delete() {
        hallReservationService.delete(this);
    }
    
    /**
     * Same as delete method. We want to be able to save the reservation from a state class.
     */
    public void save() {
		//hall.touchHallReservations();
		hallService.save(hall);
    }
    
    /**
     * Should return whether the reservation is active or not. e.g. is in
     * the future and has not the CancelledState
     * 
     * @return
     */
    public boolean isActive() {
        return !getState().equals(HallReservationState.CANCELLED) && (getEndTime().after(new Date()));
    }
    
    @Transient
    public Date getStartTime() {
        return getPartOfDays().get(0).getStartTime();
    }
    
    @Transient
    public Date getEndTime() {
        List<PartOfDay> partOfDays = getPartOfDays();
        return partOfDays.get(partOfDays.size() - 1).getEndTime();
    }
    
    public boolean canBeModified() {
        return !getState().equals(HallReservationState.FINAL) && !getState().equals(HallReservationState.PAID) && getStartTime().after(new Date());
    }
}
