package edu.avans.hartigehap.domain;

public interface Authenticatable {
    
    String getEmail();
    String getPassword();
    boolean checkPassword(String rawPassword);
    String getSessionID();
    void refreshSessionID();
    String getRole();
}
