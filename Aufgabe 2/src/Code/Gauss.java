package Code;

import java.util.Arrays;
import java.util.stream.Stream;

public class Gauss {

	/**
	 * Diese Methode soll die Loesung x des LGS R*x=b durch
	 * Rueckwaertssubstitution ermitteln.
	 * PARAMETER: 
	 * R: Eine obere Dreiecksmatrix der Groesse n x n 
	 * b: Ein Vektor der Laenge n
	 */
	public static double[] backSubst(double[][] R, double[] b) {
		double[] x = new double[b.length];
		for (int i = R.length - 1; i >= 0; --i) {
			double y = b[i];
			for (int j = R.length - 1; j > i; --j) {
				y -= R[i][j]*x[j];
			}
			x[i] = y / R[i][i];
		}
		return x;
	}

	/**
	 * Diese Methode soll die Loesung x des LGS A*x=b durch Code.Gauss-Elimination mit
	 * Spaltenpivotisierung ermitteln. A und b sollen dabei nicht veraendert werden. 
	 * PARAMETER: A:
	 * Eine regulaere Matrix der Groesse n x n 
	 * b: Ein Vektor der Laenge n
	 */
	public static double[] solve(double[][] Aorig, double[] borig) {
		double[][] A = Aorig.clone();
		double[] b = borig.clone();

		for (int i = 0; i < A.length; i++) {

			double jBiggest = 0;
			int jIndex = 0;
			for (int j = i+1; j < A[i].length; j++) {
				if (jBiggest < Math.abs(A[i][j])){
					jIndex = j;
					jBiggest = A[i][j];
				}
			}

			double[] q = A[i];
			A[i] = A[jIndex];
			A[jIndex] = q;

			double qq = b[i];
			b[i] = b[jIndex];
			b[jIndex] = qq;


			for (int j = i+1; j < A[i].length; j++) {
				if (A[j][i] != 0){
					double[] row = A[i];
					double brow = b[i];
					for (int i1 = 0; i1 < row.length; i1++) {
						row[i1] = row[i1]/(A[j][i]/row[i]);
					}
					brow = brow/(A[j][i]/row[i]);
					for (int i1 = 0; i1 < A[j].length; i1++) {
						A[j][i1] -= row[i1];
					}
					b[i] -= brow;
				}
			}

		}
		return backSubst(A, b);
	}


	/**
	 * Diese Methode soll eine Loesung p!=0 des LGS A*p=0 ermitteln. A ist dabei
	 * eine nicht invertierbare Matrix. A soll dabei nicht veraendert werden.
	 * 
	 * Gehen Sie dazu folgendermassen vor (vgl.Aufgabenblatt): 
	 * -Fuehren Sie zunaechst den Code.Gauss-Algorithmus mit Spaltenpivotisierung
	 *  solange durch, bis in einem Schritt alle moeglichen Pivotelemente
	 *  numerisch gleich 0 sind (d.h. <1E-10) 
	 * -Betrachten Sie die bis jetzt entstandene obere Dreiecksmatrix T und
	 *  loesen Sie Tx = -v durch Rueckwaertssubstitution 
	 * -Geben Sie den Vektor (x,1,0,...,0) zurueck
	 * 
	 * Sollte A doch intvertierbar sein, kann immer ein Pivot-Element gefunden werden(>=1E-10).
	 * In diesem Fall soll der 0-Vektor zurueckgegeben werden. 
	 * PARAMETER: 
	 * A: Eine singulaere Matrix der Groesse n x n 
	 */
	public static double[] solveSing(double[][] A) {
		//TODO: Diese Methode ist zu implementieren
		return new double[2];
	}

	/**
	 * Diese Methode berechnet das Matrix-Vektor-Produkt A*x mit A einer nxm
	 * Matrix und x einem Vektor der Laenge m. Sie eignet sich zum Testen der
	 * Code.Gauss-Loesung
	 */
	public static double[] matrixVectorMult(double[][] A, double[] x) {
		int n = A.length;
		int m = x.length;

		double[] y = new double[n];

		for (int i = 0; i < n; i++) {
			y[i] = 0;
			for (int j = 0; j < m; j++) {
				y[i] += A[i][j] * x[j];
			}
		}

		return y;
	}
}
