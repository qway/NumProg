package Code;

import java.util.Arrays;


public class Gauss {

	/**
	 * Diese Methode soll die Loesung x des LGS R*x=b durch
	 * Rueckwaertssubstitution ermitteln.
	 * PARAMETER:
	 * R: Eine obere Dreiecksmatrix der Groesse n x n
	 * b: Ein Vektor der Laenge n
	 */
	public static double[] backSubst(double[][] R, double[] b) {
		int n = R[0].length;
		double x[] = new double[n];

		//solve last row: a_nn * x_n = b_n -> x_n = b_n / a_nn
		//x[n - 1] = b[n - 1] / R[n - 1][n - 1]; (was integrated into for loop: first poly sum = 0)
		//solve secondlast row: a_n-1 n-1 * x_n-1 + a_n-1 n * x_n = b_n-1 ->
		// x_n-1 = (b_n-1 - (a_n-1 n * x_n)) / a_n-1 n-1
		for (int i = n - 1; i >= 0; i--) {
			double poly = 0.0;
			for (int j = i + 1; j < R[i].length; j++) {
				poly += R[i][j] * x[j];
			}
			x[i] = (b[i] - poly) / R[i][i];
		}

		return x;
	}

	/**
	 * Diese Methode soll die Loesung x des LGS A*x=b durch Gauss-Elimination mit
	 * Spaltenpivotisierung ermitteln. A und b sollen dabei nicht veraendert werden.
	 * PARAMETER: A:
	 * Eine regulaere Matrix der Groesse n x n
	 * b: Ein Vektor der Laenge n
	 */
	public static double[] solve(double[][] A, double[] b) {
		int n = A.length;
		double[][] Ar = new double[n][n];
		double[] br = new double[n];

		//A und b werden nicht verändert: arbeit auf Ar und br
		for (int i = 0; i < n; i++) {
			Ar[i] = A[i].clone();
		}
		br = b.clone();

		// Im k-ten Schritt der Gauß-Elimination mussen alle Elemente der k-ten Spalte
		// unterhalb der Hauptdiagonalen zu Null werden.
		for (int k = 0; k < n - 1; k++) {
			//Zeile j >= k, die das betragsgrößte Element a_jk, j>=k enthält
			int j_piv = k;
			double max = Math.abs(Ar[k][k]);

			for (int j = k + 1; j < n; j++) {
				if (max < Math.abs(Ar[j][k])) {
					max = Math.abs(Ar[j][k]);
					j_piv = j;
				}
			}

			//es werden die zeilen k und j_piv vertauscht (Falls k!=j)
			if (k != j_piv) {
				double temp;
				//swap der Zeile in Matrix Ar
				for (int i = k; i < Ar[k].length; i++) {
					temp = Ar[k][i];
					Ar[k][i] = Ar[j_piv][i];
					Ar[j_piv][i] = temp;
				}
				//swap der Elemente in Vektor br
				temp = br[k];
				br[k] = br[j_piv];
				br[j_piv] = temp;
			}

			//Eliminationsschritt
			//von jeder Zeile i>k die mit a_ik/a_kk multiplizierte k-te Zeile abziehen.
			//ACHTUNG: a_kk != 0 (wenn a_kk = 0 müssen alle darunterliegenden a_ki auch 0 sein, da sie sonst nach oben getauscht worden wären)
			//In diesem Fall weiter mit nächstem k
			if (Ar[k][k] == 0)
				continue;
			for (int i = k + 1; i < Ar[k].length; i++) {
				double a = Ar[i][k] / Ar[k][k];

				//von b_i = b_i - a * b_k
				br[i] -= a * b[k];

				for (int j = k; j < Ar[k].length; j++) {
					Ar[i][j] -= a * Ar[k][j];
				}
			}
		}

		return backSubst(Ar, br);
	}

	/**
	 * Diese Methode soll eine Loesung p!=0 des LGS A*p=0 ermitteln. A ist dabei
	 * eine nicht invertierbare Matrix. A soll dabei nicht veraendert werden.
	 * <p>
	 * Gehen Sie dazu folgendermassen vor (vgl.Aufgabenblatt):
	 * -Fuehren Sie zunaechst den Gauss-Algorithmus mit Spaltenpivotisierung
	 * solange durch, bis in einem Schritt alle moeglichen Pivotelemente
	 * numerisch gleich 0 sind (d.h. <1E-10)
	 * -Betrachten Sie die bis jetzt entstandene obere Dreiecksmatrix T und
	 * loesen Sie Tx = -v durch Rueckwaertssubstitution
	 * -Geben Sie den Vektor (x,1,0,...,0) zurueck
	 * <p>
	 * Sollte A doch intvertierbar sein, kann immer ein Pivot-Element gefunden werden(>=1E-10).
	 * In diesem Fall soll der 0-Vektor zurueckgegeben werden.
	 * PARAMETER:
	 * A: Eine singulaere Matrix der Groesse n x n
	 */
	public static double[] solveSing(double[][] A) {
		//TODO: Remove Ugly redundant Code in this File :(
		int n = A.length;
		double[][] Ar = new double[n][n];
		double[] ret = new double[n];

		//A wird nicht verändert: arbeit auf Ar
		for (int i = 0; i < n; i++) {
			System.arraycopy(A[i], 0, Ar[i], 0, n);
		}

		// Util.printMatrix(Ar);
		// Im k-ten Schritt der Gauß-Elimination mussen alle Elemente der k-ten Spalte
		// unterhalb der Hauptdiagonalen zu Null werden.
		for (int k = 0; k < n-1; k++) {
			// Zeile j >= k, die das betragsgrößte Element a_jk, j>=k enthält
			int j_piv = k;
			double max = Math.abs(Ar[k][k]);

			for (int j = k + 1; j < n; j++) {
				if (max < Math.abs(Ar[j][k])) {
					max = Math.abs(Ar[j][k]);
					j_piv = j;
				}
			}

			// if abfrage true falls nur noch nullen in der spalte. d.h. max < 1E-10
			if (max < 1E-10) {
				// A ist die Nullmatrix
				if (k == 0) {
					Arrays.fill(ret, 0);
					return ret;
				}
				// Tx = -v
				double[][] T = new double[k][k];
				// copy 0...k-1 quadratische Matrix in T
				for (int i = 0; i < k; i++) {
					for (int j = 0; j < k; j++) {
						T[i][j] = Ar[i][j];
					}
				}

				// -v
				double [] v = new double[k];
				for (int i = 0; i < k; i++) {
					v[i] = -1.0 * Ar [i][k];
				}

				//x = solve Tx = -v
				double [] x = backSubst(T, v);

				//p := (x,1,0...0)
				System.arraycopy(x, 0, ret, 0, k);
				if(x.length > k)
					ret[k] = 1;
				for (int i = k + 1; i < n; i++) {
					ret[k] = 0;
				}
				return ret;
			}



			//wie vorher vertauschung von max element nach oben
			//COPIED CODE FROM: public static double[] solve(double[][] A, double[] b)
			//es werden die zeilen k und j_piv vertauscht (Falls k!=j)
			if (k != j_piv) {
				double temp;
				//swap der Zeile in Matrix Ar
				for (int i = k; i < n; i++) {
					temp = Ar[k][i];
					Ar[k][i] = Ar[j_piv][i];
					Ar[j_piv][i] = temp;
				}
			}

			//Eliminationsschritt
			//COPIED CODE FROM: public static double[] solve(double[][] A, double[] b)
			//von jeder Zeile i>k die mit a_ik/a_kk multiplizierte k-te Zeile abziehen.
			for (int i = k + 1; i < n; i++) {
				double a = Ar[i][k] / Ar[k][k];
				for (int j = k; j < n; j++) {
					Ar[i][j] -= a * Ar[k][j];
				}
			}

		}

		//TODO: falls nullzeile in letzter zeile und wir nicht in max < 1E-10 gelaufen sind ?
		//TODO: invertierbar? null vektor zurückgeben ?
		//TODO: dieser Randfall ist: A ist eine obere Dreiecksmatrix ?

		int k = n-1;
		double max = Math.abs(Ar[k][k]);

		//COPIED FROM ABOVE
		if (max < 1E-10) {
			// A ist die Nullmatrix
			if (k == 0) {
				Arrays.fill(ret, 0);
				return ret;
			}
			// Tx = -v
			double[][] T = new double[k][k];
			// copy 0...k-1 quadratische Matrix in T
			for (int i = 0; i < k; i++) {
				for (int j = 0; j < k; j++) {
					T[i][j] = Ar[i][j];
				}
			}

			// -v
			double [] v = new double[k];
			for (int i = 0; i < k; i++) {
				v[i] = -1.0 * Ar [i][k];
			}

			//x = solve Tx = -v
			double [] x = backSubst(T, v);

			//p := (x,1,0...0)
			System.arraycopy(x, 0, ret, 0, k);
			if(x.length > k)
				ret[k] = 1;
			for (int i = k + 1; i < n; i++) {
				ret[k] = 0;
			}
			return ret;
		}

		//nullvektor als rückgabe
		Arrays.fill(ret, 0);
		return ret;
	}

	/**
	 * Diese Methode berechnet das Matrix-Vektor-Produkt A*x mit A einer nxm
	 * Matrix und x einem Vektor der Laenge m. Sie eignet sich zum Testen der
	 * Gauss-Loesung
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