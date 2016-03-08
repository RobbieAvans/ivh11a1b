package edu.avans.hartigehap.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@JsonIgnoreProperties({"reservations"})
@Getter
@Setter
@NoArgsConstructor
public class Hall extends DomainObject {
    private static final long serialVersionUID = 1L;

    private int numberOfSeats;
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hall")
    private Collection<HallReservation> reservations = new ArrayList<HallReservation>();

    public Hall(String description, int numberOfSeats) {
        this.description = description;
        this.numberOfSeats = numberOfSeats;
    }

    public Hall addReservation(HallReservation hallReservation) {
        reservations.add(hallReservation);
        hallReservation.setHall(this);
        
        return this;
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
}
