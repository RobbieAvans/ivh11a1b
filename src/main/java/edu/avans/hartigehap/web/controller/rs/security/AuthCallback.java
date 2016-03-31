package edu.avans.hartigehap.web.controller.rs.security;

import org.springframework.web.servlet.ModelAndView;

import edu.avans.hartigehap.domain.Authenticatable;
import edu.avans.hartigehap.web.controller.rs.body.InvalidJsonRequestException;

public interface AuthCallback {
	
	public ModelAndView handleRequest(Authenticatable auth) throws NotAuthorizedException, InvalidJsonRequestException;
}
