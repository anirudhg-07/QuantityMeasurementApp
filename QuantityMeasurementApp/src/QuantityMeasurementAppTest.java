import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    QuantityMeasurementApp.QuantityLength q;

    // ===================== UC1: Equality =====================
    @Test
    void testEquality_SameFeetValue() {
        var a = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);

        assertTrue(a.equals(b));
    }

    @Test
    void testEquality_FeetAndInches() {
        var feet = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var inches = new QuantityMeasurementApp.QuantityLength(12.0,
                QuantityMeasurementApp.LengthUnit.INCHES);

        assertTrue(feet.equals(inches));
    }

    // ===================== UC5: Conversion =====================
    @Test
    void testConvert_FeetToInches() {
        var feet = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);

        var result = feet.convertTo(QuantityMeasurementApp.LengthUnit.INCHES);

        assertEquals(12.0, result.getValue(), 0.0001);
    }

    @Test
    void testConvert_InchesToFeet() {
        var inches = new QuantityMeasurementApp.QuantityLength(12.0,
                QuantityMeasurementApp.LengthUnit.INCHES);

        var result = inches.convertTo(QuantityMeasurementApp.LengthUnit.FEET);

        assertEquals(1.0, result.getValue(), 0.0001);
    }

    // ===================== UC6: Addition =====================
    @Test
    void testAddition_SameUnitFeet() {
        var a = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.QuantityLength(2.0,
                QuantityMeasurementApp.LengthUnit.FEET);

        var result = a.add(b, QuantityMeasurementApp.LengthUnit.FEET);

        assertEquals(3.0, result.getValue(), 0.0001);
    }

    @Test
    void testAddition_FeetAndInches() {
        var feet = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var inches = new QuantityMeasurementApp.QuantityLength(12.0,
                QuantityMeasurementApp.LengthUnit.INCHES);

        var result = feet.add(inches, QuantityMeasurementApp.LengthUnit.FEET);

        assertEquals(2.0, result.getValue(), 0.0001);
    }

    // ===================== UC7: Target Unit Addition =====================
    @Test
    void testAddition_WithYards() {
        var feet = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var inches = new QuantityMeasurementApp.QuantityLength(12.0,
                QuantityMeasurementApp.LengthUnit.INCHES);

        var result = feet.add(inches, QuantityMeasurementApp.LengthUnit.YARDS);

        assertEquals(0.6667, result.getValue(), 0.01);
    }

    // ===================== UC4: Extra Units =====================
    @Test
    void testYardToFeetAddition() {
        var yard = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.YARDS);
        var feet = new QuantityMeasurementApp.QuantityLength(3.0,
                QuantityMeasurementApp.LengthUnit.FEET);

        var result = yard.add(feet, QuantityMeasurementApp.LengthUnit.YARDS);

        assertEquals(2.0, result.getValue(), 0.0001);
    }

    @Test
    void testCentimeterToInchConversion() {
        var cm = new QuantityMeasurementApp.QuantityLength(2.54,
                QuantityMeasurementApp.LengthUnit.CENTIMETERS);

        var result = cm.convertTo(QuantityMeasurementApp.LengthUnit.INCHES);

        assertEquals(1.0, result.getValue(), 0.01);
    }

    // ===================== EDGE CASES =====================
    @Test
    void testNullUnitThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuantityMeasurementApp.QuantityLength(1.0, null));
    }

    @Test
    void testAdditionWithNull() {
        var feet = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class,
                () -> feet.add(null, QuantityMeasurementApp.LengthUnit.FEET));
    }

    @Test
    void testInvalidValueThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuantityMeasurementApp.QuantityLength(Double.NaN,
                        QuantityMeasurementApp.LengthUnit.FEET));
    }
}