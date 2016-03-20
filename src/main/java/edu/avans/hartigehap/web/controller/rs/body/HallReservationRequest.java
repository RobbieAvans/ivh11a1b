package edu.avans.hartigehap.web.controller.rs.body;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

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
    private List<PartOfDay> partOfDays = new ArrayList<>();

    public HallReservationRequest() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this); 
    }
    
    public Hall getHall() throws Exception {
        Hall foundHall = hallService.findById(hall);
        
        if (foundHall == null) throw new Exception("Not an existing hall");
        
        return foundHall;
    }

    public List<HallOption> getHallOptions() {
        return hallOptionService.findByIds(hallOptions);
    }

    public Customer getCustomer() throws Exception {
        Customer foundCustomer = customerService.findById(customer);
        
        if (foundCustomer == null) throw new Exception("Not an existing customer");
        
        return foundCustomer;
    }
}