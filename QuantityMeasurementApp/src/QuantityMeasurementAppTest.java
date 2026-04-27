import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    @Test
    void testEquality_SameValue() {
        QuantityMeasurementApp.Feet a = new QuantityMeasurementApp.Feet(1.0);
        QuantityMeasurementApp.Feet b = new QuantityMeasurementApp.Feet(1.0);

        assertTrue(a.equals(b), "1.0 ft should be equal to 1.0 ft");
    }

    @Test
    void testEquality_DifferentValue() {
        QuantityMeasurementApp.Feet a = new QuantityMeasurementApp.Feet(1.0);
        QuantityMeasurementApp.Feet b = new QuantityMeasurementApp.Feet(2.0);

        assertFalse(a.equals(b), "1.0 ft should not equal 2.0 ft");
    }

    @Test
    void testEquality_NullComparison() {
        QuantityMeasurementApp.Feet a = new QuantityMeasurementApp.Feet(1.0);

        assertFalse(a.equals(null), "Value should not be equal to null");
    }

    @Test
    void testEquality_SameReference() {
        QuantityMeasurementApp.Feet a = new QuantityMeasurementApp.Feet(1.0);

        assertTrue(a.equals(a), "Object should be equal to itself");
    }

    @Test
    void testEquality_NonNumericInput() {
        QuantityMeasurementApp.Feet a = new QuantityMeasurementApp.Feet(1.0);
        Object obj = new Object();

        assertFalse(a.equals(obj), "Feet object should not equal non-Feet object");
    }
}