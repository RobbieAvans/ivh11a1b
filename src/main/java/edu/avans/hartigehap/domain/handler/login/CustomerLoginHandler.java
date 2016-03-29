package edu.avans.hartigehap.domain.handler.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.service.CustomerService;

@Configurable
public class CustomerLoginHandler extends LoginHandlerTemplate<Customer> {

    @Autowired
    private CustomerService customerService;

    @Override
    public Customer findByEmail(String email) {
        return customerService.findByEmail(email);
    }

    @Override
    public Customer save(Customer customer) {
        return customerService.save(customer);
    }
    
}
