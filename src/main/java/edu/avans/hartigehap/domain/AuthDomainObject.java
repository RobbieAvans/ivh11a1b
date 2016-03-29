package edu.avans.hartigehap.domain;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;

@Configurable
@MappedSuperclass
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@JsonIgnoreProperties({ "password", "passwordEncoder" })
public abstract class AuthDomainObject extends DomainObject implements Authenticatable {
    private static final long serialVersionUID = 1L;

    @Transient
    @Autowired
    private PasswordEncoder passwordEncoder;

    private String email;
    private String password;
    private String sessionID;
    
    public void setPassword(String plainPassword) {
        password = passwordEncoder.encode(plainPassword);
    }
    
    public boolean checkPassword(String rawPassword) {
        return passwordEncoder.matches(rawPassword, password);
    }
    
    public void refreshSessionID() {
        setSessionID(java.util.UUID.randomUUID().toString());
    }
}
