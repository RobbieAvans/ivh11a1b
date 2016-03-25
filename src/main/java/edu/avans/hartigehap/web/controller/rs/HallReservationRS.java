package edu.avans.hartigehap.web.controller.rs;

import java.util.ArrayList;
import java.util.Iterator;
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

import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.domain.HallOption;
import edu.avans.hartigehap.domain.PartOfDay;
import edu.avans.hartigehap.domain.hallreservation.ConcreteHallReservation;
import edu.avans.hartigehap.domain.hallreservation.HallReservation;
import edu.avans.hartigehap.domain.hallreservation.HallReservationOption;
import edu.avans.hartigehap.service.HallReservationService;
import edu.avans.hartigehap.service.HallService;
import edu.avans.hartigehap.web.controller.rs.body.HallReservationRequest;
import edu.avans.hartigehap.web.controller.rs.body.HallReservationResponse;
import edu.avans.hartigehap.web.controller.rs.body.InvalidJsonRequestException;

@Controller
@RequestMapping (value = RSConstants.URL_PREFIX + "/hallReservation", produces = MediaType.APPLICATION_JSON_VALUE)
public class HallReservationRS extends BaseRS {

    @Autowired
    private HallReservationService hallReservationService;
    @Autowired
    private HallService hallService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView createHallReservation(@RequestBody HallReservationRequest hallReservationRequest,
            HttpServletResponse httpResponse, WebRequest httpRequest) {

        try {
            // Get the hall where we will save it on
            Hall hall = hallReservationRequest.getHallObject();
            hall.touchHallReservations();
            
            List<HallOption> hallOptions = hallReservationRequest.getHallOptionObjects();
            Iterator<HallOption> hallOptionsIterator = hallOptions.iterator();

            // Create the HallReservation
            HallReservation reservation = new ConcreteHallReservation();
            
            // Decorate it with hallOptions
            while (hallOptionsIterator.hasNext()) {
                reservation = new HallReservationOption(reservation, hallOptionsIterator.next());
            }

            reservation.setDescription(hallReservationRequest.getDescription());
            
            // Add PartOfDays
            for (PartOfDay partOfDay : hallReservationRequest.getPartOfDaysObjects()) {
                reservation.addPartOfDay(partOfDay);
            }
            
            hall.addReservation(reservation);
                     
            hallService.save(hall);

            httpResponse.setStatus(HttpStatus.CREATED.value());
            httpResponse.setHeader("Location",
                    httpRequest.getContextPath() + "/hallReservation/" + reservation.getId());

            return createSuccessResponse(new HallReservationResponse(reservation));
        } catch (InvalidJsonRequestException e) {
            return createErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView allHallReservations() {
        List<HallReservationResponse> response = new ArrayList<>();
        
        // Fill wrapper
        for (HallReservation hallReservation : hallReservationService.findAll()) {
            response.add(new HallReservationResponse(hallReservation));
        }

        return createSuccessResponse(response);
    }

    @RequestMapping(value = "/{hallReservationId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getHallReservation(@PathVariable long hallReservationId) {
   
        HallReservation hallReservation = hallReservationService.findById(hallReservationId);
        
        if (hallReservation != null) {            
            return createSuccessResponse(new HallReservationResponse(hallReservation));
        }

        return createErrorResponse("hallreservation_not_exists");
    }

    @RequestMapping(value = "/{hallReservationId}", method = RequestMethod.PUT)
    @ResponseBody
    public ModelAndView updateHallReservation(@RequestBody HallReservationRequest hallReservationRequest,
            @PathVariable long hallReservationId, HttpServletResponse httpResponse) {

        HallReservation hallReservation = hallReservationService.findById(hallReservationId);

        if (hallReservation != null) {
            
           try {
                hallReservation = hallReservationService.update(hallReservation, hallReservationRequest);
                
                httpResponse.setStatus(HttpStatus.OK.value());
                
                return createSuccessResponse(new HallReservationResponse(hallReservation));
            } catch (InvalidJsonRequestException e) {
                return createErrorResponse(e.getMessage());
            }
        }

        return createErrorResponse("hallreservation_not_exists");
    }

    @RequestMapping(value = "/{hallReservationId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ModelAndView removeHallReservation(@PathVariable long hallReservationId, HttpServletResponse httpResponse) {
        HallReservation hallReservation = hallReservationService.findById(hallReservationId);

        if (hallReservation != null) {
            hallReservationService.delete(hallReservation);

            httpResponse.setStatus(HttpStatus.OK.value());

            return createSuccessResponse(hallReservationId);
        }

        return createErrorResponse("hallreservation_not_exists");
    }
}
