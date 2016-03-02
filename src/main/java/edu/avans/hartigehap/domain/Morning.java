package edu.avans.hartigehap.domain;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Morning extends PartOfDay {
	private static final long serialVersionUID = 1L;
	private static final int startTimeHour = 8;
	private static final int endTimeHour = 13;

	@SuppressWarnings("deprecation")
	public Morning(Date date) {

		date.setMinutes(0);
		date.setSeconds(0);

		Date startTime = (Date) date.clone();
		Date endTime = (Date) date.clone();

		startTime.setHours(startTimeHour);
		endTime.setHours(endTimeHour);

		setStartTime(startTime);
		setEndTime(endTime);
		setDescription("Morning");
	}

}
