package edu.avans.hartigehap.domain;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Evening extends PartOfDay {
	private static final long serialVersionUID = 1L;
	private static int startTimeHoud = 13;
	private static int endTimeHour = 18;

	@SuppressWarnings("deprecation")
	public Evening(Date date) {

		date.setMinutes(0);
		date.setSeconds(0);

		Date startTime = (Date) date.clone();
		Date endTime = (Date) date.clone();

		startTime.setHours(startTimeHoud);
		endTime.setHours(endTimeHour);

		setStartTime(startTime);
		setEndTime(endTime);
		setDescription("Evening");
	}
}
