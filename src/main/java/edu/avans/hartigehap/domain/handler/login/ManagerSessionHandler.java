package edu.avans.hartigehap.domain.handler.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import edu.avans.hartigehap.domain.Authenticatable;
import edu.avans.hartigehap.domain.Manager;
import edu.avans.hartigehap.domain.handler.Handler;
import edu.avans.hartigehap.service.ManagerService;

@Configurable
public class ManagerSessionHandler extends Handler<String, Authenticatable> {

    @Autowired
    private ManagerService managerService;

    @Override
    public Authenticatable handleRequest(String request) {
        Manager manager = managerService.findBySessionID(request);

        if (manager != null)
            return manager;

        return (successor != null) ? successor.handleRequest(request) : null;
    }

}
