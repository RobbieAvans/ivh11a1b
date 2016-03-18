package edu.avans.hartigehap.web.controller.rs.requestbody;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String email;
    private String password;
}
