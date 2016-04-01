package edu.avans.hartigehap.domain.strategy;

import edu.avans.hartigehap.domain.HallOption;
import edu.avans.hartigehap.domain.PartOfDay;

public interface PartOfDayHallOptionPriceStrategy {
	public double calculate(HallOption hallOption, PartOfDay partOfDay);
}
