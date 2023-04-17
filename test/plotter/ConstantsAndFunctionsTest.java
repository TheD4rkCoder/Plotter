package plotter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstantsAndFunctionsTest {
    @Test
    @DisplayName("Test for the value of \"PI\"")
    public void piTest() {
        assertEquals(Math.PI, Mparser.calculate("PI"));
    }
}
