package edu.avans.hartigehap.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import edu.avans.hartigehap.domain.CancelledState;
import edu.avans.hartigehap.domain.ConcreteHallReservation;
import edu.avans.hartigehap.domain.CreatedState;
import edu.avans.hartigehap.domain.HallOption;
import edu.avans.hartigehap.domain.HallReservation;
import edu.avans.hartigehap.domain.HallReservationOption;
import edu.avans.hartigehap.domain.PaidState;
import edu.avans.hartigehap.domain.PartOfDayFactory;
import edu.avans.hartigehap.repository.HallOptionRepository;
import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;

public class HallReservationTest extends AbstractTransactionRollbackTest {

	@Autowired
	private HallReservationService hallReservationService;

	@Autowired
	private HallOptionRepository hallOptionRepository;

	@Test
	@Rollback(false)
	public void createHallReservationNoDecoration() {
		// Create HallOptions in database
		HallOption hall = new HallOption("Hall", 100.00);
		hallOptionRepository.save(hall);
		
		HallOption option = new HallOption("Wifi", 5.00);
		hallOptionRepository.save(option);

		HallOption option2 = new HallOption("DJ", 50.00);
		hallOptionRepository.save(option2);

		List<HallReservation> foundHallReservations;

		HallReservation reservation = new ConcreteHallReservation(hall);
		HallReservation hallOption1 = new HallReservationOption(reservation, option);
		HallReservation hallOption2 = new HallReservationOption(hallOption1, option2);
		hallReservationService.save(hallOption2);
		
		foundHallReservations = hallReservationService.findAll();

		HallReservation foundReservation = foundHallReservations.get(2);
		
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
		
		HallOption hall = new HallOption("Hall", 100.00);
		hallOptionRepository.save(hall);

		HallReservation reservation = 
				new HallReservationOption(new ConcreteHallReservation(hall), option);
		
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
