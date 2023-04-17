package plotter;
import com.example.plotter.PlotArea;
import org.junit.jupiter.api.function.Executable;
import org.mariuszgromada.math.mxparser.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class PlotAreaTest {

    @Test
    @DisplayName("Invalid function Exception test")
    public void invalidFunctionPlotAreaTest() {

        License.iConfirmNonCommercialUse("Plotter");
        PlotArea pa = new PlotArea();
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                pa.addFunction(new Function("f(x) = 5//x"));
            }
        });
    }

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
