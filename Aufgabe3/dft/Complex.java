package dft;

/**
 * Klasse zum Darstellen komplexer Zahlen
 *
 * @author Sebastian Rettenberger
 */
public class Complex {
    /**
     * Realteil der Zahl
     */
    private double real;
    /**
     * Imaginaerteil der Zahl
     */
    private double imaginary;

    public Complex() {
        this(0, 0);
    }

    public Complex(double real) {
        this(real, 0);
    }

    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * Addiert zwei komplexe Zahlen.
     *
     * @return "this + other"
     */
    public Complex add(Complex other) {
        return new Complex(this.real + other.getReal(), this.imaginary + other.getImaginaer());
    }

    /**
     * Substrahiert zwei komplexe Zahlen
     *
     * @return "this - other"
     */
    public Complex sub(Complex other) {
        return new Complex(this.real - other.getReal(), this.imaginary - other.getImaginaer());
    }

    /**
     * Multipliziert zwei komplexe Zahlen
     *
     * @return "this * other"
     */
    public Complex mul(Complex other) {
        double retReal = this.real * other.getReal() - this.imaginary * other.getImaginaer();
        double retIma = this.real * other.getImaginaer() + this.imaginary * other.getReal();
        return new Complex(retReal, retIma);
    }

    /**
     * Dividiert zwei komplexe Zahlen
     *
     * @return "this / other"
     */
    public Complex div(Complex other) {
        double retReal = (this.real * other.getReal() + this.imaginary * other.getImaginaer()) / (Math.pow(other.getReal(), 2) + Math.pow(other.getImaginaer(), 2));
        double retIma = (this.imaginary * other.getReal() - this.real * other.getImaginaer()) / (Math.pow(other.getReal(), 2) + Math.pow(other.getImaginaer(), 2));
        return new Complex(retReal, retIma);
    }

    /**
     * Potzenziert die Zahl mit einem ganzzahligen Exponenten
     *
     * @return "this ^ n"
     */
    public Complex power(int n) {
        return Complex.fromPolar(Math.pow(getRadius(), n), n * getPhi());
    }

    /**
     * Gibt die komplex konjugierte Zahl zurueck
     */
    public Complex conjugate() {
        return new Complex(real, -imaginary);
    }

    /**
     * @return String representation of the number
     */
    public String toString() {
        return real + "+" + imaginary + "i";
    }

    /**
     * Gibt den Realteil der Zahl zurueck
     */
    public double getReal() {
        return real;
    }

    /**
     * Gibt den Imaginaerteil der Zahl zurueck
     */
    public double getImaginaer() {
        return imaginary;
    }

    /**
     * Berechnet den Radius der Zahl (fuer Polarkoordinaten)
     */
    public double getRadius() {
        return Math.sqrt(real * real + imaginary * imaginary);
    }

    /**
     * Berechnet den Winkel der Zahl (fuer Polarkoordinaten)
     */
    public double getPhi() {
        if (real > 0)
            return Math.atan(imaginary / real);

        if (real < 0) {
            if (imaginary >= 0)
                return Math.atan(imaginary / real) + Math.PI;
            return Math.atan(imaginary / real) - Math.PI;
        }

        // real == 0
        if (imaginary > 0)
            return Math.PI / 2;

        return -Math.PI / 2;
    }

    /**
     * Erstellt eine neue komplexe Zahl, gegeben durch den Radius r und den
     * Winkel phi.
     */
    public static Complex fromPolar(double r, double phi) {
        if (r < 0) {
            return null;
        }
        double retReal = r * Math.cos(phi);
        double retIma = r * Math.sin(phi);
        return new Complex(retReal, retIma);
    }
}