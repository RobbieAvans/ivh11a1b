package edu.avans.hartigehap.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
public abstract class PartOfDay extends DomainObject {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Date startTime;
    private Date endTime;
    private String description;

    @ManyToOne()
    private HallReservation hallReservation;

}
