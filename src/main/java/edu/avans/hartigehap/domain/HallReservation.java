package edu.avans.hartigehap.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Tom GIesbergen
 */

@Entity
//optional
@Table(name = "HALLRESERVATION")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = {"description"})
public abstract class HallReservation extends DomainObject {
    private static final long 	serialVersionUID = 1L;
    private String 				description;
    private Double				price;
    
    @JoinColumn(name="description", nullable=false)
    private RestervationState 	state;
    
    public void setState(RestervationState state){
    	this.state = state;
    }
    
    public RestervationState getState(){
    	return state;
    }
}
