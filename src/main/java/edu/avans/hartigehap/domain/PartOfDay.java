package edu.avans.hartigehap.domain;

import java.util.Date;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public abstract class PartOfDay extends DomainObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date StartTime;
	private Date EndTime;
	private String Description;
	
	//abstract void getTime();
	
	public PartOfDay(Date date){
		
	}
	
	
}