package edu.avans.hartigehap.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Tom Giesbergen
 */
@Entity
// optional
@Table(name = "OWNERS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = {"name"})
public class Owner extends DomainObject {
    private static final long serialVersionUID = 1L;

    private String name;
    
    @ManyToMany(cascade = javax.persistence.CascadeType.ALL)
    private List<Restaurant> restaurants;
   
}
