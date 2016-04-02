package edu.avans.hartigehap.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Util {
    private static final int SCAlE = 2;

    public static String doubleToString(double value) {
        return new BigDecimal(value).setScale(SCAlE, RoundingMode.HALF_UP).toString();
    }

    private Util() {

    }
}
