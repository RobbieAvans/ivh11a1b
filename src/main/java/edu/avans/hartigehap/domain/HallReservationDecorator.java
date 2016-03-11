package edu.avans.hartigehap.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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
@NoArgsConstructor
@ToString(callSuper = true, includeFieldNames = true)
public abstract class HallReservationDecorator extends HallReservation {
    private static final long serialVersionUID = 1L;

    @OneToOne
    @Cascade({ CascadeType.ALL })
    private HallReservation hallReservation;

    public HallReservationDecorator(HallReservation hallReservation, HallOption hallOption) {
        super(hallReservation.getHall(), hallOption);
        
        // Make sure the hall is only set on the last hallReservationOption
        hallReservation.setHall(null);
        
        this.hallReservation = hallReservation;
    }

    @Override
    @Transient
    public Double getPrice() {
        Double price = getHallOption().getPrice() + hallReservation.getPrice();
  
        if (getHall() != null) {
            price += getHall().getPrice();
        }
        
        return price;
    }

    @Override
    @Transient
    public List<HallOption> getHallOptions() {
        List<HallOption> hallOptions = hallReservation.getHallOptions();
        hallOptions.add(getHallOption());
        return hallOptions;
    }
}
