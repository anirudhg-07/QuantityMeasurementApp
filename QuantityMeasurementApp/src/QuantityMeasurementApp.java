// =======================
// UC9 WEIGHT UNIT SYSTEM
// =======================

enum WeightUnit {

    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double toKgFactor;

    WeightUnit(double toKgFactor) {
        this.toKgFactor = toKgFactor;
    }

    public double toBase(double value) {
        return value * toKgFactor; // convert to KG
    }

    public double fromBase(double baseValue) {
        return baseValue / toKgFactor; // convert from KG
    }
}


// =======================
// QuantityWeight CLASS
// =======================

class QuantityWeight {

    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null || Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Invalid input");
        }
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public WeightUnit getUnit() {
        return unit;
    }

    // Convert to target unit
    public QuantityWeight convertTo(WeightUnit target) {
        double base = unit.toBase(value);
        double converted = target.fromBase(base);
        return new QuantityWeight(converted, target);
    }

    // ADD (default unit = first operand)
    public QuantityWeight add(QuantityWeight other) {
        return add(other, this.unit);
    }

    // ADD (explicit target unit)
    public QuantityWeight add(QuantityWeight other, WeightUnit target) {

        double sumInBase =
                this.unit.toBase(this.value)
                        + other.unit.toBase(other.value);

        double result = target.fromBase(sumInBase);

        return new QuantityWeight(result, target);
    }

    // EQUALITY (compare in KG)
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (!(obj instanceof QuantityWeight)) return false;

        QuantityWeight other = (QuantityWeight) obj;

        double thisBase = this.unit.toBase(this.value);
        double otherBase = other.unit.toBase(other.value);

        return Math.abs(thisBase - otherBase) < 0.0001;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(unit.toBase(value));
    }

    @Override
    public String toString() {
        return "QuantityWeight(" + value + ", " + unit + ")";
    }
}


// =======================
// MAIN APP (DEMO)
// =======================

public class Main {

    public static void main(String[] args) {

        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

        System.out.println("Equality: " + w1.equals(w2)); // true

        System.out.println("Convert KG → G: " +
                w1.convertTo(WeightUnit.GRAM));

        System.out.println("Add (default): " +
                w1.add(w2));

        System.out.println("Add (target GRAM): " +
                w1.add(w2, WeightUnit.GRAM));

        System.out.println("Add (POUND): " +
                w1.add(w2, WeightUnit.POUND));
    }
}