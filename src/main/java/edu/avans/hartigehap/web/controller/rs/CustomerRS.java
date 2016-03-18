package edu.avans.hartigehap.web.controller.rs;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.domain.HallReservation;
import edu.avans.hartigehap.service.CustomerService;
import edu.avans.hartigehap.service.HallService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CustomerRS extends BaseRS {

    @Autowired
    private CustomerService customerService;

    /**
     * Tested with curl: curl -H "Content-Type: application/json" -X POST -d
     * '{"version":0,"description":"api_test","numberOfSeats":20}'
     * http://localhost:8080/hh/rest/v1/hall
     * 
     * Response:
     * 
     * { "success": { true }, "data": {
     * "@id":1,"id":1,"version":0,"numberOfSeats":20,"description":"api_test" } }
     * 
     * 
     * @param hall
     * @param httpResponse
     * @param httpRequest
     * @return
     */
    @RequestMapping(value = RSConstants.URL_PREFIX
            + "/customer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ModelAndView createCustomer(@RequestBody Customer customer, HttpServletResponse httpResponse, WebRequest httpRequest) {
        try {
        	Customer findCustomer = customerService.findByEmail(customer.getEmail());
            if (findCustomer == null) {
	        	// If registred, set initial login sessionID
	        	//customer.setSessionID(java.util.UUID.randomUUID().toString());
	        	
	            Customer savedCustomer = customerService.save(customer);
	            httpResponse.setStatus(HttpStatus.CREATED.value());
	            httpResponse.setHeader("Location", httpRequest.getContextPath() + "/customer/" + savedCustomer.getId());
	
	            return createSuccessResponse(savedCustomer);
            }else{
            	return createErrorResponse("Customer already exists");
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            return createErrorResponse("Error when creating a new customer");
        }
    }

}
