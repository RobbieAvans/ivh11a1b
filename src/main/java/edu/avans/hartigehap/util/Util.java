package edu.avans.hartigehap.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Util {
	public static String doubleToString(double value) {
		return	new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).toString();
	}
}
