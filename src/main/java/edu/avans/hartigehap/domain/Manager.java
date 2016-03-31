package edu.avans.hartigehap.domain;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.NoArgsConstructor;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@NoArgsConstructor
public class Manager extends AuthDomainObject {
    private static final long serialVersionUID = 1L;
    
    public static final String ROLE = "manager";

    @Transient
    @Override
    public String getRole() {
        return ROLE;
    }
}
