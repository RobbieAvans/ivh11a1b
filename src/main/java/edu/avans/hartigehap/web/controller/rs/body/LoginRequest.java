package edu.avans.hartigehap.web.controller.rs.body;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String email;
    private String password;
}
