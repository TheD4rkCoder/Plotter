package com.example.plotter;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

public class DerivativeCalculator {

    public static double calculateDerivative(String function, String variable, double point, double delta) {
        Argument x = new Argument(variable, point);
        Expression e = new Expression(function, x);
        double valueBefore = e.calculate();
        x.setArgumentValue(point + delta);
        double valueAfter = e.calculate();
        double derivative = (valueAfter - valueBefore) / delta;
        return roundToDecimalPlaces(derivative, 3);
    }

    public static double roundToDecimalPlaces(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
}