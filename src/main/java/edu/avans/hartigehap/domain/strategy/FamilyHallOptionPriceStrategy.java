package edu.avans.hartigehap.domain.strategy;

import edu.avans.hartigehap.domain.HallOption;

public class FamilyHallOptionPriceStrategy implements HallOptionPriceStrategy {

	@Override
	public double calculateInVat(HallOption hallOption) {
		return calculateExVat(hallOption) * 1.21;
	}

	@Override
	public double calculateExVat(HallOption hallOption) {
		// Family pays just once
		return hallOption.getBasePrice();
	}

}
