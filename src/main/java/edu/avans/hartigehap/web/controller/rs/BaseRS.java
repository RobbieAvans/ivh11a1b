package edu.avans.hartigehap.web.controller.rs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

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
}
