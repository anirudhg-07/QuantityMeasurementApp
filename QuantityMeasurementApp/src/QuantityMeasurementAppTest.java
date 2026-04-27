import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    private static final double EPSILON = 1e-3;

    @Test
    void testAddition_ExplicitTargetUnit_Feet() {
        var result = QuantityMeasurementApp.Quantity.add(
                new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.FEET),
                new QuantityMeasurementApp.Quantity(12.0, QuantityMeasurementApp.LengthUnit.INCH),
                QuantityMeasurementApp.LengthUnit.FEET);

        assertEquals(2.0, extract(result), EPSILON);
    }

    @Test
    void testAddition_ExplicitTargetUnit_Inches() {
        var result = QuantityMeasurementApp.Quantity.add(
                new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.FEET),
                new QuantityMeasurementApp.Quantity(12.0, QuantityMeasurementApp.LengthUnit.INCH),
                QuantityMeasurementApp.LengthUnit.INCH);

        assertEquals(24.0, extract(result), EPSILON);
    }

    @Test
    void testAddition_ExplicitTargetUnit_Yards() {
        var result = QuantityMeasurementApp.Quantity.add(
                new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.FEET),
                new QuantityMeasurementApp.Quantity(12.0, QuantityMeasurementApp.LengthUnit.INCH),
                QuantityMeasurementApp.LengthUnit.YARD);

        assertEquals(0.667, extract(result), EPSILON);
    }

    @Test
    void testAddition_ExplicitTargetUnit_Commutativity() {
        var r1 = QuantityMeasurementApp.Quantity.add(
                new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.FEET),
                new QuantityMeasurementApp.Quantity(12.0, QuantityMeasurementApp.LengthUnit.INCH),
                QuantityMeasurementApp.LengthUnit.YARD);

        var r2 = QuantityMeasurementApp.Quantity.add(
                new QuantityMeasurementApp.Quantity(12.0, QuantityMeasurementApp.LengthUnit.INCH),
                new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.FEET),
                QuantityMeasurementApp.LengthUnit.YARD);

        assertEquals(extract(r1), extract(r2), EPSILON);
    }

    @Test
    void testAddition_WithZero_TargetUnit() {
        var result = QuantityMeasurementApp.Quantity.add(
                new QuantityMeasurementApp.Quantity(5.0, QuantityMeasurementApp.LengthUnit.FEET),
                new QuantityMeasurementApp.Quantity(0.0, QuantityMeasurementApp.LengthUnit.INCH),
                QuantityMeasurementApp.LengthUnit.YARD);

        assertEquals(1.667, extract(result), EPSILON);
    }

    @Test
    void testAddition_NullTargetUnit() {
        assertThrows(IllegalArgumentException.class, () ->
                QuantityMeasurementApp.Quantity.add(
                        new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.FEET),
                        new QuantityMeasurementApp.Quantity(12.0, QuantityMeasurementApp.LengthUnit.INCH),
                        null));
    }

    // Helper
    private double extract(QuantityMeasurementApp.Quantity q) {
        return Double.parseDouble(q.toString().split("[(), ]+")[1]);
    }
}