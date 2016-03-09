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
    private List<HallOption> hallOptions = new ArrayList<>();
    private Hall hall;
    private List<PartOfDay> partOfDays = new ArrayList<>();

    public HallReservationAPIWrapper(HallReservation hallReservation) {
        id = hallReservation.getId();
        description = hallReservation.getDescription();
        state = hallReservation.getState();
        
        /**
         * We have to clone some objects because they can exist multiple times
         * in the json. In this way the have all a unique json @id property
         */
        
        // Clone the customer
        Customer customer = hallReservation.getCustomer();
        if (customer != null) {
            Customer cloneCustomer = new Customer(customer.getFirstName(), customer.getLastName(),
                    customer.getEmail(), customer.getBirthDate(), customer.getPartySize(), customer.getDescription(),
                    customer.getPhoto());
            cloneCustomer.setId(customer.getId());
            
            this.customer = cloneCustomer;
        }
        
        // Clone the hall
        Hall hall = hallReservation.getHall();
        Hall cloneHall = new Hall(hall.getDescription(), hall.getNumberOfSeats());
        cloneHall.setId(hall.getId());
        
        this.hall = cloneHall;
        
        partOfDays = hallReservation.getPartOfDays();
        
        // Clone the hallOptions
        for (HallOption hallOption : hallReservation.getHallOptions()) {
            HallOption clone = new HallOption(hallOption.getDescription(), hallOption.getPrice());
            clone.setId(hallOption.getId());
            hallOptions.add(clone);
        }
    }
}
