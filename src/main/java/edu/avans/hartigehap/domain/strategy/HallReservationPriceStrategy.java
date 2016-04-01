package edu.avans.hartigehap.domain.strategy;

import edu.avans.hartigehap.domain.hallreservation.HallReservation;

public interface HallReservationPriceStrategy {
	
	public double calculateInVat(HallReservation hallReservation);
	
	public double calculateExVat(HallReservation hallReservation);
}
