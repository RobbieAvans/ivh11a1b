package edu.avans.hartigehap.service;

import static org.junit.Assert.assertEquals;

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
		hallReservationService.save(reservation);

		HallReservation hallOption1 = new HallReservationOption(reservation, option);
		hallReservationService.save(hallOption1);
		
		HallReservation hallOption2 = new HallReservationOption(hallOption1, option2);
		hallReservationService.save(hallOption2);
		
		foundHallReservations = hallReservationService.findAll();

		HallReservation foundReservation = foundHallReservations.get(0);
		
		assertEquals("100.0", foundReservation.getPrice().toString());
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
	

}
