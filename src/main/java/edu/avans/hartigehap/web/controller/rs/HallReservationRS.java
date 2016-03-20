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
import edu.avans.hartigehap.domain.hallreservation.ConcreteHallReservation;
import edu.avans.hartigehap.domain.hallreservation.HallReservation;
import edu.avans.hartigehap.domain.hallreservation.HallReservationOption;
import edu.avans.hartigehap.service.HallReservationService;
import edu.avans.hartigehap.service.HallService;
import edu.avans.hartigehap.web.controller.rs.body.HallReservationRequest;
import edu.avans.hartigehap.web.controller.rs.body.HallReservationResponse;

@Controller
public class HallReservationRS extends BaseRS {

    @Autowired
    private HallReservationService hallReservationService;
    @Autowired
    private HallService hallService;

    @RequestMapping(value = RSConstants.URL_PREFIX
            + "/hallReservation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ModelAndView createHallReservation(@RequestBody HallReservationRequest hallReservationRequest,
            HttpServletResponse httpResponse, WebRequest httpRequest) {

        try {
            // Get the hall where we will save it on
            Hall hall = hallReservationRequest.getHall();
            
            List<HallOption> hallOptions = hallReservationRequest.getHallOptions();
            Iterator<HallOption> hallOptionsIterator = hallOptions.iterator();

            // Create the HallReservation
            HallReservation reservation = new ConcreteHallReservation();
            
            // Decorate it with hallOptions
            while (hallOptionsIterator.hasNext()) {
                reservation = new HallReservationOption(reservation, hallOptionsIterator.next());
            }

            reservation.setDescription(hallReservationRequest.getDescription());
            hall.addReservation(reservation);
            hallService.save(hall);

            httpResponse.setStatus(HttpStatus.CREATED.value());
            httpResponse.setHeader("Location",
                    httpRequest.getContextPath() + "/hallReservation/" + reservation.getId());

            return createSuccessResponse(new HallReservationResponse(reservation));
        } catch (Exception e) {
            return createErrorResponse("Unable to create the HallReservation");
        }
    }

    @RequestMapping(value = RSConstants.URL_PREFIX
            + "/hallReservation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ModelAndView allHallReservations() {
        List<HallReservationResponse> response = new ArrayList<>();

        // Fill wrapper
        for (HallReservation hallReservation : hallReservationService.findAll()) {
            response.add(new HallReservationResponse(hallReservation));
        }

        return createSuccessResponse(response);
    }

    @RequestMapping(value = RSConstants.URL_PREFIX
            + "/hallReservation/{hallReservationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ModelAndView getHallReservation(@PathVariable long hallReservationId) {
        HallReservation hallReservation = hallReservationService.findById(hallReservationId);

        if (hallReservation != null) {
            return createSuccessResponse(new HallReservationResponse(hallReservation));
        }

        return createErrorResponse("HallReservation with id " + hallReservationId + " was not found");
    }

    @RequestMapping(value = RSConstants.URL_PREFIX
            + "/hallReservation/{hallReservationId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ModelAndView updateHallReservation(@RequestBody HallReservationRequest hallReservationRequest,
            @PathVariable long hallReservationId) {

        HallReservation hallReservation = hallReservationService.findById(hallReservationId);

        if (hallReservation != null) {
            
            try {
                hallReservation = hallReservationService.update(hallReservation, hallReservationRequest);

                return createSuccessResponse(new HallReservationResponse(hallReservation));
            } catch (Exception e) {
                return createErrorResponse("Unable to update the HallReservation");
            }
        }

        return createErrorResponse("HallReservation doesn't exists");
    }

    @RequestMapping(value = RSConstants.URL_PREFIX
            + "/hallReservation/{hallReservationId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ModelAndView removeHallReservation(@PathVariable long hallReservationId, HttpServletResponse httpResponse) {
        HallReservation hallReservation = hallReservationService.findById(hallReservationId);

        if (hallReservation != null) {
            hallReservationService.delete(hallReservation);

            httpResponse.setStatus(HttpStatus.OK.value());

            return createSuccessResponse(hallReservationId);
        }

        return createErrorResponse("HallReservation doesn't exists");
    }
}
