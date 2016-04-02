package edu.avans.hartigehap.domain.strategy;

import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.domain.PartOfDay;
import edu.avans.hartigehap.domain.hallreservation.HallReservation;

public class DefaultHallPriceStrategy implements HallPriceStrategy {

    private HallReservation hallReservation;
    private PartOfDayHallPriceStrategy partOfDayHallPriceStrategy;

    public DefaultHallPriceStrategy(PartOfDayHallPriceStrategy partOfDayHallPriceStrategy,
            HallReservation hallReservation) {
        this.hallReservation = hallReservation;
        this.partOfDayHallPriceStrategy = partOfDayHallPriceStrategy;
    }

    @Override
    public double calculateInVat(Hall hall) {
        double price = 0.0;

        for (PartOfDay partOfDay : hallReservation.getPartOfDays()) {
            price += partOfDayHallPriceStrategy.calculateInVat(hall, partOfDay);
        }

        return price;
    }

    @Override
    public double calculateExVat(Hall hall) {
        double price = 0.0;

        for (PartOfDay partOfDay : hallReservation.getPartOfDays()) {
            price += partOfDayHallPriceStrategy.calculateExVat(hall, partOfDay);
        }

        return price;
    }

}
