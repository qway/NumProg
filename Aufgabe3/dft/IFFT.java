package dft;

import java.util.Arrays;

/**
 * Schnelle inverse Fourier-Transformation
 *
 * @author Sebastian Rettenberger
 */
public class IFFT {
    /**
     * Schnelle inverse Fourier-Transformation (IFFT).
     * <p>
     * Die Funktion nimmt an, dass die Laenge des Arrays c immer eine
     * Zweierpotenz ist. Es gilt also: c.length == 2^m fuer ein beliebiges m.
     */
    public static Complex[] ifft(Complex[] c) {
        // TODO: diese Methode ist zu implementieren

        if (c.length == 1) {
            return c;
        }

        int m = c.length / 2;
        Complex[] temp1 = new Complex[m];
        Complex[] temp2 = new Complex[m];
        for (int j = 0; j < c.length; j++) {
            if (j % 2 == 0) {
                temp1[j / 2] = c[j];
            } else {
                temp2[(j - 1) / 2] = c[j];
            }
        }
        Complex[] z1 = ifft(temp1);
        Complex[] z2 = ifft(temp2);

        //FromPolar and conjugate method
        Complex omega1 = Complex.fromPolar(1, 2 * Math.PI / c.length).conjugate();
        //Division method
        //Complex omega2 = new Complex(Math.E,0).power()

        Complex[] v = new Complex[c.length];
        for (int j = 0; j < m; j++) {
            v[j] = z1[j].add(omega1.power(j).mul(z2[j]));
            v[m + j] = z1[j].sub(omega1.power(j).mul(z2[j]));
        }

        return v;
    }
}
