package edu.avans.hartigehap.domain.strategy;

import edu.avans.hartigehap.domain.hallreservation.HallReservation;

public interface HallReservationPriceStrategyFactory {

    public HallReservationPriceStrategy create(HallReservation hallReservation);
}
