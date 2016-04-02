package edu.avans.hartigehap.domain;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.strategy.HallOptionPriceStrategy;
import edu.avans.hartigehap.service.HallOptionService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Configurable
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@NoArgsConstructor
public class HallOption extends DomainObject {
    private static final long serialVersionUID = 1L;

    @Transient
    @JsonIgnore
    private HallOptionPriceStrategy strategy;

    @Transient
    @Autowired
    @JsonIgnore
    private HallOptionService hallOptionService;

    private String description;
    private Double basePrice;

    public HallOption(String description, Double basePrice) {
        this.description = description;
        this.basePrice = basePrice;
    }

    @Transient
    @JsonIgnore
    public double getPriceInVat() {
        return strategy.calculateInVat(this);
    }

    @Transient
    @JsonIgnore
    public double getPriceExVat() {
        return strategy.calculateExVat(this);
    }

    @JsonProperty
    public boolean canBeDeleted() {
        return !hallOptionService.hasHallReservations(this);
    }
}
