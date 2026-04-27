public class QuantityMeasurementApp {

    // ===================== ENUM (Standalone Unit) =====================
    enum LengthUnit {

        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(1.0 / 30.48);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double convertToBaseUnit(double value) {
            return value * toFeetFactor; // to FEET
        }

        public double convertFromBaseUnit(double baseValue) {
            return baseValue / toFeetFactor;
        }

        public double getConversionFactor() {
            return toFeetFactor;
        }
    }

    // ===================== VALUE OBJECT =====================
    static class QuantityLength {

        private final double value;
        private final LengthUnit unit;
        private static final double EPSILON = 1e-6;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid input");
            }
            this.value = value;
            this.unit = unit;
        }

        public double getValue() {
            return value;
        }

        public LengthUnit getUnit() {
            return unit;
        }

        // ===================== CONVERT =====================
        public QuantityLength convertTo(LengthUnit targetUnit) {
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double base = unit.convertToBaseUnit(value);
            double converted = targetUnit.convertFromBaseUnit(base);

            return new QuantityLength(converted, targetUnit);
        }

        // ===================== ADDITION =====================
        public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
            if (other == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double base1 = this.unit.convertToBaseUnit(this.value);
            double base2 = other.unit.convertToBaseUnit(other.value);

            double sum = base1 + base2;

            double result = targetUnit.convertFromBaseUnit(sum);

            return new QuantityLength(result, targetUnit);
        }

        // ===================== EQUALITY =====================
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof QuantityLength)) return false;

            QuantityLength other = (QuantityLength) obj;

            double base1 = this.unit.convertToBaseUnit(this.value);
            double base2 = other.unit.convertToBaseUnit(other.value);

            return Math.abs(base1 - base2) < EPSILON;
        }

        @Override
        public int hashCode() {
            double base = unit.convertToBaseUnit(value);
            return Double.valueOf(base).hashCode();
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // ===================== MAIN METHOD =====================
    public static void main(String[] args) {

        QuantityLength oneFoot = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength twelveInches = new QuantityLength(12.0, LengthUnit.INCHES);
        QuantityLength threeFeet = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength oneYard = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength thirtySixInches = new QuantityLength(36.0, LengthUnit.INCHES);
        QuantityLength cm = new QuantityLength(2.54, LengthUnit.CENTIMETERS);

        // ===================== EQUALITY =====================
        System.out.println("1 ft == 12 in : " + oneFoot.equals(twelveInches));

        // ===================== CONVERSION =====================
        System.out.println("1 ft to inches : " +
                oneFoot.convertTo(LengthUnit.INCHES));

        System.out.println("2.54 cm to inches : " +
                cm.convertTo(LengthUnit.INCHES));

        // ===================== ADDITION =====================
        System.out.println("1 ft + 12 in (FEET) : " +
                oneFoot.add(twelveInches, LengthUnit.FEET));

        System.out.println("1 ft + 12 in (INCHES) : " +
                oneFoot.add(twelveInches, LengthUnit.INCHES));

        System.out.println("1 ft + 12 in (YARDS) : " +
                oneFoot.add(twelveInches, LengthUnit.YARDS));

        // ===================== MORE CASES =====================
        System.out.println("1 yard + 3 feet : " +
                oneYard.add(threeFeet, LengthUnit.YARDS));

        System.out.println("36 inches == 1 yard : " +
                thirtySixInches.equals(oneYard));
    }
}