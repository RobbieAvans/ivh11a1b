package edu.avans.hartigehap.domain;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import edu.avans.hartigehap.domain.strategy.HallOptionPriceStrategy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
@NoArgsConstructor
public class HallOption extends DomainObject {
    private static final long serialVersionUID = 1L;

    @Transient
    private HallOptionPriceStrategy strategy;
    
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
}
