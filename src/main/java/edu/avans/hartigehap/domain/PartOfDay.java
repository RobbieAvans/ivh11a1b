package edu.avans.hartigehap.domain;

import java.util.Collections;
import java.util.Date;
import java.util.List;

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

    @ManyToOne
    @JsonIgnore
    private HallReservation hallReservation;

    public void setTime(Date date, int start, int end){

        date.setMinutes(0);
        date.setSeconds(0);

        Date startTime = (Date) date.clone();
        Date endTime = (Date) date.clone();

        startTime.setHours(start);
        endTime.setHours(end);

        setStartTime(startTime);
        setEndTime(endTime);
    }
    
    /**
     * Method that should check if another ParyOfDay can be added after this
     * 
     * @param after
     * @return
     */
    public abstract boolean canAddAfter(PartOfDay after);

    public static List<PartOfDay> orderListAsc(List<PartOfDay> partOfDays) {
        Collections.sort(partOfDays, (o1, o2) -> o1.getStartTime().compareTo(o2.getStartTime()));

        return partOfDays;
    }
}
