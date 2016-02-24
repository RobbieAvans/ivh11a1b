package edu.avans.hartigehap.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.avans.hartigehap.domain.*;
import edu.avans.hartigehap.repository.HallOptionRepository;
import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;

public class HallReservationTest extends AbstractTransactionRollbackTest {

	@Autowired
	private HallReservationService hallReservationService;

	@Autowired
	private HallOptionRepository hallOptionRepository;

	@Test
	public void createHallReservationNoDecoration() {
		// Create HallOptions in database
		HallOption option = new HallOption("Wifi", 5.00);
		hallOptionRepository.save(option);

		HallOption option2 = new HallOption("DJ", 50.00);
		hallOptionRepository.save(option2);

		List<HallReservation> foundHallReservations;

		HallReservation reservation = new HallReservationOption(
				new HallReservationOption(new ConcreteHallReservation(), option), option2);

		hallReservationService.save(reservation);
		foundHallReservations = hallReservationService.findAll();

		HallReservation foundReservation = foundHallReservations.get(0);
		assertEquals("155.0", foundReservation.getPrice().toString());
	}
	
	@Test
	public void createHallStateTest(){
		HallReservation reservation = new ConcreteHallReservation();
		
		CreatedState createdState = new CreatedState();
		createdState.doAction(reservation);
		assertEquals("Created state", reservation.getState().toString());
		
		PaidState paidState = new PaidState();
		paidState.doAction(reservation);
		assertEquals("Paid state", reservation.getState().toString());
		
		CancelledState cancelledState = new CancelledState();
		cancelledState.doAction(reservation);
		assertEquals("Cancelled state", reservation.getState().toString());	
	}
	
	@Test
	public void createPartOfDayTest(){
		PartOfDayFactory factory = new PartOfDayFactory();
		
		HallOption option = new HallOption("Wifi", 5.00);
		hallOptionRepository.save(option);

		HallReservation reservation = 
				new HallReservationOption(new ConcreteHallReservation(), option);
		
		Date date1 = new Date();
		date1.setMonth(1);
		date1.setDate(15);
		
		reservation.AddPartOfDay(factory.MakePartOfDay("morning", new Date()));
		reservation.AddPartOfDay(factory.MakePartOfDay("Evening",  new Date()));
		reservation.AddPartOfDay(factory.MakePartOfDay("afternoon", date1));
		
		hallReservationService.save(reservation);
		List<HallReservation> foundHallReservations;
		foundHallReservations = hallReservationService.findAll();
		
		HallReservation ReservationFromDB = foundHallReservations.get(0);
		
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
}
