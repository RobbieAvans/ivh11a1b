package edu.avans.hartigehap.domain.strategy;

import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.domain.PartOfDay;

public interface PartOfDayHallPriceStrategy {
	public double calculate(Hall hall, PartOfDay partOfDay);
}
