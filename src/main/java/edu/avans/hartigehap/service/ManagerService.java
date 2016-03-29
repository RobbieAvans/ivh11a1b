package edu.avans.hartigehap.service;

import edu.avans.hartigehap.domain.Manager;

public interface ManagerService {

    Manager findByEmail(String Email);

    Manager findBySessionID(String sessionID);
    
    Manager save(Manager manager);
}
