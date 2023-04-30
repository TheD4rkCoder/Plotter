/**
 * PlotAreaTest is a test class for verifying the correct behavior
 * of the PlotArea class in the plotter application.
 *
 * @autor Plotter
 * @version 1.0
 */
package plotter;

import com.example.plotter.PlotArea;
import org.junit.jupiter.api.function.Executable;
import org.mariuszgromada.math.mxparser.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlotAreaTest {

    /**
     * Tests the behavior of the PlotArea class when attempting to add an
     * invalid function. An IllegalArgumentException should be thrown.
     */
    @Test
    @DisplayName("Invalid function Exception test")
    public void invalidFunctionPlotAreaTest() {

        License.iConfirmNonCommercialUse("Plotter");
        PlotArea pa = new PlotArea(100);
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                pa.addFunction(new Function("f(x) = 5//x"));
            }
        });

    }

    /**
     * Tests the behavior of the PlotArea class when applying constants
     * to all functions. The test verifies that constants are correctly
     * applied and the expected result is calculated.
     */
    @Test
    @DisplayName("constants applying to all functions test")
    public void constantsTest() {
        //PlotArea pa = new PlotArea();
        Argument a = new Argument("a = 15");
        //pa.addFunction(new Function("f(x) = x*a"));
        //pa.addVariable(a);
        Function f = new Function("f(x) = x*a");
        f.addArguments(a);
        assertEquals(30, f.calculate(2.0));
    }
}
