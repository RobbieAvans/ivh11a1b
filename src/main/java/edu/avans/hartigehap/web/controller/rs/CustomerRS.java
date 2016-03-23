package edu.avans.hartigehap.web.controller.rs;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.service.CustomerService;
import edu.avans.hartigehap.web.controller.rs.body.CreateCustomerRequest;
import edu.avans.hartigehap.web.controller.rs.body.LoginRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = RSConstants.URL_PREFIX, produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerRS extends BaseRS {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView createCustomer(@RequestBody CreateCustomerRequest customerRequest,
            HttpServletResponse httpResponse, WebRequest httpRequest) {
        try {
            Customer findCustomer = customerService.findByEmail(customerRequest.getEmail());
            if (findCustomer == null) {
                Customer customer = new Customer();
                customer.setEmail(customerRequest.getEmail());
                customer.setPassword(customerRequest.getPassword());
                customer.setFirstName(customerRequest.getFirstName());
                customer.setLastName(customerRequest.getLastName());

                // If registred, set initial login sessionID
                customer.setSessionID(java.util.UUID.randomUUID().toString());

                // Save
                Customer savedCustomer = customerService.save(customer);
                httpResponse.setStatus(HttpStatus.CREATED.value());

                return createSuccessResponse(savedCustomer);
            } else {
                return createErrorResponse("Customer already exists");
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            return createErrorResponse("Error when creating a new customer");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpResponse,
            WebRequest httpRequest) {

        Customer customer = customerService.findByEmail(loginRequest.getEmail());
        if (customer != null) {
            // Check for password
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(loginRequest.getPassword(), customer.getPassword())) {
                customer.setSessionID(java.util.UUID.randomUUID().toString());

                Customer savedCustomer = customerService.save(customer);

                return createSuccessResponse(savedCustomer);   
            }
        }
            
        return createErrorResponse("Wrong credentials");
    }
    
    @RequestMapping(value = "/login/{sessionID}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView checkLogin(@PathVariable String sessionID,
            HttpServletResponse httpResponse, WebRequest httpRequest) {
        
        Customer findCustomer = customerService.findBySessionID(sessionID);
        
        return createSuccessResponse(findCustomer != null);
    }
    
}
