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
    public void testLinearFunction() {
        double result = DerivativeCalculator.calculateDerivative("3*x", "x", 2, delta);
        assertEquals(3, result, 1e-9);
    }

    @Test
    public void testQuadraticFunction() {
        double result = DerivativeCalculator.calculateDerivative("x^2", "x", 2, delta);
        assertEquals(4, result, 1e-9);
    }

    @Test
    public void testExponentialFunction() {
        double result = DerivativeCalculator.calculateDerivative("e^x", "x", 2, delta);
        assertEquals(7.389, result, 1e-9);
    }

    @Test
    public void testTrigonometricFunction() {
        double result = DerivativeCalculator.calculateDerivative("sin(x)", "x", Math.PI / 2, delta);
        assertEquals(0, result, 1e-9);
    }
}