package edu.avans.hartigehap.domain.handler.login;

import edu.avans.hartigehap.domain.Authenticatable;
import edu.avans.hartigehap.domain.handler.Handler;
import edu.avans.hartigehap.web.controller.rs.body.LoginRequest;

public abstract class LoginHandlerTemplate<T extends Authenticatable> extends Handler<LoginRequest, Authenticatable> {

    @Override
    public Authenticatable handleRequest(LoginRequest request) {
        T object = findByEmail(request.getEmail());
        
        if (object != null) {
            // Check for password
            if (object.checkPassword(request.getPassword())) {
                object.refreshSessionID();

                return save(object);
            }
        }
        
        return (successor != null) ? successor.handleRequest(request) : null;
    }
    
    public abstract T findByEmail(String email);
    public abstract T save(T object);
}
