package Logic;

import Model.Gen;
import Model.Individuo;
import Model.IndividuoBin;
import Model.IndividuoReal;

public class Cruce {

	private double p;
	
	private boolean bin;

	public Cruce(double p, int funcion_idx) {
		this.p = p;
		this.bin=(funcion_idx<4?true:false);
	}

	public Individuo[] cruce_monopuntoBin(Individuo[] selec) {
		int n = selec.length;
		if (n % 2 == 1) n--; // descarta al ultimo si es impar
		Individuo[] ret = new Individuo[n];

		int[] long_genes = new int[selec[0].genes.length];
		int corte_maximo = -1, cont = 0;
		for (Gen c : selec[0].genes) {
			corte_maximo += c.v.length;
			long_genes[cont++] = c.v.length;
		}
		// int l=corte_maximo+1;
		int i = 0, j = 0, k = 0;
		Individuo ind1, ind2;
		int corte, tmp;
		while (i < n) {		
			ind1 = new IndividuoBin(selec[i]);
			ind2 = new IndividuoBin(selec[i+1]);
		
			
			// System.out.print("(ANTES) Ind1: "); ind1.printIndividuo();
			// System.out.print("(ANTES) Ind2: "); ind2.printIndividuo();
			if (Math.random() < p) {
				corte = (int) (Math.random() * (corte_maximo)) + 1; // [1,corte_maximo]
				cont = 0;
				j = 0;
				// System.out.println("Corte en: " + corte + "
				// ----------------------------------" );
				for (k = 0; k < corte; k++) {
					tmp = ind1.genes[cont].v[j];
					ind1.genes[cont].v[j] = ind2.genes[cont].v[j];
					ind2.genes[cont].v[j] = tmp;
					j++;
					if (j == long_genes[cont]) {
						cont++;
						j = 0;
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

	public Individuo[] cruce_monopuntoReal(Individuo[] selec, int d) {
		int n = selec.length;
		if (n % 2 == 1) n--; // TODO CAMBIAR PARA QUE AÑADA EL ELIMINADO EN LA POBLACION
		Individuo[] ret = new Individuo[n];
		
		int corte_maximo = d-1;
		
		int i = 0, j = 0;
		Individuo ind1, ind2;
		double corte, tmp;
		while (i < n) {		
			ind1 = new IndividuoReal(selec[i]);
			ind2 = new IndividuoReal(selec[i+1]);		
			if (Math.random() < p) {
				corte = (int) (Math.random() * (corte_maximo)) + 1; 
				
				for (j = 0; j < corte; j++) {
					tmp = ind1.fenotipo[j];
					ind1.fenotipo[j]=ind2.fenotipo[j];
					ind2.fenotipo[j]=tmp;				
				}
			}
			ret[i++] = ind1;
			ret[i++] = ind2;
		}
		return ret;
	}
	
	
	public Individuo[] cruce_uniformeBin(Individuo[] selec) {
		int n = selec.length;
		if (n % 2 == 1)
			n--; // descarta al ultimo si es impar
		Individuo[] ret = new Individuo[n];

		int[] long_genes = new int[selec[0].genes.length];
		int cont = 0, l = 0;
		for (Gen c : selec[0].genes) {
			l += c.v.length;
			long_genes[cont++] = c.v.length;
		}

		int i = 0, j = 0, k = 0;
		Individuo ind1, ind2;
		int tmp;
		while (i < n) {
			ind1 = new IndividuoBin(selec[i]);
			ind2 = new IndividuoBin(selec[i+1]);
			
			// System.out.print("(ANTES) Ind1: "); ind1.printIndividuo();
			// System.out.print("(ANTES) Ind2: "); ind2.printIndividuo();
			if (Math.random() > p) {
				// System.out.println("Cruce");
				cont = 0;
				j = 0;
				for (k = 0; k < l; k++) {
					if (Math.random() < 0.5) {
						// System.out.print(k + " ");
						tmp = ind1.genes[cont].v[j];
						ind1.genes[cont].v[j] = ind2.genes[cont].v[j];
						ind2.genes[cont].v[j] = tmp;
					}
					j++;
					if (j == long_genes[cont]) {
						cont++;
						j = 0;
					}
				}
				// System.out.println();
			}
			ret[i++] = ind1;
			ret[i++] = ind2;
			// System.out.print("(DESPUES) Ind1: "); ind1.printIndividuo();
			// System.out.print("(DESPUES) Ind2: "); ind2.printIndividuo();
			// System.out.println();
		}
		return ret;
	}
	
	public Individuo[] cruce_uniformeReal(Individuo[] selec, int d) {
		int n = selec.length;
		if (n % 2 == 1) n--; // descarta al ultimo si es impar
		Individuo[] ret = new Individuo[n];

		
		int i = 0, j = 0;
		Individuo ind1, ind2;
		double corte, tmp;
		while (i < n) {		
			ind1 = new IndividuoReal(selec[i]);
			ind2 = new IndividuoReal(selec[i+1]);		
			if (Math.random() < p) {				
				for (j = 0; j < d; j++) {
					if (Math.random() < 0.5) {
						tmp = ind1.fenotipo[j];
						ind1.fenotipo[j]=ind2.fenotipo[j];
						ind2.fenotipo[j]=tmp;		
					}
				}
			}
			ret[i++] = ind1;
			ret[i++] = ind2;
		}
		return ret;
	}
	
	public Individuo[] cruce_aritmetico(Individuo[] selec) {
		return null;
	}
	
	
	public Individuo[] cruce_BLX(Individuo[] selec) {
		return null;
	}
}
