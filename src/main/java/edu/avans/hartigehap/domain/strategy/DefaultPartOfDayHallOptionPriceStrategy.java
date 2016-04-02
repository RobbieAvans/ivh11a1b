package edu.avans.hartigehap.domain.strategy;

import java.util.List;

import edu.avans.hartigehap.domain.HallOption;
import edu.avans.hartigehap.domain.PartOfDay;

public class DefaultPartOfDayHallOptionPriceStrategy implements PartOfDayHallOptionPriceStrategy {
    private static final double VAT = 1.21;
    private static final int FREEFROM = 3;
    private List<PartOfDay> partOfDays;

    public DefaultPartOfDayHallOptionPriceStrategy(List<PartOfDay> allPartOfDaysOfHallReservation) {
        this.partOfDays = allPartOfDaysOfHallReservation;
    }

    @Override
    public double calculateExVat(HallOption hallOption, PartOfDay partOfDay) {

        // If there are more than 3 partOfDays in the hallReservation -> the
        // price for the last HallOption is FREE!
        if (partOfDays.size() > FREEFROM && partOfDays.get(partOfDays.size() - 1).equals(partOfDay)) {
            return 0.0;
        }

        return hallOption.getBasePrice();
    }

    @Override
    public double calculateInVat(HallOption hallOption, PartOfDay partOfDay) {
        return calculateInVat(hallOption, partOfDay) * VAT;
    }

}
