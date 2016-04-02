package edu.avans.hartigehap.domain.strategy;

import edu.avans.hartigehap.domain.HallOption;
import edu.avans.hartigehap.domain.hallreservation.HallReservation;

public class DefaultHallReservationPriceStrategy implements HallReservationPriceStrategy {
    private static final double VAT = 1.21;
    private HallPriceStrategy hallPriceStrategy;
    private HallOptionPriceStrategy hallOptionPriceStrategy;

    public DefaultHallReservationPriceStrategy(HallPriceStrategy hallPriceStrategy,
            HallOptionPriceStrategy hallOptionPriceStrategy) {
        this.hallPriceStrategy = hallPriceStrategy;
        this.hallOptionPriceStrategy = hallOptionPriceStrategy;
    }

    @Override
    public double calculateInVat(HallReservation hallReservation) {
        return calculateExVat(hallReservation) * VAT;
    }

    @Override
    public double calculateExVat(HallReservation hallReservation) {
        double price = hallPriceStrategy.calculateExVat(hallReservation.getHall());

        for (HallOption hallOption : hallReservation.getHallOptions()) {
            price += hallOptionPriceStrategy.calculateExVat(hallOption);
        }

        return price;
    }
}
