package edu.avans.hartigehap.domain.handler.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import edu.avans.hartigehap.domain.Manager;
import edu.avans.hartigehap.service.ManagerService;

@Configurable
public class ManagerLoginHandler extends LoginHandlerTemplate<Manager> {

    @Autowired
    private ManagerService managerService;

    @Override
    public Manager findByEmail(String email) {
        return managerService.findByEmail(email);
    }

    @Override
    public Manager save(Manager manager) {
        return managerService.save(manager);
    }
}
