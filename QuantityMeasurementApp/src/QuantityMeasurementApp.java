/**
 * UC6: Supports equality, conversion, and addition of length quantities
 */
public class QuantityMeasurementApp {

    // Base unit = FEET
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.0328084);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }

        public double fromFeet(double feet) {
            return feet / toFeetFactor;
        }
    }

    // Immutable Quantity class
    static class Quantity {
        private final double value;
        private final LengthUnit unit;
        private static final double EPSILON = 1e-6;

        public Quantity(double value, LengthUnit unit) {
            validate(value, unit);
            this.value = value;
            this.unit = unit;
        }

        private static void validate(double value, LengthUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        // ----------- ADDITION (INSTANCE METHOD) -----------

        /**
         * Adds another Quantity and returns result in THIS unit
         */
        public Quantity add(Quantity other) {
            if (other == null) {
                throw new IllegalArgumentException("Second operand cannot be null");
            }

            double sumFeet = this.toFeet() + other.toFeet();
            double resultValue = this.unit.fromFeet(sumFeet);

            return new Quantity(resultValue, this.unit);
        }

        // ----------- STATIC ADD (OVERLOADED) -----------

        public static Quantity add(Quantity q1, Quantity q2, LengthUnit targetUnit) {
            if (q1 == null || q2 == null || targetUnit == null) {
                throw new IllegalArgumentException("Arguments cannot be null");
            }

            double sumFeet = q1.toFeet() + q2.toFeet();
            double result = targetUnit.fromFeet(sumFeet);

            return new Quantity(result, targetUnit);
        }

        public static Quantity add(double v1, LengthUnit u1,
                                   double v2, LengthUnit u2,
                                   LengthUnit targetUnit) {

            return add(new Quantity(v1, u1), new Quantity(v2, u2), targetUnit);
        }

        // ----------- EQUALITY -----------

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;
            return Math.abs(this.toFeet() - other.toFeet()) < EPSILON;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(toFeet());
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // ----------- DEMO METHODS -----------

    public static void demonstrateAddition(Quantity q1, Quantity q2) {
        System.out.println("add(" + q1 + ", " + q2 + ") → " + q1.add(q2));
    }

    public static void main(String[] args) {

        demonstrateAddition(
                new Quantity(1.0, LengthUnit.FEET),
                new Quantity(2.0, LengthUnit.FEET));

        demonstrateAddition(
                new Quantity(1.0, LengthUnit.FEET),
                new Quantity(12.0, LengthUnit.INCH));

        demonstrateAddition(
                new Quantity(12.0, LengthUnit.INCH),
                new Quantity(1.0, LengthUnit.FEET));

        demonstrateAddition(
                new Quantity(1.0, LengthUnit.YARD),
                new Quantity(3.0, LengthUnit.FEET));

        demonstrateAddition(
                new Quantity(2.54, LengthUnit.CENTIMETER),
                new Quantity(1.0, LengthUnit.INCH));
    }
}