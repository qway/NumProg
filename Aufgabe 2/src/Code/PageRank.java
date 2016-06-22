package Code;

import java.util.Arrays;
import java.util.Comparator;

public class PageRank {

	/**
	 * Diese Methode erstellt die Matrix A~ fuer das Code.PageRank-Verfahren
	 * PARAMETER:
	 * L: die Linkmatrix (s. Aufgabenblatt)
	 * rho: Wahrscheinlichkeit, anstatt einem Link zu folgen,
	 * zufaellig irgendeine Seite zu besuchen
	 */
	public static double[][] buildProbabilityMatrix(int[][] L, double rho) {
		int n = L.length;
		double a[][] = new double[n][n];

		int[] link_anz = new int[n];

		// berechnung aller #Links die von j ausgehen und speichern in Vektor link_anz
		for (int j = 0; j < n; j++) {
			link_anz[j] = 0;
			for (int i = 0; i < n; i++) {
				link_anz[j] += L[i][j];
			}
		}

		// Matrix a_ij mit 1/#Links die von j ausgehen belegen
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				// Division by Zero ? How to handle ? If evry row elemnt is 0,
				// its no longer a stocastical Matrix with rowsum 1?
				a[i][j] = 1.0 / link_anz[j];
			}
		}

		// Optimize calculation. Transfer it in previos 2xfor loop
		// Matrix a_quer_ij in Matrix a schreiben (inplace)
		// mit (1-roh)*a_ij+p/n
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				a[i][j] = (1.0-rho)*a[i][j]+(rho/(double)n);
			}
		}

		return a;
	}

	/**
	 * Diese Methode berechnet die PageRanks der einzelnen Seiten,
	 * also das Gleichgewicht der Aufenthaltswahrscheinlichkeiten.
	 * (Entspricht dem p-Strich aus der Angabe)
	 * Die Ausgabe muss dazu noch normiert sein.
	 * PARAMETER:
	 * L: die Linkmatrix (s. Aufgabenblatt)
	 * rho: Wahrscheinlichkeit, zufaellig irgendeine Seite zu besuchen
	 * ,anstatt einem Link zu folgen.
	 */
	public static double[] rank(int[][] L, double rho) {
		int n = L.length;
		double a[][] = new double[n][n];
		double p [] = new double[n];

		a = buildProbabilityMatrix(L, rho);

		// Berechnung des Vektors p durch (A~-I)p=0
		// 1. Brechnung A-I
		for (int i = 0; i < n; i++) {
			a[i][i] -= 1.0;
		}
		// 2. Brechnung (A~-I)p=0 und speichern in Variable p
		p = Gauss.solveSing(a);

		// Berechnen von p_quer
		// mit p_quer = p*lambda speichern in variable p_quer
		// 1. Berechnung von lambda
		double lambda = 0.0;
		for (int i = 0; i < n; i++) {
			lambda += p[i];
		}
		lambda = 1.0/lambda;
		// 2.Brechnung von p_quer gespeichert in p
		for (int i = 0; i < n; i++) {
			p[i] *= lambda;
		}
		return p;
	}

	/**
	 * Diese Methode erstellt eine Rangliste der uebergebenen URLs nach
	 * absteigendem Code.PageRank.
	 * PARAMETER:
	 * urls: Die URLs der betrachteten Seiten
	 * L: die Linkmatrix (s. Aufgabenblatt)
	 * rho: Wahrscheinlichkeit, anstatt einem Link zu folgen,
	 * zufaellig irgendeine Seite zu besuchen
	 */
	public static String[] getSortedURLs(String[] urls, int[][] L, double rho) {
		int n = L.length;

		double[] p = rank(L, rho);

		RankPair[] sortedPairs = new RankPair[n];
		for (int i = 0; i < n; i++) {
			sortedPairs[i] = new RankPair(urls[i], p[i]);
		}

		Arrays.sort(sortedPairs, new Comparator<RankPair>() {

			@Override
			public int compare(RankPair o1, RankPair o2) {
				return -Double.compare(o1.pr, o2.pr);
			}
		});

		String[] sortedUrls = new String[n];
		for (int i = 0; i < n; i++) {
			sortedUrls[i] = sortedPairs[i].url;
		}

		return sortedUrls;
	}

	/**
	 * Ein RankPair besteht aus einer URL und dem zugehoerigen Rang, und dient
	 * als Hilfsklasse zum Sortieren der Urls
	 */
	private static class RankPair {
		public String url;
		public double pr;

		public RankPair(String u, double p) {
			url = u;
			pr = p;
		}
	}
}