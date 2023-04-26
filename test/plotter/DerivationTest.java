package plotter;

import com.example.plotter.DerivativeCalculator;
import org.mariuszgromada.math.mxparser.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DerivationTest {
    private final double delta = 1e-9;

    @Test
    public void testConstantFunction() {
        /* This is a workaround for the fact that the mxparser library is not free software.
         * The library is free for non-commercial use, but the author requires that you
         * confirm that you are not using it for commercial purposes. This is a way to do
         * that. */
        boolean isCallSuccessful = License.iConfirmNonCommercialUse("Plotter");
        double result = DerivativeCalculator.calculateDerivative("5", "x", 2, delta);
        assertEquals(0, result, 1e-9);
    }

    @Test
    public void testCalculateDerivative_LinearFunction() {
        String function = "3 * x + 2";
        String variable = "x";
        double point = 1;
        double delta = 0.001;
        double expected = 3.0;
        double actual = DerivativeCalculator.calculateDerivative(function, variable, point, delta);
        assertEquals(expected, actual, 0.001);
    }


    @Test
    public void testCalculateDerivative_QuadraticFunction() {
        String function = "x^2 + 2 * x + 1";
        String variable = "x";
        double point = 1;
        double delta = 0.001;
        double expected = 4.0;
        double actual = DerivativeCalculator.calculateDerivative(function, variable, point, delta);
        assertEquals(expected, actual, 0.001);
    }


    @Test
    public void testCalculateDerivative_ExponentialFunction() {
        String function = "2 * e^(x)";
        String variable = "x";
        double point = 0;
        double delta = 0.001;
        double expected = 2.0;
        double actual = DerivativeCalculator.calculateDerivative(function, variable, point, delta);
        assertEquals(expected, actual, 0.001);
    }


    @Test
    public void testCalculateDerivative_TrigonometricFunction() {
        String function = "sin(x)";
        String variable = "x";
        double point = Math.PI / 2;
        double delta = 0.001;
        double expected = 0.0;
        double actual = DerivativeCalculator.calculateDerivative(function, variable, point, delta);
        assertEquals(expected, actual, 0.001);
    }


    @Test
    public void testCalculateDerivative_CubicFunction() {
        String function = "x^3 - 2 * x^2 + x - 3";
        String variable = "x";
        double point = 2;
        double delta = 0.001;
        double expected = 5;
        double actual = DerivativeCalculator.calculateDerivative(function, variable, point, delta);
        assertEquals(expected, actual, 0.001);
    }

}