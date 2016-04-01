package edu.avans.hartigehap.domain.strategy;

import edu.avans.hartigehap.domain.HallOption;
import edu.avans.hartigehap.domain.PartOfDay;
import edu.avans.hartigehap.domain.hallreservation.HallReservation;

public class DefaultHallOptionPriceStrategy implements HallOptionPriceStrategy {

	private HallReservation hallReservation;
	private PartOfDayHallOptionPriceStrategy partOfDayHallOptionPriceStrategy;
	
	public DefaultHallOptionPriceStrategy(PartOfDayHallOptionPriceStrategy partOfDayHallOptionPriceStrategy, HallReservation hallReservation) {
		this.hallReservation = hallReservation;
		this.partOfDayHallOptionPriceStrategy = partOfDayHallOptionPriceStrategy;
	}
	
	@Override
	public double calculateInVat(HallOption hallOption) {
		return calculateExVat(hallOption) * 1.21;
	}

	@Override
	public double calculateExVat(HallOption hallOption) {
		double price = 0.0;
		
		for (PartOfDay partOfDay : hallReservation.getPartOfDays()) {
			price += partOfDayHallOptionPriceStrategy.calculate(hallOption, partOfDay);
		}
		
		return price;
	}

}
