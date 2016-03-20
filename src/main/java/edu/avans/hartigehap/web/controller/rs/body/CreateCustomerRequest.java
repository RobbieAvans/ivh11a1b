package edu.avans.hartigehap.web.controller.rs.body;

import lombok.Getter;

@Getter
public class CreateCustomerRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
