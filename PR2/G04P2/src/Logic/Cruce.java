package Logic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import Model.Individuo;

public class Cruce {

	private double p;

	private int tam_elite;
	private int aviones;

	// private boolean bin;

	public Cruce(double p, int num_vuelos, int tam_elite) {
		this.p = p;
		this.tam_elite = tam_elite;
		this.aviones = num_vuelos;
	}

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
			ind2 = new Individuo(selec[i+1]);

			
			if (Math.random() < p) {
				Map<Integer, Integer> pareja1 = new HashMap<>();
				Map<Integer, Integer> pareja2 = new HashMap<>();

				Random rand = new Random();
				
				//corte1=rand.nextInt(aviones)+3;
				//corte2=rand.nextInt(aviones-corte1)+corte1+1;
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
				int x=2;
				for (int j = 0; j < aviones - (corte2 - corte1); j++) {
					if (pareja1.containsKey(ind1.gen.v[(corte2 + j) % aviones])) {
						int p = pareja1.get(ind1.gen.v[(corte2 + j) % aviones]);
						while (pareja1.containsKey(p)) {
							p = pareja1.get(p);
							/*System.out.println("Cortes: " + corte1 + " " + corte2);
							System.out.print("Ind1: "); ind1.printIndividuo();
							System.out.print("Ind2: "); ind2.printIndividuo();
							System.out.println("1: "+p);*/
						}
						ind1.gen.v[(corte2 + j) % aviones] = p;
					} 
					if (pareja2.containsKey(ind2.gen.v[(corte2 + j) % aviones])) {
						int p = pareja2.get(ind2.gen.v[(corte2 + j) % aviones]);
						while (pareja2.containsKey(p)) {
							p = pareja2.get(p);
							/*System.out.println("Cortes: " + corte1 + " " + corte2);
							System.out.print("Ind1: "); ind1.printIndividuo();
							System.out.print("Ind2: "); ind2.printIndividuo();
							System.out.println("2: "+p);*/
						}
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

		int i = 0, k, ppi;
		Individuo ind1, ind2;
		int validos[] = new int[aviones-pp];

		while (i < n) {
			ind1 = new Individuo(selec[i]);
			ind2 = new Individuo(selec[i + 1]);

			
			if (Math.random() < p) {
				// System.out.print("(ANTES) Ind1: "); ind1.printIndividuo();
				// System.out.print("(ANTES) Ind2: "); ind2.printIndividuo();

				Set<Integer> puntos = new HashSet<>();
				int puntosList[] = new int[n];
				Set<Integer> set1 = new HashSet<>();
				Set<Integer> set2 = new HashSet<>();

				int punto;
				for (int j = 0; j < pp; j++) {
					punto = (int) (Math.random() * (aviones - 1));
					while (puntos.contains(punto)) {
						punto = (int) (Math.random() * (aviones - 1));
					}

					puntos.add(punto);
					puntosList[j] = punto;

					set1.add(ind2.gen.v[punto]);
					set2.add(ind1.gen.v[punto]);
				}

				k = 0;
				for (int j = 0; j < aviones; j++) {
					if (!set1.contains(ind1.gen.v[j])) {
						validos[k++] = ind1.gen.v[j];
					}
				}

				ppi = 0;
				k = 0;
				for (int j = 0; j < aviones; j++) {
					if (!puntos.contains(j)) {
						ind1.gen.v[j] = validos[k++];
					}
				}	
				
				k = 0;
				for (int j = 0; j < aviones; j++) {
					if (!set2.contains(ind2.gen.v[j])) {
						validos[k++] = ind2.gen.v[j];
					}
				}

				ppi = 0;
				k = 0;
				for (int j = 0; j < aviones; j++) {
					if (!puntos.contains(j)) {
						ind2.gen.v[j] = validos[k++];
					}
				}

				for (int j = 0; j < pp; j++) {
					int temp = ind1.gen.v[puntosList[j]];
					ind1.gen.v[puntosList[j]] = ind2.gen.v[puntosList[j]];
					ind2.gen.v[puntosList[j]] = temp;
				}
				// System.out.print("(DESPUES) Ind1: "); ind1.printIndividuo();
				// System.out.print("(DESPUES) Ind2: "); ind2.printIndividuo();
				// System.out.println();
			}
			ret[i++] = ind1;
			ret[i++] = ind2;
			
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

		int i = 0;
		Individuo ind1, ind2;
		while (i < n) {
			ind1 = new Individuo(selec[i]);
			ind2 = new Individuo(selec[i + 1]);

			// System.out.print("(ANTES) Ind1: "); ind1.printIndividuo();
			// System.out.print("(ANTES) Ind2: "); ind2.printIndividuo();
			if (Math.random() < p) {
				Map<Integer, Integer> pareja1 = new HashMap<>();

				for (int j = 0; j < aviones; j++) {
					pareja1.put(ind1.gen.v[j], ind2.gen.v[j]);
				}

				Set<Integer> set1 = new HashSet<>();

				int c1 = ind1.gen.v[0];
				set1.add(c1); 
				c1 = pareja1.get(c1);
				while (!set1.contains(c1)) {
					set1.add(c1); 
					c1 = pareja1.get(c1);
				}

				for (int j = 0; j < aviones; j++) {
					if (!set1.contains(ind1.gen.v[j])) {
						int temp = ind1.gen.v[j];
						ind1.gen.v[j] = ind2.gen.v[j];
						ind2.gen.v[j] = temp;
					}
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

		int i = 0, k, e;
		Individuo ind1, ind2;
		int corte1;
		while (i < n) {
			ind1 = new Individuo(selec[i]);
			ind2 = new Individuo(selec[i + 1]);

			// System.out.print("(ANTES) Ind1: "); ind1.printIndividuo();
			// System.out.print("(ANTES) Ind2: "); ind2.printIndividuo();
			if (Math.random() < p) {
				int[] listaDinamica = new int[aviones];

				// codificacion ind1
				for (int j = 0; j < aviones; j++) {
					listaDinamica[j] = j;
				}

				for (int j = 0; j < aviones; j++) {
					k = 0;
					e = 1;
					while (listaDinamica[k] != ind1.gen.v[j]) {
						if (listaDinamica[k] != -1)
							e++;
						k++;
					}
					ind1.gen.v[j] = e;
					listaDinamica[k] = -1;
				}

				// codificacion ind2
				for (int j = 0; j < aviones; j++) {
					listaDinamica[j] = j;
				}

				for (int j = 0; j < aviones; j++) {
					k = 0;
					e = 1;
					while (listaDinamica[k] != ind2.gen.v[j]) {
						if (listaDinamica[k] != -1)
							e++;
						k++;
					}
					ind2.gen.v[j] = e;
					listaDinamica[k] = -1;
				}

				corte1 = (int) (Math.random() * aviones);

				for (int j = 0; j < corte1; j++) {
					int temp = ind1.gen.v[j];
					ind1.gen.v[j] = ind2.gen.v[j];
					ind2.gen.v[j] = temp;
				}

				// descodificacion ind1
				for (int j = 0; j < aviones; j++) {
					listaDinamica[j] = j;
				}

				for (int j = 0; j < aviones; j++) {
					k = -1;
					e = 0;
					while (e < ind1.gen.v[j]) {
						k++;
						if (listaDinamica[k] != -1)
							e++;
					}
					ind1.gen.v[j] = listaDinamica[k];
					listaDinamica[k] = -1;
				}

				// descodificacion ind2
				for (int j = 0; j < aviones; j++) {
					listaDinamica[j] = j;
				}

				for (int j = 0; j < aviones; j++) {
					k = -1;
					e = 0;
					while (e < ind2.gen.v[j]) {
						k++;
						if (listaDinamica[k] != -1)
							e++;
					}
					ind2.gen.v[j] = listaDinamica[k];
					listaDinamica[k] = -1;
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

	
	public Individuo[] custom(Individuo[] selec) {
		int n = selec.length;
		Individuo[] ret= new Individuo[n];
		// TODO
		
		
		
		return ret;
		
	}

}
