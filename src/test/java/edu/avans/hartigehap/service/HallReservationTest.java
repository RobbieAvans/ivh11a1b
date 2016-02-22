package edu.avans.hartigehap.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
		
	}
	

}
