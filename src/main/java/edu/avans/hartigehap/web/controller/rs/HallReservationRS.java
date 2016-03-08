package edu.avans.hartigehap.web.controller.rs;

import java.util.ArrayList;
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

import edu.avans.hartigehap.domain.HallReservation;
import edu.avans.hartigehap.domain.HallReservationAPIWrapper;
import edu.avans.hartigehap.service.HallReservationService;
import edu.avans.hartigehap.service.HallService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HallReservationRS extends BaseRS {

    @Autowired
    private HallReservationService hallReservationService;
    @Autowired
    private HallService hallService;

    @RequestMapping(value = RSConstants.URL_PREFIX
            + "/hallReservation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ModelAndView createHallReservation(@RequestBody HallReservationAPIWrapper hallReservationWrapper,
            HttpServletResponse httpResponse, WebRequest httpRequest) {
        /**
        HallReservation hallReservation = hallReservationWrapper.getHallReservation();
        Hall hall = hallReservation.getHall();
        
        // Decorate with the halloptions
        for (HallOption hallOption : hallReservationWrapper.getHallOptions()) {
            hallReservation = new HallReservationOption(hallReservation, hallOption);
        }
        
        hall.addReservation(hallReservation);
        hallService.save(hall);
        
        httpResponse.setStatus(HttpStatus.CREATED.value());
        httpResponse.setHeader("Location",
                httpRequest.getContextPath() + "/hallReservation/" + hallReservation.getId());

        return createSuccessResponse(hallReservation);
        */
        return createSuccessResponse(null);
    }

    @RequestMapping(value = RSConstants.URL_PREFIX
            + "/hallReservation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ModelAndView allHallReservations() {
        List<HallReservationAPIWrapper> wrapper = new ArrayList<>();
        
        // Fill wrapper
        for (HallReservation hallReservation : hallReservationService.findAll()) {
            wrapper.add(new HallReservationAPIWrapper(hallReservation));
        }
        
        return createSuccessResponse(wrapper);
    }

    @RequestMapping(value = RSConstants.URL_PREFIX
            + "/hallReservation/{hallReservationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ModelAndView getHallReservation(@PathVariable long hallReservationId) {
        HallReservation hallReservation = hallReservationService.findById(hallReservationId);

        if (hallReservation != null) {
            return createSuccessResponse(new HallReservationAPIWrapper(hallReservation));
        }

        return createErrorResponse("HallReservation with id " + hallReservationId + " was not found");
    }

    @RequestMapping(value = RSConstants.URL_PREFIX
            + "/hallReservation/{hallReservationId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ModelAndView updateHallReservation(@RequestBody HallReservation hallReservation, @PathVariable long hallReservationId,
            HttpServletResponse httpResponse) {

        if (hallReservationService.findById(hallReservationId) != null) {
            hallReservation.setId(hallReservationId);
            hallReservationService.save(hallReservation);

            httpResponse.setStatus(HttpStatus.OK.value());

            return createSuccessResponse(hallReservation);
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
