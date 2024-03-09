package Logic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Model.Individuo;

public class Cruce {

	private double p;

	private int tam_elite;
	private int aviones;

	// private boolean bin;

	public Cruce(double p, int funcion_idx, int tam_elite) {
		this.p = p;
		this.tam_elite = tam_elite;
		this.aviones = 12;
	}
	
	// TODO PROBLEMA EN AEROPUERTO 2, SE QUEDA DANDO VUELTAS EN ALGUNA PARTE
	public Individuo[] PMX(Individuo[] selec) {
		int n = selec.length; // aviones = selec[0].gen.v.length;
		Individuo[] ret = new Individuo[n + tam_elite];

		if (n % 2 == 1) {
			ret[n - 1] = selec[n - 1];
			n--; // descarta al ultimo si es impar
		}

		int i = 0, k;
		Individuo ind1, ind2;
		int corte1, corte2;
		while (i < n) {
			ind1 = new Individuo(selec[i]);
			ind2 = new Individuo(selec[i + 1]);

			//System.out.print("(ANTES) Ind1: "); ind1.printIndividuo();
			//System.out.print("(ANTES) Ind2: "); ind2.printIndividuo();
			if (Math.random() < p) {

				Map<Integer, Integer> pareja1 = new HashMap<>();
				Map<Integer, Integer> pareja2 = new HashMap<>();
				
				corte1 = (int) (Math.random() * (aviones - 2));
				corte2 = corte1 + (int) (Math.random() * (aviones - corte1));
				//System.out.println("Corte en " + corte1 + " "+ corte2);

				for (int j = corte1; j < corte2; j++) {
					int temp = ind1.gen.v[j];
					ind1.gen.v[j] = ind2.gen.v[j];
					ind2.gen.v[j] = temp;

					pareja1.put(ind1.gen.v[j], ind2.gen.v[j]);
					pareja2.put(ind2.gen.v[j], ind1.gen.v[j]);
				}

				for (int j = 0; j < aviones - (corte2 - corte1); j++) {
					if (pareja1.containsKey(ind1.gen.v[(corte2 + j) % aviones])) {
						int p = pareja1.get(ind1.gen.v[(corte2 + j) % aviones]);
						while (pareja1.containsKey(p))
							p = pareja1.get(p);

						ind1.gen.v[(corte2 + j) % aviones] = p;
					} 
					if (pareja2.containsKey(ind2.gen.v[(corte2 + j) % aviones])) {
						int p = pareja2.get(ind2.gen.v[(corte2 + j) % aviones]);
						while (pareja2.containsKey(p))
							p = pareja2.get(p);

						ind2.gen.v[(corte2 + j) % aviones] = p;
					}
				}
			}
			ret[i++] = ind1;
			ret[i++] = ind2;
			//System.out.print("(DESPUES) Ind1: "); ind1.printIndividuo();
			//System.out.print("(DESPUES) Ind2: "); ind2.printIndividuo();
			//System.out.println();
		}
		return ret;
	}

	public Individuo[] OX(Individuo[] selec) {
		int n = selec.length;
		Individuo[] ret = new Individuo[n + tam_elite];

		if (n % 2 == 1) {
			ret[n - 1] = selec[n - 1];
			n--; // descarta al ultimo si es impar
		}

		int i = 0, k;
		Individuo ind1, ind2;
		int corte1, corte2;
		while (i < n) {
			ind1 = new Individuo(selec[i]);
			ind2 = new Individuo(selec[i + 1]);

			// System.out.print("(ANTES) Ind1: "); ind1.printIndividuo();
			// System.out.print("(ANTES) Ind2: "); ind2.printIndividuo();
			if (Math.random() < p) {
				corte1 = (int) (Math.random() * (aviones - 2));
				corte2 = corte1 + (int) (Math.random() * (aviones - corte1));

				Set<Integer> set1 = new HashSet<>();
				Set<Integer> set2 = new HashSet<>();

				for (int j = corte1; j < corte2; j++) {
					set1.add(ind2.gen.v[j]);
					set2.add(ind1.gen.v[j]);
				}

				k = corte2;
				for (int j = 0; j < aviones; j++) {
					if (!set1.contains(ind1.gen.v[(corte2 + j) % aviones])) {
						ind1.gen.v[k++ % aviones] = ind1.gen.v[(corte2 + j) % aviones];
					}
				}

				k = corte2;
				for (int j = 0; j < aviones; j++) {
					if (!set2.contains(ind2.gen.v[(corte2 + j) % aviones])) {
						ind2.gen.v[k++ % aviones] = ind2.gen.v[(corte2 + j) % aviones];
					}
				}

				for (int j = corte1; j < corte2; j++) {
					int temp = ind1.gen.v[j];
					ind1.gen.v[j] = ind2.gen.v[j];
					ind2.gen.v[j] = temp;
				}

			}
			ret[i++] = ind1;
			ret[i++] = ind2;
			// System.out.print("(DESPUES) Ind1: "); ind1.printIndividuo();
			// System.out.print("(DESPUES) Ind2: "); ind2.printIndividuo();
			// System.out.println();
		}
		return ret;
	}

	public Individuo[] OX_PP(Individuo[] selec, int pp) {
		int n = selec.length;
		Individuo[] ret = new Individuo[n + tam_elite];

		if (n % 2 == 1) {
			ret[n - 1] = selec[n - 1];
			n--; // descarta al ultimo si es impar
		}

		int i = 0, k;
		Individuo ind1, ind2;
		int corte1, corte2;
		while (i < n) {
			ind1 = new Individuo(selec[i]);
			ind2 = new Individuo(selec[i + 1]);

			// System.out.print("(ANTES) Ind1: "); ind1.printIndividuo();
			// System.out.print("(ANTES) Ind2: "); ind2.printIndividuo();
			if (Math.random() < p) {
				Set<Integer> puntos = new HashSet<>();
				int puntosList[] = new int[n];
				Set<Integer> set1 = new HashSet<>();
				Set<Integer> set2 = new HashSet<>();

				int punto;
				for (int j = 0; j < pp; j++) {
					punto = (int) (Math.random() * (aviones - 1));
					while (!puntos.contains(punto)) {
						punto = (int) (Math.random() * (aviones - 1));
					}

					puntos.add(punto);
					puntosList[j] = punto;

					set1.add(ind2.gen.v[punto]);
					set2.add(ind1.gen.v[punto]);
				}

				int ji;

				k = 0;
				ji = 0;
				while (k < aviones) {
					if (puntos.contains(k)) {
						k++;
					} else if (!set1.contains(ind1.gen.v[ji % aviones])) {
						ind1.gen.v[k++] = ind1.gen.v[ji];
					}
					ji++;

				}

				k = 0;
				ji = 0;
				while (k < aviones) {
					if (puntos.contains(k)) {
						k++;
					}
					if (!set2.contains(ind2.gen.v[ji])) {
						ind2.gen.v[k++] = ind2.gen.v[ji];
					}
					ji++;
				}

				for (int j = 0; j < pp; j++) {
					int temp = ind1.gen.v[puntosList[j]];
					ind1.gen.v[puntosList[j]] = ind2.gen.v[puntosList[j]];
					ind2.gen.v[puntosList[j]] = temp;
				}
			}
			ret[i++] = ind1;
			ret[i++] = ind2;
			// System.out.print("(DESPUES) Ind1: "); ind1.printIndividuo();
			// System.out.print("(DESPUES) Ind2: "); ind2.printIndividuo();
			// System.out.println();
		}
		return ret;
	}

	public Individuo[] CX(Individuo[] selec) {
		int n = selec.length;
		Individuo[] ret = new Individuo[n + tam_elite];

		if (n % 2 == 1) {
			ret[n - 1] = selec[n - 1];
			n--; // descarta al ultimo si es impar
		}

		int i = 0, k;
		Individuo ind1, ind2;
		int corte1, corte2;
		while (i < n) {
			ind1 = new Individuo(selec[i]);
			ind2 = new Individuo(selec[i + 1]);

			// System.out.print("(ANTES) Ind1: "); ind1.printIndividuo();
			// System.out.print("(ANTES) Ind2: "); ind2.printIndividuo();
			if (Math.random() < p) {
				Map<Integer, Integer> pareja1 = new HashMap<>();
				Map<Integer, Integer> pareja2 = new HashMap<>();

				for (int j = 0; j < n; j++) {
					pareja1.put(ind1.gen.v[j], ind2.gen.v[j]);
					pareja2.put(ind2.gen.v[j], ind1.gen.v[j]);
				}

				Set<Integer> set1 = new HashSet<>();
				Set<Integer> set2 = new HashSet<>();

				int c1 = ind1.gen.v[0];
				set1.add(c1);
				while (pareja1.containsKey(c1)) {
					c1 = pareja1.get(c1);
					set1.add(c1);
				}
				int c2 = ind2.gen.v[0];
				set2.add(c2);
				while (pareja2.containsKey(c2)) {
					c1 = pareja1.get(c1);
					set1.add(c1);
				}

				for (int j = 0; j < n; j++) {
					if (!set1.contains(ind1.gen.v[j]))
						swap(ind1.gen.v[j], ind2.gen.v[j]);
				}

			}
			ret[i++] = ind1;
			ret[i++] = ind2;
			// System.out.print("(DESPUES) Ind1: "); ind1.printIndividuo();
			// System.out.print("(DESPUES) Ind2: "); ind2.printIndividuo();
			// System.out.println();
		}
		return ret;
	}

	public Individuo[] CO(Individuo[] selec) {
		int n = selec.length;
		Individuo[] ret = new Individuo[n + tam_elite];

		if (n % 2 == 1) {
			ret[n - 1] = selec[n - 1];
			n--; // descarta al ultimo si es impar
		}

		int i = 0, k, de;
		Individuo ind1, ind2;
		int corte1, corte2;
		while (i < n) {
			ind1 = new Individuo(selec[i]);
			ind2 = new Individuo(selec[i + 1]);

			// System.out.print("(ANTES) Ind1: "); ind1.printIndividuo();
			// System.out.print("(ANTES) Ind2: "); ind2.printIndividuo();
			if (Math.random() < p) {
				int[] listaDinamica = new int[n];
				for (int j = 0; j < n; j++) {
					listaDinamica[j] = j;
				}

				for (int j = 0; j < n; j++) {
					k = 0;
					de = 0;
					while (de < ind1.gen.v[j]) {
						if (listaDinamica[k] != 0)
							de++;
						k++;
					}
					ind1.gen.v[j] = listaDinamica[k];
					listaDinamica[k] = 0;
				}

				for (int j = 0; j < n; j++) {
					listaDinamica[j] = j;
				}

				for (int j = 0; j < n; j++) {
					k = 0;
					de = 0;
					while (de < ind2.gen.v[j]) {
						if (listaDinamica[k] != 0)
							de++;
						k++;
					}
					ind2.gen.v[j] = listaDinamica[k];
					listaDinamica[k] = 0;
				}

				corte1 = (int) (Math.random() * (ind1.gen.v.length - 1));

				for (int j = 0; j < n; j++) {
					swap(ind1.gen.v[j], ind2.gen.v[j]);
				}

				for (int j = 0; j < n; j++) {
					listaDinamica[j] = j;
				}

				for (int j = 0; j < n; j++) {
					k = 0;
					de = 0;
					while (de < ind1.gen.v[j]) {
						if (listaDinamica[k] != 0)
							de++;
						k++;
					}
					ind1.gen.v[j] = listaDinamica[k];
					listaDinamica[k] = 0;
				}

				for (int j = 0; j < n; j++) {
					listaDinamica[j] = j;
				}

				for (int j = 0; j < n; j++) {
					k = 0;
					de = 0;
					while (de < ind2.gen.v[j]) {
						if (listaDinamica[k] != 0)
							de++;
						k++;
					}
					ind2.gen.v[j] = listaDinamica[k];
					listaDinamica[k] = 0;
				}
			}
			ret[i++] = ind1;
			ret[i++] = ind2;
			// System.out.print("(DESPUES) Ind1: "); ind1.printIndividuo();
			// System.out.print("(DESPUES) Ind2: "); ind2.printIndividuo();
			// System.out.println();
		}
		return ret;
	}

	static void swap(int x, int y) {

	}
	
	public Individuo[] custom(Individuo[] selec) {
		int n = selec.length;
		Individuo[] ret= new Individuo[n];
		// TODO
		
		
		
		return ret;
		
	}

}
