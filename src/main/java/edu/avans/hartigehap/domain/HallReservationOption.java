package edu.avans.hartigehap.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = {})
@NoArgsConstructor
public class HallReservationOption extends HallReservationDecorator {

    private static final long serialVersionUID = 1L;

    public HallReservationOption(HallReservation hallReservation, HallOption hallOption) {
        super(hallReservation, hallOption);
        setHall(hallReservation.getHall());
        setObservers(hallReservation.getObservers());
        setPartOfDays(hallReservation.getPartOfDays());
        setCustomer(hallReservation.getCustomer());
    }
}
