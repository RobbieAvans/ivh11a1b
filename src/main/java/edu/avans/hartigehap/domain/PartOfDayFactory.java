package edu.avans.hartigehap.domain;

import java.util.Date;

public class PartOfDayFactory {
	
	public PartOfDay MakePartOfDay(String Part, Date date){
		if(Part == "" || date == null){
			return null;
		}
		if(Part.equalsIgnoreCase("MORNING")){
	       return new Morning(date);
	         
	    } else if(Part.equalsIgnoreCase("NOON")){
	       return new Noon(date);
	         
	    } else if(Part.equalsIgnoreCase("AFTERNOON")){
	       return new Afternoon(date);
	    }
		return null;
	}
}
