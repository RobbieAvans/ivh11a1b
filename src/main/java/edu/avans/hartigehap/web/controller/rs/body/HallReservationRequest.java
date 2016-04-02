package edu.avans.hartigehap.web.controller.rs.body;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.domain.HallOption;
import edu.avans.hartigehap.domain.PartOfDay;
import edu.avans.hartigehap.domain.hallreservation.state.HallReservationState;
import edu.avans.hartigehap.service.CustomerService;
import edu.avans.hartigehap.service.HallOptionService;
import edu.avans.hartigehap.service.HallService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HallReservationRequest {
    @Autowired
    private HallService hallService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private HallOptionService hallOptionService;

    private String description;
    private HallReservationState state;
    private Long customer;
    private List<Long> hallOptions = new ArrayList<>();
    private Long hall;
    private List<PartOfDayRequest> partOfDays = new ArrayList<>();

    public HallReservationRequest() {
        // Needed to autowire, @Configurable not working
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @JsonIgnore
    public Hall getHallObject() throws InvalidJsonRequestException {
        Hall foundHall = hallService.findById(hall);

        if (foundHall == null)
            throw new InvalidJsonRequestException("hall_not_exists");

        return foundHall;
    }

    @JsonIgnore
    public List<HallOption> getHallOptionObjects() {
        return hallOptionService.findByIds(hallOptions);
    }

    @JsonIgnore
    public Customer getCustomerObject() throws InvalidJsonRequestException {
        Customer foundCustomer = customerService.findById(customer);

        if (foundCustomer == null)
            throw new InvalidJsonRequestException("customer_not_exists");

        return foundCustomer;
    }

    @JsonIgnore
    public List<PartOfDay> getPartOfDaysObjects() throws InvalidJsonRequestException {
        // Create an ordered list for the partOfDays
        List<PartOfDay> partOfDaysList = new ArrayList<>();
        for (PartOfDayRequest partOfDayRequest : partOfDays) {
            partOfDaysList.add(partOfDayRequest.getPartOfDayObject());
        }

        partOfDaysList = PartOfDay.orderListAsc(partOfDaysList);

        // Check if there is at least one partOfDay
        if (partOfDaysList.isEmpty()) {
            throw new InvalidJsonRequestException("partofdays_is_required");
        }

        // Check if the order for the partOfDays is correct
        for (int i = 0; i < partOfDaysList.size(); i++) {
            PartOfDay newPartOfDay = partOfDaysList.get(i);
            if (i > 0 && !partOfDaysList.get(i - 1).canAddAfter(newPartOfDay)) {
                // Check if it is possible to add it
                throw new InvalidJsonRequestException("partofdays_invalid_order");
            }
        }

        // Check if the partOfDays are in the future
        if (!partOfDaysList.get(0).getStartTime().after(new Date())) {
            throw new InvalidJsonRequestException("partofdays_in_past");
        }

        // Check if the partOfDays are available
        if (!hallService.isAvailableFor(getHallObject(), partOfDaysList)) {
            throw new InvalidJsonRequestException("hall_not_available");
        }

        return partOfDaysList;
    }
}