package edu.avans.hartigehap.domain;

import javax.persistence.Entity;
import javax.persistence.PostLoad;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Tom GIesbergen
 */

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true, of = {})
@NoArgsConstructor
public class ConcreteHallReservation extends HallReservation {
    private static final long serialVersionUID = 1L;

    public ConcreteHallReservation(Hall hall) {
        super(hall);
        addObservers();
    }
    
    @PostLoad
    public void addObservers() {
        addObserver(Mailer.getInstance());
    }
}
