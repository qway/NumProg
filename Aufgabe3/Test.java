import java.util.Arrays;

import dft.DFT;
import dft.IFFT;
import dft.Complex;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        //testNewton();
        testSplines();
        //testFFT();
    }

    private static void testNewton() {
        {
            double[] x = {-1, 1, 3};
            double[] y = {-3, 1, -3};
            NewtonPolynom p = new NewtonPolynom(x, y);

            System.out.println(p.evaluate(0) + " sollte sein: 0.0");
            System.out.println(p.evaluate(1) + " sollte sein: 1.0");
            System.out.println(p.evaluate(2) + " sollte sein: 0.0");
            System.out.println("-------------------------------");
        }
        {
            double[] x = {0, 1, -1};
            double[] y = {0, 1, -1};
            NewtonPolynom p = new NewtonPolynom(x, y);

            System.out.println(p.evaluate(0) + " sollte sein: 0.0");
            System.out.println(p.evaluate(-1) + " sollte sein: -1.0");
            System.out.println(p.evaluate(-0.5) + " sollte sein: -0.5");
            System.out.println("-------------------------------");
        }
        {
            double[] x = {0, 1, -1};
            double[] y = {0, 1, -1};
            NewtonPolynom p = new NewtonPolynom(x, y);
            double[] newy = {-1, 0, 1};
            p.init(-1, 1, 2, newy);

            System.out.println(p.evaluate(0) + " sollte sein: 0.0");
            System.out.println(p.evaluate(-1) + " sollte sein: -1.0");
            System.out.println(p.evaluate(-0.5) + " sollte sein: -0.5");
            System.out.println("-------------------------------");
        }
        {
            double[] x = {0.5, 1, -1};
            double[] y = {0, 1, -1};
            NewtonPolynom p = new NewtonPolynom(x, y);

            System.out.println(p.evaluate(0) + " sollte sein: -0.666666667");
            System.out.println(p.evaluate(-1) + " sollte sein: -1.0");
            System.out.println(p.evaluate(0.5) + " sollte sein: 0.0");
            System.out.println("-------------------------------");
        }
        {
            double[] x = {0, 1, -1, 0.5};
            double[] y = {0, 1, -1, 0};
            NewtonPolynom p = new NewtonPolynom(x, y);

            System.out.println(p.evaluate(0) + " sollte sein: 0.0");
            System.out.println(p.evaluate(-0.5) + " sollte sein: 0.0");
            System.out.println(p.evaluate(0.5) + " sollte sein: 0.0");
            System.out.println(p.evaluate(2) + " sollte sein: 10.0");
            System.out.println(p.evaluate(-2) + " sollte sein: -10.0");
            System.out.println("-------------------------------");
        }
        {
            double[] x = {0};
            double[] y = {0};
            NewtonPolynom p = new NewtonPolynom(x, y);

            System.out.println(p.evaluate(0) + " sollte sein: 0.0");
            System.out.println(p.evaluate(-1) + " sollte sein: 0.0");
            System.out.println(p.evaluate(0.5) + " sollte sein: 0.0");
            System.out.println("-------------------------------");
        }
        {
            double[] x = {0,1};
            double[] y = {1,1};
            NewtonPolynom p = new NewtonPolynom(x, y);

            System.out.println(p.evaluate(0) + " sollte sein: 1.0");
            System.out.println(p.evaluate(-1) + " sollte sein: 1.0");
            System.out.println(p.evaluate(0.5) + " sollte sein: 1.0");
            System.out.println("-------------------------------");
        }
        {
            double[] x = {1};
            double[] y = {1};
            NewtonPolynom p = new NewtonPolynom(x, y);

            System.out.println(p.evaluate(0) + " sollte sein: 1.0");
            System.out.println(p.evaluate(-1) + " sollte sein: 1.0");
            System.out.println(p.evaluate(0.5) + " sollte sein: 1.0");
            System.out.println("-------------------------------");
        }
        System.out.println("Newton Test beendet: x und y sind unterschiedlich lang und x oder y is leer wurden nicht getestet!!!");
        System.out.println("-------------------------------");
    }

    public static void testSplines() {
        {
            CubicSpline spl = new CubicSpline();
            double[] y = {2, 0, 2, 3};

            spl.init(-1, 2, 3, y);
            spl.setBoundaryConditions(9, 0);
            System.out.println(Arrays.toString(spl.getDerivatives())
                    + " sollte sein: [9.0, -3.0, 3.0, 0.0].");
            System.out.println(spl.evaluate(0) + " sollte sein: 0.0");
            System.out.println(spl.evaluate(2) + " sollte sein: 3.0");
            System.out.println(spl.evaluate(0.5) + " sollte sein: 0.25");
            System.out.println(spl.evaluate(1.5) + " sollte sein: 2.875");
            System.out.println("-------------------------------");
        }
        {
            CubicSpline spl = new CubicSpline();
            double[] y = {2, 0};
            spl.init(-1, 0, 1, y);
            spl.setBoundaryConditions(8.5, 0);
            System.out.println(Arrays.toString(spl.getDerivatives())
                    + " sollte sein: [8.5, 0.0].");
            System.out.println(spl.evaluate(0) + " sollte sein: 0.0");
            System.out.println(spl.evaluate(-0.5) + " sollte sein: 2.0625");
            System.out.println(spl.evaluate(-0.8) + " sollte sein: 2.88");
            System.out.println("-------------------------------");
        }
        {
            CubicSpline spl = new CubicSpline();
            double[] y = {2, 1, 0.5, 0, 2, 3 ,4 , 0, -2};
            spl.init(1, 3, 8, y);
            spl.setBoundaryConditions(0, -12);
            System.out.println(Arrays.toString(spl.getDerivatives())
                    + " sollte sein: \n[0.0, -3.75552282, -2.97790868, 3.66715758, 6.30927835, 7.09572901, -10.69219440, -0.32695139, -12.0].");
            // to be calculated jet
            //System.out.println(spl.evaluate(0) + " sollte sein: 0.0");
            //System.out.println(spl.evaluate(-0.5) + " sollte sein: 2.0625");
            //System.out.println(spl.evaluate(-0.8) + " sollte sein: 2.88");
            System.out.println("-------------------------------");
        }
        {
            CubicSpline spl = new CubicSpline();
            double[] y = {-3.125, 12.33334};
            spl.init(1, 2, 1, y);
            spl.setBoundaryConditions(0, 0);
            System.out.println(Arrays.toString(spl.getDerivatives())
                    + " sollte sein: [0.0, 0.0].");
        }
        {
            CubicSpline spl = new CubicSpline();
            double[] y = {-3.125, 12.33334};
            spl.init(1, 2, 1, y);
            System.out.println(Arrays.toString(spl.getDerivatives())
                    + " sollte sein: [0.0, 0.0].");
        }
    }

    public static void testFFT() {
        System.out.println("Teste Fast Fourier Transformation");

        double[] v = new double[4];
        for (int i = 0; i < 4; i++)
            v[i] = i + 1;
        Complex[] c = dft.DFT.dft(v);
        Complex[] v2 = dft.IFFT.ifft(c);

        for (int i = 0; i < 4; i++) {
            System.out.println(v2[i]);
        }
        System.out
                .println("Richtig waeren gerundet: Eigene Beispiele ueberlegen");

        System.out.println("*************************************\n");
    }
}
