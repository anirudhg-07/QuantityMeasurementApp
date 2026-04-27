/**
 * UC7: Addition with explicit target unit support
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

        // ----------- PRIVATE UTILITY METHOD (DRY) -----------

        private static double addInFeet(Quantity q1, Quantity q2) {
            return q1.toFeet() + q2.toFeet();
        }

        // ----------- UC6 (existing) -----------

        public Quantity add(Quantity other) {
            if (other == null) throw new IllegalArgumentException("Second operand cannot be null");

            double sumFeet = addInFeet(this, other);
            double result = this.unit.fromFeet(sumFeet);

            return new Quantity(result, this.unit);
        }

        // ----------- UC7 (explicit target unit) -----------

        public static Quantity add(Quantity q1, Quantity q2, LengthUnit targetUnit) {
            if (q1 == null || q2 == null)
                throw new IllegalArgumentException("Operands cannot be null");

            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double sumFeet = addInFeet(q1, q2);
            double result = targetUnit.fromFeet(sumFeet);

            return new Quantity(result, targetUnit);
        }

        // Overloaded version for raw values
        public static Quantity add(double v1, LengthUnit u1,
                                   double v2, LengthUnit u2,
                                   LengthUnit targetUnit) {

            return add(new Quantity(v1, u1),
                    new Quantity(v2, u2),
                    targetUnit);
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

    // ----------- DEMO -----------

    public static void main(String[] args) {

        System.out.println(
                Quantity.add(
                        new Quantity(1.0, LengthUnit.FEET),
                        new Quantity(12.0, LengthUnit.INCH),
                        LengthUnit.FEET));

        System.out.println(
                Quantity.add(
                        new Quantity(1.0, LengthUnit.FEET),
                        new Quantity(12.0, LengthUnit.INCH),
                        LengthUnit.INCH));

        System.out.println(
                Quantity.add(
                        new Quantity(1.0, LengthUnit.FEET),
                        new Quantity(12.0, LengthUnit.INCH),
                        LengthUnit.YARD));
    }
}