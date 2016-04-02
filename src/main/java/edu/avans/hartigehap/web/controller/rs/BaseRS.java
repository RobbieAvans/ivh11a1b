package edu.avans.hartigehap.web.controller.rs;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import edu.avans.hartigehap.domain.Authenticatable;
import edu.avans.hartigehap.domain.Manager;
import edu.avans.hartigehap.domain.handler.Handler;
import edu.avans.hartigehap.domain.handler.login.CustomerSessionHandler;
import edu.avans.hartigehap.domain.handler.login.ManagerSessionHandler;
import edu.avans.hartigehap.web.controller.rs.body.InvalidJsonRequestException;
import edu.avans.hartigehap.web.controller.rs.security.AuthCallback;
import edu.avans.hartigehap.web.controller.rs.security.NotAuthorizedException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseRS {

    @Autowired
    protected View jsonView;

    protected static final String SUCCESS_FIELD = "success";
    protected static final String DATA_FIELD = "data";

    /**
     * Create a error response with the given message
     * 
     * @param message
     * @return
     */
    protected ModelAndView createErrorResponse(String message) {
        ModelAndView modelAndView = new ModelAndView(jsonView, SUCCESS_FIELD, false);
        modelAndView.addObject(DATA_FIELD, message);

        return modelAndView;
    }

    /**
     * Create a successful response with the given object
     * 
     * @param object
     * @return
     */
    protected ModelAndView createSuccessResponse(Object object) {
        ModelAndView modelAndView = new ModelAndView(jsonView, SUCCESS_FIELD, true);
        modelAndView.addObject(DATA_FIELD, object);

        return modelAndView;
    }

    protected ModelAndView shouldBeInRole(String sessionID, String[] roles, AuthCallback callback) {

        return shouldBeAuthenticated(sessionID, (Authenticatable auth) -> {
            if (Arrays.asList(roles).contains(auth.getRole())) {
                return callback.handleRequest(auth);
            }

            throw new NotAuthorizedException();
        });
    }

    protected ModelAndView shouldBeInRole(String sessionID, String role, AuthCallback callback) {
        return shouldBeInRole(sessionID, new String[] { role }, callback);
    }

    protected ModelAndView shouldBeManager(String sessionID, AuthCallback callback) {
        return shouldBeInRole(sessionID, Manager.ROLE, callback);
    }

    protected ModelAndView shouldBeAuthenticated(String sessionID, AuthCallback callback) {
        Handler<String, Authenticatable> handler = new CustomerSessionHandler();
        handler.setSuccessor(new ManagerSessionHandler());

        Authenticatable auth = handler.handleRequest(sessionID);

        if (auth != null) {
            try {
                return callback.handleRequest(auth);
            } catch (NotAuthorizedException e) {
                log.debug(e.getMessage());
                return createErrorResponse("not_authorized");
            } catch (InvalidJsonRequestException e) {
                log.debug(e.getMessage());
                return createErrorResponse(e.getMessage());
            }
        }

        return createErrorResponse("not_authorized");
    }
}
