package edu.avans.hartigehap.web.controller.rs.body;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.domain.HallOption;
import edu.avans.hartigehap.domain.PartOfDay;
import edu.avans.hartigehap.domain.hallreservation.HallReservation;
import edu.avans.hartigehap.domain.hallreservation.state.HallReservationState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class HallReservationResponse {
    
    private Long id;
    private String description;
    private HallReservationState state;
    private Customer customer;
    private List<HallOption> hallOptions = new ArrayList<>();
    private Hall hall;
    private List<PartOfDayRequest> partOfDays = new ArrayList<>();
    private String[] actions;

    public HallReservationResponse(HallReservation hallReservation) {
        id = hallReservation.getId();
        description = hallReservation.getDescription();
        state = hallReservation.getState();
        actions = state.getPossibleActions();
        
        /**
         * We have to clone some objects because they can exist multiple times
         * in the json. In this way the have all a unique json @id property
         */

        // Clone the customer
        Customer hallReservationCustomer = hallReservation.getCustomer();
        if (hallReservationCustomer != null) {
            Customer cloneCustomer = new Customer(hallReservationCustomer.getFirstName(), hallReservationCustomer.getLastName(), hallReservationCustomer.getEmail(),
                    hallReservationCustomer.getBirthDate(), hallReservationCustomer.getPartySize(), hallReservationCustomer.getDescription(), hallReservationCustomer.getPhoto());
            cloneCustomer.setId(hallReservationCustomer.getId());

            this.customer = cloneCustomer;
        }

        // Clone the hall
        Hall hallReservationHall = hallReservation.getHall();
        Hall cloneHall = new Hall(hallReservationHall.getDescription(), hallReservationHall.getNumberOfSeats(), hallReservationHall.getPrice());
        cloneHall.setId(hallReservationHall.getId());

        this.hall = cloneHall;

        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        for (PartOfDay partOfDay : hallReservation.getPartOfDays()) {
            partOfDays.add(new PartOfDayRequest(format.format(partOfDay.getStartTime()),partOfDay.getDescription()));
        }

        // Clone the hallOptions
        for (HallOption hallOption : hallReservation.getHallOptions()) {
            HallOption clone = new HallOption(hallOption.getDescription(), hallOption.getPrice());
            clone.setId(hallOption.getId());
            hallOptions.add(clone);
        }
    }
}