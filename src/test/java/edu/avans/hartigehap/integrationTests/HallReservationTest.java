package edu.avans.hartigehap.integrationTests;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.domain.HallOption;
import edu.avans.hartigehap.domain.PartOfDay;
import edu.avans.hartigehap.domain.PartOfDayFactory;
import edu.avans.hartigehap.domain.hallreservation.ConcreteHallReservation;
import edu.avans.hartigehap.domain.hallreservation.HallReservation;
import edu.avans.hartigehap.domain.hallreservation.HallReservationOption;
import edu.avans.hartigehap.domain.hallreservation.state.HallReservationState;
import edu.avans.hartigehap.service.CustomerService;
import edu.avans.hartigehap.service.HallOptionService;
import edu.avans.hartigehap.service.HallReservationService;
import edu.avans.hartigehap.service.HallService;
import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;
import edu.avans.hartigehap.web.controller.rs.body.HallReservationRequest;
import edu.avans.hartigehap.web.controller.rs.body.PartOfDayRequest;

public class HallReservationTest extends AbstractTransactionRollbackTest {

    @Autowired
    private HallOptionService hallOptionService;

    @Autowired
    private HallService hallService;

    @Autowired
    private HallReservationService hallReservationService;

    @Autowired
    private CustomerService customerService;

    /**
     * Create the objects we need
     */
    private Hall hall = new Hall("Grote zaal", 180, 100);
    private Hall hall2 = new Hall("Kleinere zaal", 80, 50);
    private HallOption hallOption1 = new HallOption("Wifi", 5.0);
    private HallOption hallOption2 = new HallOption("DJ", 50.00);
    private byte[] photo = new byte[] { 127, -128, 0 };
    private Customer customer = new Customer("FirstName", "LastName", "email", new DateTime(), 0, "description", photo);

    @Test
    public void CRUDHallReservation() throws Exception {

        String description = "This is a nice hall";

        // Save the hallOptions
        hallOptionService.save(hallOption1);
        hallOptionService.save(hallOption2);

        // Get partOfDayObjects
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1); // Add one day so it is never in the
                                        // past

        PartOfDayFactory factory = new PartOfDayFactory();
        PartOfDay part1 = factory.makePartOfDay("Evening", calendar.getTime());
        PartOfDay part2 = factory.makePartOfDay("Afternoon", calendar.getTime());

        // Decorate reservation
        HallReservation reservation = new ConcreteHallReservation();
        reservation = new HallReservationOption(reservation, hallOption1);
        reservation = new HallReservationOption(reservation, hallOption2);

        // Add info
        reservation.setDescription(description);
        reservation.setCustomer(customer);
        reservation.addPartOfDay(part1);
        reservation.addPartOfDay(part2);

        // Asserts for domain
        assertEquals("137.5", reservation.getPrice().toString()); // 55.0 because
                                                                 // the hall is
                                                                 // not set
        assertEquals(description, reservation.getDescription());
        assertEquals(2, reservation.getHallOptions().size());
        assertEquals("FirstName", reservation.getCustomer().getFirstName());
        assertEquals("LastName", reservation.getCustomer().getLastName());

        // Add reservation to the hall
        hall.addReservation(reservation);

        // Save the hall
        hall = hallService.save(hall);

        // Get the hall from the database
        Hall hallFromDb = hallService.findById(hall.getId());

        // Asserts to test if it is all saved correctly
        assertEquals(1, hallFromDb.getReservations().size());

        HallReservation foundHallReservation = hallFromDb.getReservations().iterator().next();

        assertEquals("387.5", foundHallReservation.getPrice().toString());
        assertEquals(description, foundHallReservation.getDescription());
        assertEquals(2, foundHallReservation.getHallOptions().size());
        assertEquals("FirstName", foundHallReservation.getCustomer().getFirstName());
        assertEquals("LastName", foundHallReservation.getCustomer().getLastName());

        // PartOfDays tests
        assertEquals(2, foundHallReservation.getPartOfDays().size());
        // It should be ordered, so afternoon should be the first one
        assertEquals("Afternoon", foundHallReservation.getPartOfDays().get(0).getDescription());
        assertEquals(13, foundHallReservation.getPartOfDays().get(0).getStartTime().getHours());
        assertEquals(18, foundHallReservation.getPartOfDays().get(0).getEndTime().getHours());

        assertEquals("Evening", foundHallReservation.getPartOfDays().get(1).getDescription());
        assertEquals(18, foundHallReservation.getPartOfDays().get(1).getStartTime().getHours());
        assertEquals(23, foundHallReservation.getPartOfDays().get(1).getEndTime().getHours());
        assertEquals(calendar.getTime().getMonth(),
                foundHallReservation.getPartOfDays().get(1).getStartTime().getMonth());
        assertEquals(calendar.getTime().getDate(),
                foundHallReservation.getPartOfDays().get(1).getStartTime().getDate());

        // Test to update the hallReservation
        String updatedDescription = "Updated description";

        // Build the request
        HallReservationRequest request = new HallReservationRequest();
        // We need to set the services manually
        request.setHallService(hallService);
        request.setHallOptionService(hallOptionService);
        request.setCustomerService(customerService);

        request.setDescription(updatedDescription);
        request.setState(HallReservationState.PAID);

        // Create a new hall so we can change the hall in the reservation
        hall2 = hallService.save(hall2);
        request.setHall(hall2.getId());

        // Don't change the customer
        request.setCustomer(foundHallReservation.getCustomer().getId());

        // Don't set the hallOptions, so this will be an empty list

        // Set new partOfDays
        List<PartOfDayRequest> partOfDaysRequest = new ArrayList<>();

        PartOfDayRequest partOfDayRequest1;

        calendar.add(Calendar.DATE, 1); // Add again 1 day

        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String updatedDateString = format.format(calendar.getTime());

        partOfDayRequest1 = new PartOfDayRequest();
        partOfDayRequest1.setDate(updatedDateString);
        partOfDayRequest1.setPartOfDay("morning");

        partOfDaysRequest.add(partOfDayRequest1);

        request.setPartOfDays(partOfDaysRequest);
        
        HallReservation updatedHallReservation = hallReservationService.update(foundHallReservation, request);

        assertEquals("50.0", updatedHallReservation.getPrice().toString());
        assertEquals(hall2, updatedHallReservation.getHall());
        assertEquals(updatedDescription, updatedHallReservation.getDescription());
        // HallOptions should be removed:
        assertEquals(0, updatedHallReservation.getHallOptions().size());
        // The customer should not be changed:
        assertEquals("FirstName", updatedHallReservation.getCustomer().getFirstName());
        assertEquals("LastName", updatedHallReservation.getCustomer().getLastName());

        // The PartOfDays are changed 
        assertEquals(1, updatedHallReservation.getPartOfDays().size());
        assertEquals("Morning", updatedHallReservation.getPartOfDays().get(0).getDescription());
        assertEquals(8, updatedHallReservation.getPartOfDays().get(0).getStartTime().getHours());
        assertEquals(13, updatedHallReservation.getPartOfDays().get(0).getEndTime().getHours());
        assertEquals(calendar.getTime().getMonth(),
                updatedHallReservation.getPartOfDays().get(0).getStartTime().getMonth());
        assertEquals(calendar.getTime().getDate(),
                updatedHallReservation.getPartOfDays().get(0).getStartTime().getDate());

        // Test to delete the hallReservation
        hallReservationService.delete(updatedHallReservation);

        assertEquals(null, hallReservationService.findById(updatedHallReservation.getId()));
    }
}