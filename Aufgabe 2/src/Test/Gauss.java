package Test;

import Code.Util;

/**
 * Created by bene on 6/3/16.
 */

public abstract class Gauss {
    public abstract boolean test();

}

class Backsub extends Gauss{
    double[] b;
    double[][] C;
    double[] xC;

    public Backsub(double[] b, double[][] c, double[] xC) {
        this.b = b;
        C = c;
        this.xC = xC;
    }

    public boolean test(){
        double[] x = Code.Gauss.backSubst(C, b);
        boolean out = Util.vectorCompare(x, xC);
        if (out) {
            System.out.println("    Richtiges Ergebnis");
        } else {
            System.out.println("    FEHLER: falsches Ergebnis:");
            Util.printVector(x);
            System.out.println("            richtiges Ergebnis:");
            Util.printVector(xC);
        }
        return out;
    }
}

class Solve extends Gauss{
    double[] b;
    double[][] C;
    double[] xC;

    public Solve(double[] b, double[][] c, double[] xC) {
        this.b = b;
        C = c;
        this.xC = xC;
    }

    public boolean test(){
        double[] x = Code.Gauss.solve(C, b);
        boolean out = Util.vectorCompare(x, xC);
        if (out) {
            System.out.println("    Richtiges Ergebnis");
        } else {
            System.out.println("    FEHLER: falsches Ergebnis:");
            Util.printVector(x);
            System.out.println("            richtiges Ergebnis:");
            Util.printVector(xC);
        }
        return out;
    }
}

class SolveSing extends Gauss{
    double[] b;
    double[][] A;
    double[] xA;

    public SolveSing(double[] b, double[][] c, double[] xA) {
        this.b = b;
        A = c;
        this.xA = xA;
    }

    public boolean test(){
        double[] x = Code.Gauss.solveSing(A);
        double lambda = xA[0] / x[0];
        for (int i = 0; i < x.length; i++) {
            x[i] *= lambda;
        }
        boolean out = Util.vectorCompare(x, xA);
        if (out) {
            System.out.println("    Richtiges Ergebnis");
        } else {
            System.out.println("    FEHLER: falsches Ergebnis:");
            Util.printVector(x);
            System.out.println("            richtiges Ergebnis:");
            Util.printVector(xA);
        }
        return out;
    }
}