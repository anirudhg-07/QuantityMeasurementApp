import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    // Yard tests

    @Test
    void testEquality_YardToYard_SameValue() {
        var q1 = new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.YARD);
        var q2 = new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.YARD);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_YardToFeet_EquivalentValue() {
        var q1 = new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.YARD);
        var q2 = new QuantityMeasurementApp.Quantity(3.0, QuantityMeasurementApp.LengthUnit.FEET);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_YardToInches_EquivalentValue() {
        var q1 = new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.YARD);
        var q2 = new QuantityMeasurementApp.Quantity(36.0, QuantityMeasurementApp.LengthUnit.INCH);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_YardToFeet_NonEquivalentValue() {
        var q1 = new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.YARD);
        var q2 = new QuantityMeasurementApp.Quantity(2.0, QuantityMeasurementApp.LengthUnit.FEET);

        assertFalse(q1.equals(q2));
    }

    // Centimeter tests

    @Test
    void testEquality_CentimeterToCentimeter_SameValue() {
        var q1 = new QuantityMeasurementApp.Quantity(2.0, QuantityMeasurementApp.LengthUnit.CENTIMETER);
        var q2 = new QuantityMeasurementApp.Quantity(2.0, QuantityMeasurementApp.LengthUnit.CENTIMETER);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_CentimeterToInch_EquivalentValue() {
        var q1 = new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.CENTIMETER);
        var q2 = new QuantityMeasurementApp.Quantity(0.393701, QuantityMeasurementApp.LengthUnit.INCH);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_CentimeterToFeet_NonEquivalentValue() {
        var q1 = new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.CENTIMETER);
        var q2 = new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.FEET);

        assertFalse(q1.equals(q2));
    }

    // Transitive property

    @Test
    void testEquality_MultiUnit_TransitiveProperty() {
        var yard = new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.YARD);
        var feet = new QuantityMeasurementApp.Quantity(3.0, QuantityMeasurementApp.LengthUnit.FEET);
        var inch = new QuantityMeasurementApp.Quantity(36.0, QuantityMeasurementApp.LengthUnit.INCH);

        assertTrue(yard.equals(feet));
        assertTrue(feet.equals(inch));
        assertTrue(yard.equals(inch));
    }

    // Null & reference

    @Test
    void testEquality_SameReference() {
        var q = new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.YARD);
        assertTrue(q.equals(q));
    }

    @Test
    void testEquality_NullComparison() {
        var q = new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.YARD);
        assertFalse(q.equals(null));
    }

    @Test
    void testEquality_InvalidUnit() {
        assertThrows(IllegalArgumentException.class, () -> {
            new QuantityMeasurementApp.Quantity(1.0, null);
        });
    }

    // Complex scenario

    @Test
    void testEquality_AllUnits_ComplexScenario() {
        var yard = new QuantityMeasurementApp.Quantity(2.0, QuantityMeasurementApp.LengthUnit.YARD);
        var feet = new QuantityMeasurementApp.Quantity(6.0, QuantityMeasurementApp.LengthUnit.FEET);
        var inch = new QuantityMeasurementApp.Quantity(72.0, QuantityMeasurementApp.LengthUnit.INCH);

        assertTrue(yard.equals(feet));
        assertTrue(feet.equals(inch));
        assertTrue(yard.equals(inch));
    }
}