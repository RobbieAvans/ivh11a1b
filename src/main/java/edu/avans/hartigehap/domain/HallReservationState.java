package edu.avans.hartigehap.domain;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@JsonIgnoreProperties({ "currentHallReservation" })
@Getter
@Setter
@NoArgsConstructor
public abstract class HallReservationState extends DomainObject {
    private static final long serialVersionUID = 1L;

    @Transient
    private HallReservation currentHallReservation;

    public HallReservationState(HallReservation hallReservation) {
        currentHallReservation = hallReservation;
    }

    public abstract String strMailBody();

    public abstract String strMailSubject();

    public abstract void submitReservation();

    public void payReservation() {
        getCurrentHallReservation().setState(getCurrentHallReservation().getPaidState());
    }

    public void cancelReservation() {
        getCurrentHallReservation().setState(getCurrentHallReservation().getCancelledState());
    }

    public String getState() {
        return this.getClass().getSimpleName();
    }
}
