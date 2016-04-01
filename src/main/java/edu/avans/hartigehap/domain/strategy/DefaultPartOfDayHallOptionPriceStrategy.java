package edu.avans.hartigehap.domain.strategy;

import java.util.List;

import edu.avans.hartigehap.domain.HallOption;
import edu.avans.hartigehap.domain.PartOfDay;

public class DefaultPartOfDayHallOptionPriceStrategy implements PartOfDayHallOptionPriceStrategy {

	private List<PartOfDay> partOfDays;
	
	public DefaultPartOfDayHallOptionPriceStrategy(List<PartOfDay> allPartOfDaysOfHallReservation) {
		this.partOfDays = allPartOfDaysOfHallReservation;
	}
	
	@Override
	public double calculate(HallOption hallOption, PartOfDay partOfDay) {
		
		// If there are more than 3 partOfDays in the hallReservation -> the price for the last HallOption is FREE!
		if (partOfDays.size() > 3 && partOfDays.get(partOfDays.size() - 1).equals(partOfDay)) {
			return 0.0;
		}
		
		return hallOption.getBasePrice();
	}

}
