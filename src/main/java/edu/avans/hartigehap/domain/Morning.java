package edu.avans.hartigehap.domain;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Morning extends PartOfDay {
	private static final long serialVersionUID = 1L;
	private static int startTime = 8;
	private static int endTime = 13;

	@SuppressWarnings("deprecation")
	public Morning(Date date) {
		super(date);

		date.setMinutes(0);
		date.setSeconds(0);

		Date StartTime = (Date) date.clone();
		Date EndTime = (Date) date.clone();

		StartTime.setHours(startTime);
		EndTime.setHours(endTime);

		setStartTime(StartTime);
		setEndTime(EndTime);
		setDescription("Morning");
	}

}
