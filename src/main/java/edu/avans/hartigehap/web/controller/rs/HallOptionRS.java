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

import edu.avans.hartigehap.domain.HallOption;
import edu.avans.hartigehap.service.HallOptionService;

// http://briansjavablog.blogspot.nl/2012/08/rest-services-with-spring.html
@Controller
@RequestMapping (value = RSConstants.URL_PREFIX + "/halloption", produces = MediaType.APPLICATION_JSON_VALUE)
public class HallOptionRS extends BaseRS {

    @Autowired
    private HallOptionService hallOptionService;

    /**
     * Tested with curl: curl -H "Content-Type: application/json" -X POST -d
     * '{"version":0,"description":"api_test","price":20.20}'
     * http://localhost:8080/hh/rest/v1/halloption
     * 
     * Response:
     * 
     * { "data": { "@id": 1, "id": 4, "version": 0, "description": "api_test",
     * "price": 20.2 }, "success": true }
     * 
     * 
     * @param hallOption
     * @param httpResponse
     * @param httpRequest
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView createHallOption(@RequestBody HallOption hallOption, HttpServletResponse httpResponse,
            WebRequest httpRequest) {

        HallOption savedHallOption = hallOptionService.save(hallOption);
        httpResponse.setStatus(HttpStatus.CREATED.value());
        httpResponse.setHeader("Location", httpRequest.getContextPath() + "/hallOption/" + savedHallOption.getId());

        return createSuccessResponse(savedHallOption);
    }

    /**
     * Tested with curl:
     * 
     * curl -H "Content-Type: application/json" -X GET
     * http://localhost:8080/hh/rest/v1/halloption
     * 
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView allHallOptions() {
        return createSuccessResponse(hallOptionService.findAll());
    }

    /**
     * Tested with curl:
     * 
     * curl -H "Content-Type: application/json" -X GET
     * http://localhost:8080/hh/rest/v1/halloption/1
     * 
     * @param hallOptionId
     * @return
     */
    @RequestMapping(value = "/{hallOptionId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getHallOption(@PathVariable long hallOptionId) {
        HallOption hallOption = hallOptionService.findById(hallOptionId);

        if (hallOption != null) {
            return createSuccessResponse(hallOption);
        }

        return createErrorResponse("halloption_not_exists");
    }

    /**
     * Tested with curl:
     * 
     * curl -H "Content-Type: application/json" -X PUT -d
     * '{"version":0,"description":"api_edit_test","price":20.40}'
     * http://localhost:8080/hh/rest/v1/halloption/1
     * 
     * @param hallOption
     * @param hallOptionId
     * @param httpResponse
     * @return
     */
    @RequestMapping(value = "/{hallOptionId}", method = RequestMethod.PUT)
    @ResponseBody
    public ModelAndView updateHallOption(@RequestBody HallOption hallOption, @PathVariable long hallOptionId,
            HttpServletResponse httpResponse) {

        if (hallOptionService.findById(hallOptionId) != null) {
            hallOption.setId(hallOptionId);
            hallOptionService.save(hallOption);

            httpResponse.setStatus(HttpStatus.OK.value());

            return createSuccessResponse(hallOption);
        }

        return createErrorResponse("halloption_not_exists");
    }

    /**
     * Tested with curl:
     * 
     * curl -H "Content-Type: application/json" -X DELETE
     * http://localhost:8080/hh/rest/v1/hall/1
     * 
     * @param halloptionId
     * @param httpResponse
     * @return
     */
    @RequestMapping(value = "/{hallOptionId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ModelAndView removeHallOption(@PathVariable long hallOptionId, HttpServletResponse httpResponse) {
        hallOptionService.deleteById(hallOptionId);

        httpResponse.setStatus(HttpStatus.OK.value());

        return createSuccessResponse(hallOptionId);
    }
}
