package edu.avans.hartigehap.domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public abstract class HallReservationState extends DomainObjectNaturalId {
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy="state")
    private Collection<HallReservation> hallReservations;
    
    @Transient
    private HallReservation currentHallReservation;

    public HallReservationState(HallReservation hallReservation) {
        currentHallReservation = hallReservation;
    }

    public abstract String strMailBody();

    public abstract String strMailSubject();

    public abstract void submitReservation();

    public abstract void payReservation();

    public abstract void cancelReservation();

    @Transient
    public String getState() {
        return getId();
    }
}
