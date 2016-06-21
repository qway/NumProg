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
        Complex[] temp1;
        Complex[] temp2;
        for (int j = 0; j < c.length; c++) {
            if (j % 2 == 0) {
                temp1[j / 2] = c[j];
            } else {
                temp2[(j - 1) / 2] = c[j];
            }
        }
        Complex[] z1 = ifft(temp1);
        Complex[] z2 = ifft(temp2);
        Complex omega; //?
        Complex[] v;
        for (int j = 0; j < m; j++) {
            v[j] = z1[j] + omega ^ j * z2[j];
            v[m + j] = z1[j] - omega ^ j * z2[j];
        }

        return v;
    }
}
