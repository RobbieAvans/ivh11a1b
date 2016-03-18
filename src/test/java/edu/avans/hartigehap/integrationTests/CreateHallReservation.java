package edu.avans.hartigehap.integrationTests;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.domain.HallOption;
import edu.avans.hartigehap.domain.PartOfDay;
import edu.avans.hartigehap.domain.PartOfDayFactory;
import edu.avans.hartigehap.domain.hallreservation.ConcreteHallReservation;
import edu.avans.hartigehap.domain.hallreservation.HallReservation;
import edu.avans.hartigehap.domain.hallreservation.HallReservationOption;
import edu.avans.hartigehap.service.HallOptionService;
import edu.avans.hartigehap.service.HallService;
import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;

public class CreateHallReservation extends AbstractTransactionRollbackTest {

    @Autowired
    private HallOptionService hallOptionService;
    
    @Autowired
    private HallService hallService;
    
    /**
     * Create the objects we need
     */
    private Hall hall = new Hall("Grote zaal", 180, 100);
    private HallOption hallOption1 = new HallOption("Wifi", 5.0);
    private HallOption hallOption2 = new HallOption("DJ", 50.00);
    private byte[] photo = new byte[] { 127, -128, 0 };
    private Customer customer = new Customer("FirstName", "LastName", "email", new DateTime(), 0, "description", photo);

    @Test
    public void createHallReservation() {

        String description = "This is a nice hall";
        int month = 1;
        int day = 15;
        
        // Save the hallOptions
        hallOptionService.save(hallOption1);
        hallOptionService.save(hallOption2);
        
        // Get partOfDayObjects
        PartOfDayFactory factory = new PartOfDayFactory();
        PartOfDay part1 = factory.makePartOfDay("morning", new Date());
        
        Date date = new Date();
        date.setMonth(month);
        date.setDate(day);
        PartOfDay part2 = factory.makePartOfDay("Evening", date);
        
        // Decorate reservation
        HallReservation reservation = new ConcreteHallReservation(hall);
        reservation = new HallReservationOption(reservation, hallOption1);
        reservation = new HallReservationOption(reservation, hallOption2);
        
        // Add info
        reservation.setDescription(description);
        reservation.setCustomer(customer);
        reservation.addPartOfDay(part1);
        reservation.addPartOfDay(part2);
        
        // Asserts for domain
        assertEquals("305.0", reservation.getPrice().toString());
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
        assertEquals("305.0", foundHallReservation.getPrice().toString());
        assertEquals(description, foundHallReservation.getDescription());
        assertEquals(2, foundHallReservation.getHallOptions().size());
        assertEquals("FirstName", foundHallReservation.getCustomer().getFirstName());
        assertEquals("LastName", foundHallReservation.getCustomer().getLastName());
        
        // PartOfDays tests
        assertEquals(2, foundHallReservation.getPartOfDays().size());
        assertEquals("Morning", foundHallReservation.getPartOfDays().get(0).getDescription());
        assertEquals(8, foundHallReservation.getPartOfDays().get(0).getStartTime().getHours());
        assertEquals(13, foundHallReservation.getPartOfDays().get(0).getEndTime().getHours());

        assertEquals("Evening", foundHallReservation.getPartOfDays().get(1).getDescription());
        assertEquals(13, foundHallReservation.getPartOfDays().get(1).getStartTime().getHours());
        assertEquals(18, foundHallReservation.getPartOfDays().get(1).getEndTime().getHours());
        assertEquals(month, foundHallReservation.getPartOfDays().get(1).getStartTime().getMonth());
        assertEquals(day, foundHallReservation.getPartOfDays().get(1).getStartTime().getDate());
    }
}