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

import edu.avans.hartigehap.domain.Authenticatable;
import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.service.HallService;

@Controller
@RequestMapping(value = RSConstants.URL_PREFIX + "/hall", produces = MediaType.APPLICATION_JSON_VALUE)
public class HallRS extends BaseRS {

    @Autowired
    private HallService hallService;

    /**
     * Tested with curl: curl -H "Content-Type: application/json" -X POST -d
     * '{"version":0,"description":"api_test","numberOfSeats":20}'
     * http://localhost:8080/hh/rest/v1/hall
     * 
     * Response:
     * 
     * { "success": { true }, "data": {
     * "@id":1,"id":1,"version":0,"numberOfSeats":20,"description":"api_test" }
     * }
     * 
     * 
     * @param hall
     * @param httpResponse
     * @param httpRequest
     * @return
     */
    @RequestMapping(value = "/{sessionID}", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView createHall(@RequestBody Hall hall, @PathVariable String sessionID,
            HttpServletResponse httpResponse, WebRequest httpRequest) {

        return shouldBeManager(sessionID, (Authenticatable auth) -> {
            Hall savedHall = hallService.save(hall);
            httpResponse.setStatus(HttpStatus.CREATED.value());
            httpResponse.setHeader("Location", httpRequest.getContextPath() + "/hall/" + savedHall.getId());

            return createSuccessResponse(savedHall);
        });
    }

    /**
     * Tested with curl:
     * 
     * curl -H "Content-Type: application/json" -X GET
     * http://localhost:8080/hh/rest/v1/hall
     * 
     * Response:
     * 
     * { "success": { true }, "data": {
     * "@id":1,"id":1,"version":0,"numberOfSeats":20,"description":"api_test" }
     * }
     * 
     * @return
     */
    @RequestMapping(value = "/{sessionID}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView allHalls(@PathVariable String sessionID) {
        return shouldBeAuthenticated(sessionID, (Authenticatable auth) -> 
            createSuccessResponse(hallService.findAll())
        );
    }

    /**
     * Tested with curl:
     * 
     * curl -H "Content-Type: application/json" -X GET
     * http://localhost:8080/hh/rest/v1/hall/1
     * 
     * Response:
     * 
     * { "success": { true }, "data": {
     * "@id":1,"id":1,"version":0,"numberOfSeats":20,"description":"api_test" }
     * }
     * 
     * Test with not existing hall
     * 
     * curl -H "Content-Type: application/json" -X GET
     * http://localhost:8080/hh/rest/v1/hall/2
     * 
     * Response:
     * 
     * { "success": { false }, "data": { "Hall with id 2 was not found" } }
     * 
     * @param hallId
     * @return
     */
    @RequestMapping(value = "/{hallId}/{sessionID}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getHall(@PathVariable long hallId, @PathVariable String sessionID) {
        return shouldBeAuthenticated(sessionID, (Authenticatable auth) -> {
            Hall hall = hallService.findById(hallId);

            if (hall != null) {
                return createSuccessResponse(hall);
            }

            return createErrorResponse("hall_not_exists");
        });
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
     * { "success": { true }, "data": {
     * "@id":1,"id":1,"version":0,"numberOfSeats":30,"description":
     * "api_edit_test" } }
     * 
     * Test with not existing hall
     * 
     * curl -H "Content-Type: application/json" -X PUT -d
     * '{"version":0,"description":"api_edit_test","numberOfSeats":30}'
     * http://localhost:8080/hh/rest/v1/hall/2
     * 
     * Response:
     * 
     * { "success": { false }, "data": { "Hall doesn't exists" } }
     * 
     * @param hall
     * @param hallId
     * @param httpResponse
     * @return
     */
    @RequestMapping(value = "/{hallId}/{sessionID}", method = RequestMethod.PUT)
    @ResponseBody
    public ModelAndView updateHall(@RequestBody Hall hall, @PathVariable long hallId, @PathVariable String sessionID,
            HttpServletResponse httpResponse) {

        return shouldBeManager(sessionID, (Authenticatable auth) -> {

            if (hallService.findById(hallId) != null) {
                hall.setId(hallId);
                hallService.save(hall);

                httpResponse.setStatus(HttpStatus.OK.value());

                return createSuccessResponse(hall);
            }

            return createErrorResponse("hall_not_exists");
        });
    }

    /**
     * Tested with curl:
     * 
     * curl -H "Content-Type: application/json" -X DELETE
     * http://localhost:8080/hh/rest/v1/hall/1
     * 
     * Response:
     * 
     * {"success":true,"data":1}
     * 
     * Test with not existing hall
     * 
     * curl -H "Content-Type: application/json" -X DELETE
     * http://localhost:8080/hh/rest/v1/hall/2
     * 
     * Response:
     * 
     * {"success":false,"data":
     * "Hall cannot be deleted. Maybe it has active reservations"}
     * 
     * @param hallId
     * @param httpResponse
     * @return
     */
    @RequestMapping(value = "/{hallId}/{sessionID}", method = RequestMethod.DELETE)
    @ResponseBody
    public ModelAndView removeHall(@PathVariable long hallId, @PathVariable String sessionID,
            HttpServletResponse httpResponse) {

        return shouldBeManager(sessionID, (Authenticatable auth) -> {

            boolean result = hallService.deleteById(hallId);

            if (result) {
                httpResponse.setStatus(HttpStatus.OK.value());

                return createSuccessResponse(hallId);
            }

            return createErrorResponse("hall_has_active_reservation");
        });
    }
}
