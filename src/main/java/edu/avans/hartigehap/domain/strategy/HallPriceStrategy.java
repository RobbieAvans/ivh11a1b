package edu.avans.hartigehap.domain.strategy;

import edu.avans.hartigehap.domain.Hall;

public interface HallPriceStrategy {
	
	public double calculateInVat(Hall hall);
	
	public double calculateExVat(Hall hall);
}
