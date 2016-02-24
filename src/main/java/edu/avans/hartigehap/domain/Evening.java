package edu.avans.hartigehap.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Evening extends PartOfDay{
	
	private static int startTime = 13;
	private static int endTime = 18;
	
	@SuppressWarnings("deprecation")
	public Evening(Date date) {
		super(date);
		
		date.setMinutes(0);
		date.setSeconds(0);
		
		Date StartTime = (Date) date.clone();
		Date EndTime = (Date) date.clone();
		
		StartTime.setHours(startTime);
		EndTime.setHours(endTime);
		
		setStartTime(StartTime);
		setEndTime(EndTime);
		setDescription("Evening");
	}
	

	//public Noon(Date StartTime, Date EndTime, String Description) {
	//	super(StartTime, EndTime, Description);
	//}
	
	

}
