package edu.avans.hartigehap.domain.hallreservation;

import javax.persistence.Entity;
import javax.persistence.PostLoad;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.Mailer;
import lombok.Getter;
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
public class ConcreteHallReservation extends HallReservation {
    private static final long serialVersionUID = 1L;

    public ConcreteHallReservation() {
    	super();
        addObservers();
    }
    
    @PostLoad
    public void addObservers() {
        addObserver(Mailer.getInstance());
    }
}
