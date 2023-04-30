/**
 * ConstantsAndFunctionsTest is a test class for verifying the correct behavior
 * of mathematical constants and functions using the mxParser library.
 *
 * @author Plotter
 * @version 1.0
 */
package plotter;

import org.mariuszgromada.math.mxparser.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstantsAndFunctionsTest {
    /**
     * Test for the value of "PI".
     */
    @Test
    @DisplayName("Test for the value of \"PI\"")
    public void piTest() {
        License.iConfirmNonCommercialUse("Plotter");
        Expression e = new Expression("pi");

        assertEquals(Math.PI, e.calculate());
    }

    /**
     * Test for the sin function.
     */
    @Test
    @DisplayName("Test for sin")
    public void sinTest() {
        Expression e = new Expression("sin(pi/2)");
        assertEquals(1, e.calculate());
    }
}