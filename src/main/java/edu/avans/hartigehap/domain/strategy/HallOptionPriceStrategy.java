package edu.avans.hartigehap.domain.strategy;

import edu.avans.hartigehap.domain.HallOption;

public interface HallOptionPriceStrategy {

    public double calculateInVat(HallOption hallOption);

    public double calculateExVat(HallOption hallOption);
}
