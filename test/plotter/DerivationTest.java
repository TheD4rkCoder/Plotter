/**
 * DerivationTest is a test class for verifying the correct behavior
 * of the DerivativeCalculator class with various functions.
 *
 * @autor Plotter
 * @version 1.0
 */
package plotter;

import com.example.plotter.DerivativeCalculator;
import org.mariuszgromada.math.mxparser.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DerivationTest {
    private final double delta = 1e-9;

    /**
     * Test for a constant function.
     */
    @Test
    public void testConstantFunction() {
        boolean isCallSuccessful = License.iConfirmNonCommercialUse("Plotter");
        double result = DerivativeCalculator.calculateDerivative("5", "x", 2, delta);
        assertEquals(0, result, 1e-9);
    }

    /**
     * Test for calculating the derivative of a linear function.
     */
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

    /**
     * Test for calculating the derivative of a quadratic function.
     */
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

    /**
     * Test for calculating the derivative of a cubic function.
     */
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

    /**
     * Test for calculating the derivative of a logarithmic function.
     */
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

    /**
     * Test for calculating the derivative of a logarithmic function.
     */
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