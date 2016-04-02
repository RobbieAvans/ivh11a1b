package edu.avans.hartigehap.domain.hallreservation;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.HallOption;
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
    
    @ManyToOne
    private HallOption hallOption;

    public HallReservationDecorator(HallReservation hallReservation, HallOption hallOption) {
        this.hallReservation = hallReservation;
        this.hallOption = hallOption;
        hallReservation.addObservers();
    }

    @Override
    @Transient
    public List<HallOption> getHallOptions() {
        List<HallOption> hallOptions = hallReservation.getHallOptions();
        hallOptions.add(getHallOption());
        return hallOptions;
    }
}
