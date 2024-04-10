package Logic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Model.Individuo;

public class Cruce {

	private double p;

	private int tam_elite;
	private int num_vuelos;

	// private boolean bin;

	public Cruce(double p, int num_vuelos, int tam_elite) {
		this.p = p;
		this.tam_elite = tam_elite;
		this.num_vuelos = num_vuelos;
	}

	public Individuo[] PMX(Individuo[] selec) {
		int n = selec.length; // num_vuelos = selec[0].gen.v.length;
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

				
				//corte1=rand.nextInt(num_vuelos)+3;
				//corte2=rand.nextInt(num_vuelos-corte1)+corte1+1;
				corte1 = (int) (Math.random() * (num_vuelos - 2));
				corte2 = corte1 + (int) (Math.random() * (num_vuelos - corte1));
				
				//System.out.println("Corte en " + corte1 + " "+ corte2);
				
				for (int j = corte1; j < corte2; j++) {									
					int temp = ind1.gen.v[j];
					ind1.gen.v[j] = ind2.gen.v[j];					
					ind2.gen.v[j] = temp;		
					
					pareja1.put(ind1.gen.v[j], ind2.gen.v[j]);
					pareja2.put(ind2.gen.v[j], ind1.gen.v[j]);
				}
				int x=2;
				for (int j = 0; j < num_vuelos - (corte2 - corte1); j++) {
					if (pareja1.containsKey(ind1.gen.v[(corte2 + j) % num_vuelos])) {
						int p = pareja1.get(ind1.gen.v[(corte2 + j) % num_vuelos]);
						while (pareja1.containsKey(p)) {
							p = pareja1.get(p);
							/*System.out.println("Cortes: " + corte1 + " " + corte2);
							System.out.print("Ind1: "); ind1.printIndividuo();
							System.out.print("Ind2: "); ind2.printIndividuo();
							System.out.println("1: "+p);*/
						}
						ind1.gen.v[(corte2 + j) % num_vuelos] = p;
					} 
					if (pareja2.containsKey(ind2.gen.v[(corte2 + j) % num_vuelos])) {
						int p = pareja2.get(ind2.gen.v[(corte2 + j) % num_vuelos]);
						while (pareja2.containsKey(p)) {
							p = pareja2.get(p);
							/*System.out.println("Cortes: " + corte1 + " " + corte2);
							System.out.print("Ind1: "); ind1.printIndividuo();
							System.out.print("Ind2: "); ind2.printIndividuo();
							System.out.println("2: "+p);*/
						}
						ind2.gen.v[(corte2 + j) % num_vuelos] = p;
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
				corte1 = (int) (Math.random() * (num_vuelos - 2));
				corte2 = corte1 + (int) (Math.random() * (num_vuelos - corte1));

				Set<Integer> set1 = new HashSet<>();
				Set<Integer> set2 = new HashSet<>();

				for (int j = corte1; j < corte2; j++) {
					set1.add(ind2.gen.v[j]);
					set2.add(ind1.gen.v[j]);
				}

				k = corte2;
				for (int j = 0; j < num_vuelos; j++) {
					if (!set1.contains(ind1.gen.v[(corte2 + j) % num_vuelos])) {
						ind1.gen.v[k++ % num_vuelos] = ind1.gen.v[(corte2 + j) % num_vuelos];
					}
				}

				k = corte2;
				for (int j = 0; j < num_vuelos; j++) {
					if (!set2.contains(ind2.gen.v[(corte2 + j) % num_vuelos])) {
						ind2.gen.v[k++ % num_vuelos] = ind2.gen.v[(corte2 + j) % num_vuelos];
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
		int validos[] = new int[num_vuelos-pp];

		while (i < n) {
			ind1 = new Individuo(selec[i]);
			ind2 = new Individuo(selec[i + 1]);

			
			if (Math.random() < p) {
				// System.out.print("(ANTES) Ind1: "); ind1.printIndividuo();
				// System.out.print("(ANTES) Ind2: "); ind2.printIndividuo();

				Set<Integer> puntos = new HashSet<>();
				int puntosList[] = new int[pp];
				Set<Integer> set1 = new HashSet<>();
				Set<Integer> set2 = new HashSet<>();

				int punto;
				for (int j = 0; j < pp; j++) {
					punto = (int) (Math.random() * (num_vuelos - 1));
					while (puntos.contains(punto)) {
						punto = (int) (Math.random() * (num_vuelos - 1));
					}

					puntos.add(punto);
					puntosList[j] = punto;

					set1.add(ind2.gen.v[punto]);
					set2.add(ind1.gen.v[punto]);
				}

				k = 0;
				for (int j = 0; j < num_vuelos; j++) {
					if (!set1.contains(ind1.gen.v[j])) {
						validos[k++] = ind1.gen.v[j];
					}
				}

				ppi = 0;
				k = 0;
				for (int j = 0; j < num_vuelos; j++) {
					if (!puntos.contains(j)) {
						ind1.gen.v[j] = validos[k++];
					}
				}	
				
				k = 0;
				for (int j = 0; j < num_vuelos; j++) {
					if (!set2.contains(ind2.gen.v[j])) {
						validos[k++] = ind2.gen.v[j];
					}
				}

				ppi = 0;
				k = 0;
				for (int j = 0; j < num_vuelos; j++) {
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

			if (Math.random() < p) {
				Map<Integer, Integer> pareja1 = new HashMap<>();

				for (int j = 0; j < num_vuelos; j++) {
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

				for (int j = 0; j < num_vuelos; j++) {
					if (!set1.contains(ind1.gen.v[j])) {
						int temp = ind1.gen.v[j];
						ind1.gen.v[j] = ind2.gen.v[j];
						ind2.gen.v[j] = temp;
					}
				}

			}
			ret[i++] = ind1;
			ret[i++] = ind2;
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

			if (Math.random() < p) {
				int[] listaDinamica = new int[num_vuelos];

				// codificacion ind1
				for (int j = 0; j < num_vuelos; j++) listaDinamica[j] = j;
				for (int j = 0; j < num_vuelos; j++) {
					k = 0;
					e = 1;
					while (listaDinamica[k] != ind1.gen.v[j]) {
						if (listaDinamica[k] != -1) e++;
						k++;
					}
					ind1.gen.v[j] = e;
					listaDinamica[k] = -1;
				}

				// codificacion ind2
				for (int j = 0; j < num_vuelos; j++) listaDinamica[j] = j;
				for (int j = 0; j < num_vuelos; j++) {
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

				corte1 = (int) (Math.random() * num_vuelos);

				for (int j = 0; j < corte1; j++) {
					int temp = ind1.gen.v[j];
					ind1.gen.v[j] = ind2.gen.v[j];
					ind2.gen.v[j] = temp;
				}

				// descodificacion ind1
				for (int j = 0; j < num_vuelos; j++) listaDinamica[j] = j;
				for (int j = 0; j < num_vuelos; j++) {
					k = -1;
					e = 0;
					while (e < ind1.gen.v[j]) {
						k++;
						if (listaDinamica[k] != -1) e++;
					}
					ind1.gen.v[j] = listaDinamica[k];
					listaDinamica[k] = -1;
				}

				// descodificacion ind2
				for (int j = 0; j < num_vuelos; j++) listaDinamica[j] = j;
				for (int j = 0; j < num_vuelos; j++) {
					k = -1;
					e = 0;
					while (e < ind2.gen.v[j]) {
						k++;
						if (listaDinamica[k] != -1) e++;
					}
					ind2.gen.v[j] = listaDinamica[k];
					listaDinamica[k] = -1;
				}

			}
			ret[i++] = ind1;
			ret[i++] = ind2;
		}
		return ret;
	}

	
	// 1. Intercambia las posiciones pares
	// 2. Los huecos restantes se rellenan con los del padre que no esten actualmente
	public Individuo[] custom(Individuo[] selec) {
		int n = selec.length; // num_vuelos = selec[0].gen.v.length;
		Individuo[] ret = new Individuo[n + tam_elite];

		if (n % 2 == 1) {
			ret[n - 1] = selec[n - 1];
			n--; // descarta al ultimo si es impar
		}
		
		Set<Integer> set1=new HashSet<Integer>();
		Set<Integer> set2=new HashSet<Integer>();
		int[] indE1=new int[num_vuelos];
		int[] indE2=new int[num_vuelos];
		
		int tmp;
		int i = 0;
		Individuo ind1, ind2;		
		while (i < n) {
			ind1 = new Individuo(selec[i]);
			ind2 = new Individuo(selec[i+1]);

			
			if (Math.random() < p) {
				set1.clear();
				set2.clear();
				
				for(int j=0;j<num_vuelos;j+=2) {
					indE1[j]=ind2.gen.v[j];
					indE2[j]=ind1.gen.v[j];
					set1.add(indE1[j]);
					set2.add(indE2[j]);
				}
				
				int cont=1;
				for(int x: ind1.gen.v) {
					if(!set1.contains(x)) {
						indE1[cont]=x;
						cont+=2;
					}
				}
				cont=1;
				for(int x: ind2.gen.v) {
					if(!set2.contains(x)) {
						indE2[cont]=x;
						cont+=2;
					}
				}
				ind1=new Individuo(indE1);
				ind2=new Individuo(indE2);
			}
			ret[i++] = ind1;
			ret[i++] = ind2;
		}
		return ret;				
	}

}
