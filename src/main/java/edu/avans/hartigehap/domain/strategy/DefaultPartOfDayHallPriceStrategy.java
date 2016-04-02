package edu.avans.hartigehap.domain.strategy;

import edu.avans.hartigehap.domain.Evening;
import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.domain.PartOfDay;

public class DefaultPartOfDayHallPriceStrategy implements PartOfDayHallPriceStrategy {
    private static final double VAT = 1.21;
    private static final double DEFEALTPRICE = 1;
    private static final double OTHERPRICE = 1.5;
    
	@Override
	public double calculateExVat(Hall hall, PartOfDay partOfDay) {
		double price = hall.getBasePrice();
		double priceFactor = DEFEALTPRICE;
		
		// Evening is more expensive
		if (partOfDay instanceof Evening) {
		    priceFactor = OTHERPRICE;
		}
		
		price *= priceFactor;
		
		return price;
	}

	@Override
	public double calculateInVat(Hall hall, PartOfDay partOfDay) {
		return calculateExVat(hall, partOfDay) * VAT;
	}

}
