package edu.avans.hartigehap.domain.strategy;

import edu.avans.hartigehap.domain.Evening;
import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.domain.PartOfDay;

public class DefaultPartOfDayHallPriceStrategy implements PartOfDayHallPriceStrategy {
	
	@Override
	public double calculate(Hall hall, PartOfDay partOfDay) {
		double price = hall.getBasePrice();
		double priceFactor = 1.0;
		
		// Evening is more expensive
		if (partOfDay instanceof Evening) {
			priceFactor = 1.5;
		}
		
		price *= priceFactor;
		
		return price;
	}

}
