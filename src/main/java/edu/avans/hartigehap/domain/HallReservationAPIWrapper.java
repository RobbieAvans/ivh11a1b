package edu.avans.hartigehap.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@NoArgsConstructor
public class HallReservationAPIWrapper {

    private Long id = null;
    private String description;
    private HallReservationState state = null;
    private Customer customer = null;
    private List<HallOption> hallOptions;
    private Hall hall;
    private List<PartOfDay> partOfDays = new ArrayList<>();

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
