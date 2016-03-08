package edu.avans.hartigehap.domain;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HallReservationAPIWrapper {

    private HallReservation hallReservation;
    private Collection<HallOption> hallOptions;
    private String startDate;
    private String endDate;

}
