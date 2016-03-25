package edu.avans.hartigehap.web.controller.rs.body;

public class InvalidJsonRequestException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidJsonRequestException(String msg) {
        super(msg);
    }
}
