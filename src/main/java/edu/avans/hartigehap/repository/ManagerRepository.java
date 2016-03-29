package edu.avans.hartigehap.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import edu.avans.hartigehap.domain.Manager;

public interface ManagerRepository extends PagingAndSortingRepository<Manager, Long> {
    
    Manager findByEmail(String email);
    
    Manager findBySessionID(String sessionID);
}
