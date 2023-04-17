package com.example.plotter;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

/**
 * This class provides methods to calculate the derivative of a given function
 * using the finite difference method.
 */
public class DerivativeCalculator {

    /**
     * Calculates the numerical derivative of the given function at a specific point.
     *
     * @param function The function to calculate the derivative of, represented as a string.
     * @param variable The variable used in the function, represented as a string.
     * @param point The point at which to calculate the derivative.
     * @param delta The step size used for finite difference calculations.
     * @return The approximate value of the derivative at the specified point, rounded to 3 decimal places.
     */
    public static double calculateDerivative(String function, String variable, double point, double delta) {
        Argument x = new Argument(variable, point);
        Expression e = new Expression(function, x);
        double valueBefore = e.calculate();
        x.setArgumentValue(point + delta);
        double valueAfter = e.calculate();
        double derivative = (valueAfter - valueBefore) / delta;
        return roundToDecimalPlaces(derivative, 3);
    }

    /**
     * Rounds a given number to a specified number of decimal places.
     *
     * @param value The number to be rounded.
     * @param places The number of decimal places to round the value to.
     * @return The rounded value.
     */
    public static double roundToDecimalPlaces(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
}
