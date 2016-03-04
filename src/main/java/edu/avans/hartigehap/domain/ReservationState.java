package edu.avans.hartigehap.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public abstract class ReservationState extends DomainObject {
    private static final long serialVersionUID = 1L;

    @OneToOne
    private HallReservation hallReservation;

    public ReservationState(HallReservation hallReservation) {
        this.hallReservation = hallReservation;
    }

    public abstract String strMailBody();

    public abstract String strMailSubject();

    public abstract void submitReservation();

    public abstract void payReservation();

    public abstract void cancelReservation();

    public abstract String getState();
}
