package edu.avans.hartigehap.domain;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Afternoon extends PartOfDay {

	private static final long serialVersionUID = 1L;

	private static int startTimeHour = 18;
	private static int endTimeHour = 23;

	@SuppressWarnings("deprecation")
	public Afternoon(Date date) {

		date.setMinutes(0);
		date.setSeconds(0);

		Date startTime = (Date) date.clone();
		Date endTime = (Date) date.clone();

		startTime.setHours(startTimeHour);
		endTime.setHours(endTimeHour);

		setStartTime(startTime);
		setEndTime(endTime);
		setDescription("Afternoon");
	}

}
