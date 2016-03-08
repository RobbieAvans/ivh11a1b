package edu.avans.hartigehap.domain;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class HallReservationAPIWrapper {

    private Long id;
    private String description;
    private HallReservationState state;
    private Customer customer;
    private Collection<HallOption> hallOptions;
    private Hall hall;
    private Collection<PartOfDay> partOfDays;

    public HallReservationAPIWrapper(HallReservation hallReservation) {
        id = hallReservation.getId();
        description = hallReservation.getDescription();
        state = hallReservation.getState();
        customer = hallReservation.getCustomer();
        hall = hallReservation.getHall();
        partOfDays = hallReservation.getPartOfDays();
        hallOptions = hallReservation.getHallOptions();
    }
}
