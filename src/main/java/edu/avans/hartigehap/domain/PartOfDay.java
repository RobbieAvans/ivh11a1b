package edu.avans.hartigehap.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.hallreservation.HallReservation;
import lombok.Getter;
import lombok.Setter;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
public abstract class PartOfDay extends DomainObject {
    private static final long serialVersionUID = 1L;

    private Date startTime;
    private Date endTime;
    private String description;
    private Double priceFactor;

    @ManyToOne
    @JsonIgnore
    private HallReservation hallReservation;
    
    /**
     * Method that should check if another ParyOfDay can be added after this
     * 
     * @param after
     * @return
     */
    public abstract boolean canAddAfter(PartOfDay after);
}
