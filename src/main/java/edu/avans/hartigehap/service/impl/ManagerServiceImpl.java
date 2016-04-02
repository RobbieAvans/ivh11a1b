package edu.avans.hartigehap.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.avans.hartigehap.aop.MyExecutionTime;
import edu.avans.hartigehap.domain.Manager;
import edu.avans.hartigehap.repository.ManagerRepository;
import edu.avans.hartigehap.service.ManagerService;

@Service("managerService")
@Repository
@Transactional
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerRepository managerRepository;
    
    @Override
    public Manager save(Manager manager) {
        return managerRepository.save(manager);
    }

    @Override
    public Manager findByEmail(String email) {
        return managerRepository.findByEmail(email);
    }

    @Override
    public Manager findBySessionID(String sessionID) {
        return managerRepository.findBySessionID(sessionID);
    }
}
