package edu.avans.hartigehap.domain;

import java.util.Date;

import javax.persistence.Entity;
@Entity
public class Afternoon extends PartOfDay{

	private static final long serialVersionUID = 1L;

	private static int startTime = 18;
	private static int endTime = 23;
	
	@SuppressWarnings("deprecation")
	public Afternoon(Date date) {
		
		super(date);
		
		date.setMinutes(0);
		date.setSeconds(0);
		
		Date StartTime = (Date) date.clone();
		Date EndTime = (Date) date.clone();
		
		StartTime.setHours(startTime);
		EndTime.setHours(endTime);
		
		setStartTime(StartTime);
		setEndTime(EndTime);
		setDescription("Afternoon");
	}

	

}
