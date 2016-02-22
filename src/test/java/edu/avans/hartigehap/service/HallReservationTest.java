package edu.avans.hartigehap.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.avans.hartigehap.domain.ConcreteHallReservation;
import edu.avans.hartigehap.domain.HallReservation;
import edu.avans.hartigehap.domain.HallWithWifi;
import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;

public class HallReservationTest extends AbstractTransactionRollbackTest {

    @Autowired
    private HallReservationService hallReservationService;
   
    @Test
    public void createHallReservationNoDecoration(){
    	List<HallReservation> foundHallReservations;
    	
    	HallReservation reservation = new HallWithWifi(new ConcreteHallReservation());
    	
    	hallReservationService.save(reservation);
    	foundHallReservations = hallReservationService.findAll();
    	
    	HallReservation foundReservation = foundHallReservations.get(0);    	
    	assertEquals("105.0", foundReservation.getPrice().toString());
    }

}
