package plotter;
import org.mariuszgromada.math.mxparser.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstantsAndFunctionsTest {

    @Test
    @DisplayName("Test for the value of \"PI\"")
    public void piTest() {
        License.iConfirmNonCommercialUse("Plotter");

        Expression e = new Expression("pi");
        assertEquals(Math.PI, e.calculate());
    }

}