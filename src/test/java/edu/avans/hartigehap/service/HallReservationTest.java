package edu.avans.hartigehap.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import edu.avans.hartigehap.domain.ConcreteHallReservation;
import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.domain.HallOption;
import edu.avans.hartigehap.domain.HallReservation;
import edu.avans.hartigehap.domain.HallReservationOption;
import edu.avans.hartigehap.domain.PartOfDay;
import edu.avans.hartigehap.domain.PartOfDayFactory;
import edu.avans.hartigehap.repository.CustomerRepository;
import edu.avans.hartigehap.repository.HallOptionRepository;
import edu.avans.hartigehap.repository.HallRepository;
import edu.avans.hartigehap.repository.HallReservationRepository;
import edu.avans.hartigehap.repository.PartOfDayRepository;
import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;

public class HallReservationTest extends AbstractTransactionRollbackTest {

    @Autowired
    private HallReservationRepository hallReservationRepository;

    @Autowired
    private HallOptionRepository hallOptionRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private PartOfDayRepository partOfDayRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Rollback(false)
    public void createHallReservationWithDecoration() {
        // Create Hall in database
        Hall hall = new Hall("Grote zaal", 180);
        hallRepository.save(hall);

        // Create HallOptions in database
        HallOption hallOption = new HallOption("Hall", 100.00);
        hallOptionRepository.save(hallOption);

        HallOption option = new HallOption("Wifi", 5.00);
        hallOptionRepository.save(option);

        HallOption option2 = new HallOption("DJ", 50.00);
        hallOptionRepository.save(option2);

        // Decorate reservation
        HallReservation reservation = new ConcreteHallReservation(hallOption);
        HallReservation hallOption1 = new HallReservationOption(reservation, option);
        HallReservation hallOption2 = new HallReservationOption(hallOption1, option2);

        hall.addReservation(hallOption2);
        hallRepository.save(hall);

        // Get the hall from the database
        ArrayList<Hall> list = (ArrayList<Hall>) hallRepository.findAll();
        Hall hallFromDb = list.get(list.size()-1);
        
        //Hall hallFromDb = hallRepository.findOne(id);

        HallReservation foundReservation = hallFromDb.getReservations().iterator().next();

        assertEquals("155.0", foundReservation.getPrice().toString());
    }

    @Test
    public void createHallStateTestWithMailTemplate() {

        HallOption hallOption = new HallOption("Hall", 100.00);
        hallOptionRepository.save(hallOption);

        byte[] photo = new byte[] { 127, -128, 0 };
        Customer customer = new Customer("Sander", "LastName", "email", new DateTime(), 0, "description", photo);
        customerRepository.save(customer);

        HallReservation reservation = new ConcreteHallReservation(hallOption);
        reservation.setCustomer(customer);

        hallReservationRepository.save(reservation);

        ArrayList<HallReservation> list = (ArrayList<HallReservation>) hallReservationRepository.findAll();
        HallReservation foundReservation = list.get(list.size()-1);
        foundReservation.submitReservation();

        // Hij zou hier geen mailtje mogen sturen
        assertEquals("SubmittedState", foundReservation.getState().getState());

        foundReservation.payReservation();
        assertEquals("PaidState", foundReservation.getState().getState());

        foundReservation.cancelReservation();
        assertEquals("PaidState", foundReservation.getState().getState());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void createPartOfDayTest() {
        PartOfDayFactory factory = new PartOfDayFactory();

        HallOption hall = new HallOption("Hall", 100.00);
        hallOptionRepository.save(hall);

        Date date1 = new Date();
        date1.setMonth(1);
        date1.setDate(15);

        PartOfDay day1 = factory.makePartOfDay("morning", new Date());
        PartOfDay day2 = factory.makePartOfDay("Evening", new Date());
        PartOfDay day3 = factory.makePartOfDay("afternoon", date1);

        partOfDayRepository.save(day1);
        partOfDayRepository.save(day2);
        partOfDayRepository.save(day3);

        HallReservation reservation = new ConcreteHallReservation(hall);

        reservation.addPartOfDay(day1);
        reservation.addPartOfDay(day2);
        reservation.addPartOfDay(day3);

        hallReservationRepository.save(reservation);
        System.out.println(reservation.getState().getState());
        
        ArrayList<HallReservation> list = (ArrayList<HallReservation>) hallReservationRepository.findAll();
        HallReservation ReservationFromDB = list.get(list.size()-1);

        assertEquals("Morning", ReservationFromDB.getPartOfDays().get(0).getDescription());
        assertEquals(8, ReservationFromDB.getPartOfDays().get(0).getStartTime().getHours());
        assertEquals(13, ReservationFromDB.getPartOfDays().get(0).getEndTime().getHours());
        assertEquals(new Date().getMonth(), ReservationFromDB.getPartOfDays().get(0).getStartTime().getMonth());
        assertEquals(new Date().getDay(), ReservationFromDB.getPartOfDays().get(0).getStartTime().getDay());

        assertEquals("Evening", ReservationFromDB.getPartOfDays().get(1).getDescription());
        assertEquals(13, ReservationFromDB.getPartOfDays().get(1).getStartTime().getHours());
        assertEquals(18, ReservationFromDB.getPartOfDays().get(1).getEndTime().getHours());

        assertEquals("Afternoon", ReservationFromDB.getPartOfDays().get(2).getDescription());
        assertEquals(18, ReservationFromDB.getPartOfDays().get(2).getStartTime().getHours());
        assertEquals(23, ReservationFromDB.getPartOfDays().get(2).getEndTime().getHours());
        assertEquals(1, ReservationFromDB.getPartOfDays().get(2).getStartTime().getMonth());
        assertEquals(15, ReservationFromDB.getPartOfDays().get(2).getStartTime().getDate());
    }

    @Test
    public void createHallReservationCustomer() {

        HallOption hallOption = new HallOption("Hall", 100.00);
        hallOptionRepository.save(hallOption);

        byte[] photo = new byte[] { 127, -128, 0 };
        Customer customer = new Customer("FirstName", "LastName", "email", new DateTime(), 0, "description", photo);
        customerRepository.save(customer);

        HallReservation reservation = new ConcreteHallReservation(hallOption);
        reservation.setCustomer(customer);

        hallReservationRepository.save(reservation);

        ArrayList<HallReservation> list = (ArrayList<HallReservation>) hallReservationRepository.findAll();
        HallReservation hallReservationFromDb = list.get(list.size()-1);

        assertEquals("FirstName", hallReservationFromDb.getCustomer().getFirstName());
        assertEquals("LastName", hallReservationFromDb.getCustomer().getLastName());
    }
}
