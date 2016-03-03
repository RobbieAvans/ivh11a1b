package edu.avans.hartigehap.web.controller.rs;

import java.util.List;

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
import org.springframework.web.servlet.View;

import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.service.HallService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HallRS {

    @Autowired
    private HallService hallService;
    
    @Autowired
    private View jsonView;
    
    private static final String DATA_FIELD = "data";
    private static final String ERROR_FIELD = "error";
    
    /**
     * Tested with curl:
     * curl -H "Content-Type: application/json" -X POST -d 
     * '{"version":0,"description":"api_test","numberOfSeats":20}' http://localhost:8080/hh/rest/v1/hall
     * 
     * Response:
     * 
     * {"data":{"@id":1,"id":1,"version":0,"numberOfSeats":20,"description":"api_test","reservations":[]}}
     * 
     * 
     * @param hall
     * @param httpResponse
     * @param httpRequest
     * @return
     */
    @RequestMapping(value = RSConstants.URL_PREFIX
            + "/hall", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ModelAndView createHall(@RequestBody Hall hall, HttpServletResponse httpResponse,
            WebRequest httpRequest) {
        try {
            Hall savedHall = hallService.save(hall);
            httpResponse.setStatus(HttpStatus.CREATED.value());
            httpResponse.setHeader("Location",
                    httpRequest.getContextPath() + "/hall/" + savedHall.getId());
            return new ModelAndView(jsonView, DATA_FIELD, savedHall);
        } catch (Exception e) {
            log.error("Error creating new restaurant", e);
            String message = "Error creating new restaurant. [%1$s]";
            return createErrorResponse(String.format(message, e.toString()));
        }
    }
    
    /**
     * Tested with curl:
     * 
     * curl -H "Content-Type: application/json" -X GET http://localhost:8080/hh/rest/v1/hall
     * 
     * Response:
     * 
     * [{"@id":1,"id":1,"version":0,"numberOfSeats":20,"description":"api_test","reservations":[]}]
     * 
     * @return
     */
    @RequestMapping(value = RSConstants.URL_PREFIX
            + "/hall", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Hall> allHalls() {
        return hallService.findAll();
    }
    
    /**
     * Tested with curl:
     * 
     * curl -H "Content-Type: application/json" -X GET http://localhost:8080/hh/rest/v1/hall/1
     * 
     * Response:
     * 
     * {"@id":1,"id":1,"version":0,"numberOfSeats":20,"description":"api_test","reservations":[]}
     * 
     * Test with not existing hall
     * 
     * curl -H "Content-Type: application/json" -X GET http://localhost:8080/hh/rest/v1/hall/2
     * 
     * Response:
     * 
     * Empty/nothing ???!!!
     * 
     * @param hallId
     * @return
     */
    @RequestMapping(value = RSConstants.URL_PREFIX
            + "/hall/{hallId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Hall getHall(@PathVariable long hallId) {
        return hallService.findById(hallId);
    }
    
    /**
     * Tested with curl:
     * 
     * curl -H "Content-Type: application/json" -X PUT -d 
     * '{"version":0,"description":"api_edit_test","numberOfSeats":30}' 
     * http://localhost:8080/hh/rest/v1/hall/1
     * 
     * Response:
     * 
     * {"data":{"@id":1,"id":1,"version":0,"numberOfSeats":30,"description":"api_edit_test","reservations":[]}}
     * 
     * Test with not existing hall
     * 
     * curl -H "Content-Type: application/json" -X PUT -d 
     * '{"version":0,"description":"api_edit_test","numberOfSeats":30}' 
     * http://localhost:8080/hh/rest/v1/hall/2
     * 
     * Response:
     * 
     * {"error":"Hall doesn't exists."}
     * 
     * @param hall
     * @param hallId
     * @param httpResponse
     * @return
     */
    @RequestMapping(value = RSConstants.URL_PREFIX
            + "/hall/{hallId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ModelAndView updateHall(@RequestBody Hall hall, @PathVariable long hallId, HttpServletResponse httpResponse) {
    	
    	if (hallService.findById(hallId) != null) {
    		hall.setId(hallId);
        	hallService.save(hall);
        	
        	httpResponse.setStatus(HttpStatus.OK.value());  
        	
        	return new ModelAndView(jsonView, DATA_FIELD, hall);
    	}
    	
    	return createErrorResponse("Hall doesn't exists.");
    }
    
    /**
     * Tested with curl:
     * 
     * curl -H "Content-Type: application/json" -X DELETE http://localhost:8080/hh/rest/v1/hall/1
     * 
     * Response:
     * 
     * {"data":1}
     * 
     * Test with not existing hall
     * 
     * curl -H "Content-Type: application/json" -X DELETE http://localhost:8080/hh/rest/v1/hall/2
     * 
     * Response:
     * 
     * {"data":2} Is this a problem??
     * 
     * @param hallId
     * @param httpResponse
     * @return
     */
    @RequestMapping(value = RSConstants.URL_PREFIX
            + "/hall/{hallId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ModelAndView removeHall(@PathVariable long hallId, HttpServletResponse httpResponse) {
        hallService.deleteById(hallId);
        
        httpResponse.setStatus(HttpStatus.OK.value());  
        
        return new ModelAndView(jsonView, DATA_FIELD, hallId);
    }
    
    private ModelAndView createErrorResponse(String sMessage) {
        return new ModelAndView(jsonView, ERROR_FIELD, sMessage);
    }
}
