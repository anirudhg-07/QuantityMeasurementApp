import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    private static final double EPSILON = 1e-6;

    @Test
    void testAddition_SameUnit_FeetPlusFeet() {
        var result = new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.FEET)
                .add(new QuantityMeasurementApp.Quantity(2.0, QuantityMeasurementApp.LengthUnit.FEET));

        assertEquals(3.0, resultValue(result), EPSILON);
    }

    @Test
    void testAddition_CrossUnit_FeetPlusInches() {
        var result = new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.FEET)
                .add(new QuantityMeasurementApp.Quantity(12.0, QuantityMeasurementApp.LengthUnit.INCH));

        assertEquals(2.0, resultValue(result), EPSILON);
    }

    @Test
    void testAddition_CrossUnit_InchPlusFeet() {
        var result = new QuantityMeasurementApp.Quantity(12.0, QuantityMeasurementApp.LengthUnit.INCH)
                .add(new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.FEET));

        assertEquals(24.0, resultValue(result), EPSILON);
    }

    @Test
    void testAddition_CrossUnit_YardPlusFeet() {
        var result = new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.YARD)
                .add(new QuantityMeasurementApp.Quantity(3.0, QuantityMeasurementApp.LengthUnit.FEET));

        assertEquals(2.0, resultValue(result), EPSILON);
    }

    @Test
    void testAddition_Commutativity() {
        var q1 = new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        var q2 = new QuantityMeasurementApp.Quantity(12.0, QuantityMeasurementApp.LengthUnit.INCH);

        var r1 = q1.add(q2);
        var r2 = q2.add(q1);

        assertEquals(r1.toString(), r2.toString());
    }

    @Test
    void testAddition_WithZero() {
        var result = new QuantityMeasurementApp.Quantity(5.0, QuantityMeasurementApp.LengthUnit.FEET)
                .add(new QuantityMeasurementApp.Quantity(0.0, QuantityMeasurementApp.LengthUnit.INCH));

        assertEquals(5.0, resultValue(result), EPSILON);
    }

    @Test
    void testAddition_NegativeValues() {
        var result = new QuantityMeasurementApp.Quantity(5.0, QuantityMeasurementApp.LengthUnit.FEET)
                .add(new QuantityMeasurementApp.Quantity(-2.0, QuantityMeasurementApp.LengthUnit.FEET));

        assertEquals(3.0, resultValue(result), EPSILON);
    }

    @Test
    void testAddition_NullSecondOperand() {
        var q = new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class, () -> q.add(null));
    }

    // Helper
    private double resultValue(QuantityMeasurementApp.Quantity q) {
        return Double.parseDouble(q.toString().split("[(), ]+")[1]);
    }
}