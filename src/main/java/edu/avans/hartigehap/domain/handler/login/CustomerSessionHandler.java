package edu.avans.hartigehap.domain.handler.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import edu.avans.hartigehap.domain.Authenticatable;
import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.handler.Handler;
import edu.avans.hartigehap.service.CustomerService;

@Configurable
public class CustomerSessionHandler extends Handler<String, Authenticatable> {

    @Autowired
    private CustomerService customerService;

    @Override
    public Authenticatable handleRequest(String request) {
        Customer customer = customerService.findBySessionID(request);

        if (customer != null) {
            return customer;
        }
        return (successor != null) ? successor.handleRequest(request) : null;
    }

}
