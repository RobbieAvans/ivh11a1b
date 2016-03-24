package edu.avans.hartigehap.domain;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.avans.hartigehap.domain.hallreservation.ConcreteHallReservation;
import edu.avans.hartigehap.domain.hallreservation.HallReservation;
import edu.avans.hartigehap.domain.hallreservation.state.HallReservationState;
import edu.avans.hartigehap.service.HallReservationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HallReservationTest.class })
@ImportResource({ "classpath:/test-root-context.xml", "classpath:*servlet-context.xml" })
public class HallReservationTest {

    private byte[] photo = new byte[] { 127, -128, 0 };
    private Customer customer = new Customer("FirstName", "LastName", "email", new DateTime(), 0, "description", photo);
    
    @Test
    public void stateCorrectTransitionTest() throws StateException {

        HallReservation hallReservation = new ConcreteHallReservation();
        hallReservation.setCustomer(customer);
        
        // Default state is SubmittedState
        assertEquals(HallReservationState.CONCEPT, hallReservation.getState());
        
        hallReservation.cancelReservation();
        assertEquals(HallReservationState.CANCELLED, hallReservation.getState());

        hallReservation.undoReservation();
        assertEquals(HallReservationState.CONCEPT, hallReservation.getState());

        hallReservation.confirmReservation();
        assertEquals(HallReservationState.FINAL, hallReservation.getState());
        
        hallReservation.payReservation();
        assertEquals(HallReservationState.PAID, hallReservation.getState());
    }
    
    @Test
    public void confirmCancelledTest() throws StateException {
        HallReservation hallReservation = new ConcreteHallReservation();
        // Set the hallReservationServicemock
        HallReservationService hallReservationServiceMock = Mockito.mock(HallReservationService.class);
        hallReservation.setHallReservationService(hallReservationServiceMock);
        
        hallReservation.setCustomer(customer);
        
        // Default state is SubmittedState
        assertEquals(HallReservationState.CONCEPT, hallReservation.getState());
        
        hallReservation.cancelReservation();
        assertEquals(HallReservationState.CANCELLED, hallReservation.getState());

        hallReservation.confirmReservation();
        // Check if the delete method is called on the mock
        verify(hallReservationServiceMock).delete(hallReservation);
    }
    
    @Test(expected = StateException.class)
    public void stateExceptionTest() throws StateException {
        HallReservation hallReservation = new ConcreteHallReservation();
        hallReservation.setCustomer(customer);
        
        // Default state is SubmittedState
        assertEquals(HallReservationState.CONCEPT, hallReservation.getState());
        
        hallReservation.undoReservation(); 
        // Undo is not possible in the CONCEPT state, we expect an StateException
    }
}