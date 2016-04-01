package edu.avans.hartigehap.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.hallreservation.HallReservation;
import edu.avans.hartigehap.domain.strategy.HallPriceStrategy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@JsonIgnoreProperties({ "reservations" })
@Getter
@Setter
@NoArgsConstructor
public class Hall extends DomainObject {
    private static final long serialVersionUID = 1L;

    @Transient
    private HallPriceStrategy strategy;
    
    private int numberOfSeats;
    private String description;
    private double basePrice;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hall")
    private Collection<HallReservation> reservations = new ArrayList<HallReservation>();

    public Hall(String description, int numberOfSeats, double basePrice) {
        this.description = description;
        this.numberOfSeats = numberOfSeats;
        this.basePrice = basePrice;
    }

    public Hall addReservation(HallReservation hallReservation) {
        reservations.add(hallReservation);
        hallReservation.setHall(this);

        return this;
    }

    public Hall removeReservation(HallReservation hallReservation) {
        reservations.remove(hallReservation);
        
        return this;
    }
    
    @Transient
    @JsonIgnore
    public double getPriceInVat() {
    	return strategy.calculateInVat(this);
    }
    
    @Transient
    @JsonIgnore
    public double getPriceExVat() {
    	return strategy.calculateExVat(this);
    }
    
    /**
     * Check if the hall has activeReservations at the moment
     * 
     * @return
     */
    public boolean hasActiveReservations() {
        boolean hasActive = false;
        for (HallReservation hallReservation : reservations) {
            if (hallReservation.isActive()) {
                hasActive = true;
                break;
            }
        }

        return hasActive;
    }

    /**
     * Check if the hall can be safely deleted
     * 
     * @return
     */
    public boolean canBeDeleted() {
        return !hasActiveReservations();
    }
    
    /**
     * Touch the hallReservations so they will be loaded
     */
    public void touchHallReservations() {
        reservations.size();
    }
}
