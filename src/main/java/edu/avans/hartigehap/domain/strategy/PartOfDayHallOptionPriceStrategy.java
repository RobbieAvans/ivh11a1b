package edu.avans.hartigehap.domain.strategy;

import edu.avans.hartigehap.domain.HallOption;
import edu.avans.hartigehap.domain.PartOfDay;

public interface PartOfDayHallOptionPriceStrategy {
    public double calculateInVat(HallOption hallOption, PartOfDay partOfDay);

    public double calculateExVat(HallOption hallOption, PartOfDay partOfDay);
}
