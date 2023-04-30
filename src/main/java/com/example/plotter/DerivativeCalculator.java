/**
 * DerivativeCalculator is a utility class for calculating the numerical derivative
 * of a mathematical function using the finite difference method.
 * It utilizes the mXparser library for parsing and evaluating mathematical expressions.
 *
 * @author Plotter
 * @version 1.0
 */
package com.example.plotter;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;


public class DerivativeCalculator {


    /**
     * Calculates the numerical derivative of a given function at a specified point.
     *
     * @param function The function as a string.
     * @param variable The variable name in the function.
     * @param point    The point at which to calculate the derivative.
     * @param delta    The finite difference used for calculating the derivative.
     * @return The numerical derivative value, rounded to 2 decimal places.
     */
    public static double calculateDerivative(String function, String variable, double point, double delta) {
        Argument x = new Argument(variable, point);
        Expression e = new Expression(function, x);
        double valueBefore = e.calculate();
        x.setArgumentValue(point + delta);
        double valueAfter = e.calculate();
        double derivative = (valueAfter - valueBefore) / delta;

        return roundToDecimalPlaces(derivative, 2);
    }


    /**
     * Rounds a double value to the specified number of decimal places.
     *
     * @param value  The double value to round.
     * @param places The number of decimal places to round to.
     * @return The rounded double value.
     */
    public static double roundToDecimalPlaces(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
}
