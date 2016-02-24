package edu.avans.hartigehap.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;

//@Entity
//optional
//@Table(name = "Observer")
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
public abstract class Observer extends DomainObject{
	private static final long serialVersionUID = 1L;
	public abstract void Notify();
}
