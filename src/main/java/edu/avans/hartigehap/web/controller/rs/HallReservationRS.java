package edu.avans.hartigehap.web.controller.rs;

import java.lang.reflect.Method;
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

import edu.avans.hartigehap.domain.Authenticatable;
import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.domain.HallOption;
import edu.avans.hartigehap.domain.Manager;
import edu.avans.hartigehap.domain.PartOfDay;
import edu.avans.hartigehap.domain.hallreservation.ConcreteHallReservation;
import edu.avans.hartigehap.domain.hallreservation.HallReservation;
import edu.avans.hartigehap.domain.hallreservation.HallReservationOption;
import edu.avans.hartigehap.domain.strategy.HallReservationPriceStrategyFactory;
import edu.avans.hartigehap.service.CustomerService;
import edu.avans.hartigehap.service.HallReservationService;
import edu.avans.hartigehap.service.HallService;
import edu.avans.hartigehap.web.controller.rs.body.HallReservationRequest;
import edu.avans.hartigehap.web.controller.rs.body.HallReservationResponse;
import edu.avans.hartigehap.web.controller.rs.security.NotAuthorizedException;

@Controller
@RequestMapping(value = RSConstants.URL_PREFIX + "/hallReservation", produces = MediaType.APPLICATION_JSON_VALUE)
public class HallReservationRS extends BaseRS {

	@Autowired
	private HallReservationService hallReservationService;

	@Autowired
	private HallService hallService;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private HallReservationPriceStrategyFactory hallReservationPriceStrategyFactory;

	@RequestMapping(value = "/{sessionID}", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView createHallReservation(@RequestBody HallReservationRequest hallReservationRequest,
			@PathVariable String sessionID, HttpServletResponse httpResponse, WebRequest httpRequest) {

		return shouldBeAuthenticated(sessionID, (Authenticatable auth) -> {
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

			if (auth.getRole() == Manager.ROLE) {
				reservation.setCustomer(hallReservationRequest.getCustomerObject());
			} else {
				// If the role is customer, you can only add a reservation for
				// yourself
				reservation.setCustomer(customerService.findBySessionID(auth.getSessionID()));
			}

			// Add PartOfDays
			for (PartOfDay partOfDay : hallReservationRequest.getPartOfDaysObjects()) {
				reservation.addPartOfDay(partOfDay);
			}

			hall.addReservation(reservation);

			hallService.save(hall);

			httpResponse.setStatus(HttpStatus.CREATED.value());
			httpResponse.setHeader("Location",
					httpRequest.getContextPath() + "/hallReservation/" + reservation.getId());

			return createSuccessResponse(new HallReservationResponse(reservation, hallReservationPriceStrategyFactory));
		});
	}

	@RequestMapping(value = "/{sessionID}", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView allHallReservations(@PathVariable String sessionID) {

		return shouldBeAuthenticated(sessionID, (Authenticatable auth) -> {
			List<HallReservationResponse> response = new ArrayList<>();

			// Fill wrapper
			for (HallReservation hallReservation : hallReservationService.findAll()) {
				// Only add it if it's a manager or if the hallReservation is
				// for the customer
				if (auth.getRole() != Customer.ROLE
						|| auth.getSessionID().equals(hallReservation.getCustomer().getSessionID())) {
					response.add(new HallReservationResponse(hallReservation, hallReservationPriceStrategyFactory));
				}
			}

			return createSuccessResponse(response);
		});
	}

	@RequestMapping(value = "/{hallReservationId}/{sessionID}", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getHallReservation(@PathVariable long hallReservationId, @PathVariable String sessionID) {

		return shouldBeAuthenticated(sessionID, (Authenticatable auth) -> {
			HallReservation hallReservation = hallReservationService.findById(hallReservationId);

			if (hallReservation != null) {
				checkHallReservationAuth(hallReservation, auth);

				return createSuccessResponse(new HallReservationResponse(hallReservation, hallReservationPriceStrategyFactory));
			}

			return createErrorResponse("hallreservation_not_exists");
		});
	}

	@RequestMapping(value = "/{hallReservationId}/{sessionID}", method = RequestMethod.PUT)
	@ResponseBody
	public ModelAndView updateHallReservation(@RequestBody HallReservationRequest hallReservationRequest,
			@PathVariable long hallReservationId, @PathVariable String sessionID, HttpServletResponse httpResponse) {

		return shouldBeAuthenticated(sessionID, (Authenticatable auth) -> {
			HallReservation hallReservation = hallReservationService.findById(hallReservationId);

			if (hallReservation != null) {
				checkHallReservationAuth(hallReservation, auth);

				hallReservation = hallReservationService.update(hallReservation, hallReservationRequest);

				httpResponse.setStatus(HttpStatus.OK.value());

				return createSuccessResponse(new HallReservationResponse(hallReservation, hallReservationPriceStrategyFactory));

			}

			return createErrorResponse("hallreservation_not_exists");
		});
	}

	@RequestMapping(value = "/{hallReservationId}/{action}/{sessionID}", method = RequestMethod.PUT)
	@ResponseBody
	public ModelAndView updateState(@PathVariable long hallReservationId, @PathVariable String action,
			@PathVariable String sessionID, HttpServletResponse httpResponse) {

		return shouldBeAuthenticated(sessionID, (Authenticatable auth) -> {
			HallReservation hallReservation = hallReservationService.findById(hallReservationId);

			if (hallReservation != null) {
				checkHallReservationAuth(hallReservation, auth);

				try {
					// Call the action
					Method method = HallReservation.class.getMethod(action);
					method.invoke(hallReservation);					
				} catch (Exception e) {
					return createErrorResponse("invalid_state_action");
				}

				httpResponse.setStatus(HttpStatus.OK.value());
				return createSuccessResponse(new HallReservationResponse(hallReservation, hallReservationPriceStrategyFactory));
			}

			return createErrorResponse("hallreservation_not_exists");
		});
	}

	@RequestMapping(value = "/{hallReservationId}/{sessionID}", method = RequestMethod.DELETE)
	@ResponseBody
	public ModelAndView removeHallReservation(@PathVariable long hallReservationId, @PathVariable String sessionID,
			HttpServletResponse httpResponse) {

		return shouldBeManager(sessionID, (Authenticatable auth) -> {
			HallReservation hallReservation = hallReservationService.findById(hallReservationId);

			if (hallReservation != null) {
				hallReservationService.delete(hallReservation);

				httpResponse.setStatus(HttpStatus.OK.value());

				return createSuccessResponse(hallReservationId);
			}

			return createErrorResponse("hallreservation_not_exists");
		});
	}

	private void checkHallReservationAuth(HallReservation hallReservation, Authenticatable auth)
			throws NotAuthorizedException {
		// Check if the customer can change this reservation
		if (auth.getRole() == Customer.ROLE
				&& !hallReservation.getCustomer().getSessionID().equals(auth.getSessionID())) {
			throw new NotAuthorizedException();
		}
	}
}
