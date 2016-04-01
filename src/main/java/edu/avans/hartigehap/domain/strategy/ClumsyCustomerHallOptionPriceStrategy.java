package edu.avans.hartigehap.domain.strategy;

import edu.avans.hartigehap.domain.HallOption;

public class ClumsyCustomerHallOptionPriceStrategy implements HallOptionPriceStrategy {

	private HallOptionPriceStrategy defaultStrategy;
	
	public ClumsyCustomerHallOptionPriceStrategy(HallOptionPriceStrategy defaultStrategy) {
		this.defaultStrategy = defaultStrategy;
	}
	
	@Override
	public double calculateInVat(HallOption hallOption) {
		return calculateExVat(hallOption) * 1.21;
	}

	@Override
	public double calculateExVat(HallOption hallOption) {		
		// The clumsy customer pays 20% more for every hallOption than the defaultStrategy
		return defaultStrategy.calculateExVat(hallOption) * 1.2;
	}

}
