package edu.avans.hartigehap.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.Owner;
import edu.avans.hartigehap.domain.HallReservation;
import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;

public class OwnerServiceTest extends AbstractTransactionRollbackTest {

    @Autowired
    private OwnerService ownerService;


    @Test
    public void createOwner() {
    	Owner owner = new Owner();
    	owner.setName("Tommeke");
    	ownerService.save(owner);

    	List<Owner> foundOwners = ownerService.findByName("Tommeke");
    	assertEquals("Tommeke", foundOwners.get(0).getName());
    }
    
    @Test
    public void deleteOwner() {
    	List<Owner> foundOwners;
    	Owner owner = new Owner();
    	owner.setName("Tommeke");
    	ownerService.save(owner);

    	foundOwners = ownerService.findByName("Tommeke");
    	assertEquals("Tommeke", foundOwners.get(0).getName());
    	
    	ownerService.delete(owner);
    	foundOwners = ownerService.findAll();
        assertNotNull(foundOwners);
        assertFalse("deleted owner not in the list", foundOwners.contains(owner));
    }
}
