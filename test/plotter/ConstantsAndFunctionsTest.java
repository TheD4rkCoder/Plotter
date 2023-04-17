package plotter;
import org.mariuszgromada.math.mxparser.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstantsAndFunctionsTest {
    @Test
    @DisplayName("Test for the value of \"PI\"")
    public void piTest() {
        Expression e = new Expression("PI");
        assertEquals(Math.PI, e.calculate());
    }

}