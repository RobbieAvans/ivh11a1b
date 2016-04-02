package edu.avans.hartigehap.domain.strategy;

import edu.avans.hartigehap.domain.hallreservation.HallReservation;

public class HallReservationPriceStrategyFactoryImpl implements HallReservationPriceStrategyFactory {

    @Override
    public HallReservationPriceStrategy create(HallReservation hallReservation) {

        HallPriceStrategy hallPriceStrategy = new DefaultHallPriceStrategy(new DefaultPartOfDayHallPriceStrategy(),
                hallReservation);
        HallOptionPriceStrategy hallOptionPriceStrategy = null;

        if (hallReservation.getCustomer() != null) {
            if ("peterlimonade@gmail.com".equals(hallReservation.getCustomer().getEmail())) {
                // Peter is really a clumsy customer, he must pay more for his
                // hallOptions
                hallOptionPriceStrategy = new ClumsyCustomerHallOptionPriceStrategy(
                        getDefaultHallOptionPriceStrategy(hallReservation));
            } else if ("barrybatsbak@hotmail.com".equals(hallReservation.getCustomer().getEmail())) {
                // Barry is family, he just have to pay once for every
                // hallOption no matter how many PartOfDays there are
                hallOptionPriceStrategy = new FamilyHallOptionPriceStrategy();
            }
        }

        if (hallOptionPriceStrategy == null) {
            // Use default
            hallOptionPriceStrategy = getDefaultHallOptionPriceStrategy(hallReservation);
        }

        return new DefaultHallReservationPriceStrategy(hallPriceStrategy, hallOptionPriceStrategy);
    }

    private DefaultHallOptionPriceStrategy getDefaultHallOptionPriceStrategy(HallReservation hallReservation) {
        return new DefaultHallOptionPriceStrategy(
                new DefaultPartOfDayHallOptionPriceStrategy(hallReservation.getPartOfDays()), hallReservation);
    }

}