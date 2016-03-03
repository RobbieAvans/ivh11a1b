package edu.avans.hartigehap.domain;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class HallOption extends DomainObject {
	private static final long serialVersionUID = 1L;

	private String description;
	private Double price;

	public HallOption(String description, Double price) {
		this.description = description;
		this.price = price;
	}
}
