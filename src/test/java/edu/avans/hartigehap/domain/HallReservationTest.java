package edu.avans.hartigehap.domain;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.Test;

import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;

public class HallReservationTest extends AbstractTransactionRollbackTest {
    
    private Hall hall = new Hall("Grote zaal", 180, 100);
    private byte[] photo = new byte[] { 127, -128, 0 };
    private Customer customer = new Customer("FirstName", "LastName", "email", new DateTime(), 0, "description", photo);

    @Test
    public void stateTest() {
        HallReservation hallReservation = new ConcreteHallReservation(hall);
        hallReservation.setCustomer(customer);
        
        // Default state is SubmittedState
        assertEquals("SubmittedState", hallReservation.getState().getState());
        
        // Pay the reservation
        hallReservation.payReservation();
        assertEquals("PaidState", hallReservation.getState().getState());

        // Cancel the reservation -- not possible after paying
        hallReservation.cancelReservation();
        assertEquals("PaidState", hallReservation.getState().getState());
    }
}