package edu.avans.hartigehap.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.Owner;
import edu.avans.hartigehap.domain.*;
import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;

public class HallReservationTest extends AbstractTransactionRollbackTest {

    @Autowired
    private HallReservationService hallReservationService;

   
    @Test
    public void createHallReservationNoDecoration(){
    	List<HallReservation> foundHallReservations;
    	
    	HallOption hallOption = new HallOption();
    	hallOption.setDescription("Prachtige TV");
    	hallOption.setPrice(14);
    	
    	
    	
    	HallReservation reservation 		= new SimpleHallReservation();
    	reservation							= new HallReservationOption(reservation,hallOption);

    	
    	hallReservationService.save(reservation);
    	foundHallReservations = hallReservationService.findAll();
    	assertEquals("114.0", foundHallReservations.get(0).cost().toString());
    }

}
