import org.junit.Test;
import static org.junit.Assert.*;

public class QuantityWeightTest {

    // =========================
    // 1. EQUALITY TESTS
    // =========================

    @Test
    public void testEquality_KilogramToKilogram() {
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        assertTrue(w1.equals(w2));
    }

    @Test
    public void testEquality_KilogramToGram() {
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);
        assertTrue(w1.equals(w2));
    }

    @Test
    public void testEquality_KilogramToPound() {
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(2.20462, WeightUnit.POUND);
        assertTrue(w1.equals(w2));
    }

    // =========================
    // 2. CONVERSION TESTS
    // =========================

    @Test
    public void testConvert_KgToGram() {
        QuantityWeight w = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight result = w.convertTo(WeightUnit.GRAM);
        assertEquals(1000.0, result.getValue(), 0.0001);
    }

    @Test
    public void testConvert_GramToKg() {
        QuantityWeight w = new QuantityWeight(1000.0, WeightUnit.GRAM);
        QuantityWeight result = w.convertTo(WeightUnit.KILOGRAM);
        assertEquals(1.0, result.getValue(), 0.0001);
    }

    @Test
    public void testConvert_PoundToKg() {
        QuantityWeight w = new QuantityWeight(2.20462, WeightUnit.POUND);
        QuantityWeight result = w.convertTo(WeightUnit.KILOGRAM);
        assertEquals(1.0, result.getValue(), 0.0001);
    }

    // =========================
    // 3. ADDITION TESTS
    // =========================

    @Test
    public void testAdd_SameUnit() {
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(2.0, WeightUnit.KILOGRAM);

        QuantityWeight result = w1.add(w2);

        assertEquals(3.0, result.getValue(), 0.0001);
        assertEquals(WeightUnit.KILOGRAM, result.getUnit());
    }

    @Test
    public void testAdd_CrossUnit_KgPlusGram() {
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

        QuantityWeight result = w1.add(w2);

        assertEquals(2.0, result.getValue(), 0.0001);
    }

    // =========================
    // 4. ADDITION WITH TARGET UNIT
    // =========================

    @Test
    public void testAdd_TargetUnit_Gram() {
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

        QuantityWeight result = w1.add(w2, WeightUnit.GRAM);

        assertEquals(2000.0, result.getValue(), 0.0001);
        assertEquals(WeightUnit.GRAM, result.getUnit());
    }

    @Test
    public void testAdd_TargetUnit_Pound() {
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);

        QuantityWeight result = w1.add(w2, WeightUnit.POUND);

        assertEquals(4.40924, result.getValue(), 0.0001);
    }

    // =========================
    // 5. EDGE CASES
    // =========================

    @Test
    public void testAdd_ZeroValue() {
        QuantityWeight w1 = new QuantityWeight(5.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(0.0, WeightUnit.GRAM);

        QuantityWeight result = w1.add(w2);

        assertEquals(5.0, result.getValue(), 0.0001);
    }

    @Test
    public void testNegativeValues() {
        QuantityWeight w1 = new QuantityWeight(5.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(-2.0, WeightUnit.KILOGRAM);

        QuantityWeight result = w1.add(w2);

        assertEquals(3.0, result.getValue(), 0.0001);
    }

    // =========================
    // 6. EXCEPTION TESTS
    // =========================

    @Test(expected = IllegalArgumentException.class)
    public void testNullUnit() {
        new QuantityWeight(1.0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNaNValue() {
        new QuantityWeight(Double.NaN, WeightUnit.KILOGRAM);
    }
}