public class QuantityMeasurementApp {

        // Inner class representing Feet measurement
        static class Feet {
            private final double value;

            // Constructor
            public Feet(double value) {
                this.value = value;
            }

            // Getter (optional)
            public double getValue() {
                return value;
            }

            // Override equals method
            @Override
            public boolean equals(Object obj) {
                // Check same reference
                if (this == obj) return true;

                // Check null and type
                if (obj == null || getClass() != obj.getClass()) return false;

                // Cast safely
                Feet other = (Feet) obj;

                // Compare using Double.compare
                return Double.compare(this.value, other.value) == 0;
            }

            // Optional: override hashCode when equals is overridden
            @Override
            public int hashCode() {
                return Double.hashCode(value);
            }
        }

        // Main method for demonstration
        public static void main(String[] args) {
            Feet value1 = new Feet(1.0);
            Feet value2 = new Feet(1.0);

            boolean result = value1.equals(value2);

            System.out.println("Input: 1.0 ft and 1.0 ft");
            System.out.println("Output: Equal (" + result + ")");
        }
}
