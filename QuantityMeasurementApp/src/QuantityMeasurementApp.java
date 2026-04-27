/**
 * QuantityMeasurementApp demonstrates unit comparison and conversion
 * for length measurements using a DRY and scalable design.
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

        public double fromFeet(double feetValue) {
            return feetValue / toFeetFactor;
        }
    }

    /**
     * Immutable value object representing a length.
     */
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
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Value must be finite");
            }
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        /**
         * Converts this Quantity to a target unit.
         */
        public Quantity convertTo(LengthUnit targetUnit) {
            validate(this.value, targetUnit);
            double feet = toFeet();
            double converted = targetUnit.fromFeet(feet);
            return new Quantity(converted, targetUnit);
        }

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

    // ----------- STATIC API METHODS -----------

    /**
     * Static conversion API
     */
    public static double convert(double value, LengthUnit source, LengthUnit target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Units cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite");
        }

        double feet = source.toFeet(value);
        return target.fromFeet(feet);
    }

    // ----------- METHOD OVERLOADING DEMO -----------

    public static void demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
        double result = convert(value, from, to);
        System.out.println("convert(" + value + ", " + from + ", " + to + ") → " + result);
    }

    public static void demonstrateLengthConversion(Quantity quantity, LengthUnit to) {
        Quantity converted = quantity.convertTo(to);
        System.out.println(quantity + " → " + converted);
    }

    public static void demonstrateLengthEquality(Quantity q1, Quantity q2) {
        System.out.println(q1 + " == " + q2 + " → " + q1.equals(q2));
    }

    public static void demonstrateLengthComparison(double v1, LengthUnit u1,
                                                   double v2, LengthUnit u2) {
        Quantity q1 = new Quantity(v1, u1);
        Quantity q2 = new Quantity(v2, u2);
        demonstrateLengthEquality(q1, q2);
    }

    // ----------- MAIN METHOD -----------

    public static void main(String[] args) {

        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCH);
        demonstrateLengthConversion(3.0, LengthUnit.YARD, LengthUnit.FEET);
        demonstrateLengthConversion(36.0, LengthUnit.INCH, LengthUnit.YARD);
        demonstrateLengthConversion(1.0, LengthUnit.CENTIMETER, LengthUnit.INCH);

        demonstrateLengthComparison(1.0, LengthUnit.FEET, 12.0, LengthUnit.INCH);

        Quantity q = new Quantity(2.0, LengthUnit.YARD);
        demonstrateLengthConversion(q, LengthUnit.INCH);
    }
}