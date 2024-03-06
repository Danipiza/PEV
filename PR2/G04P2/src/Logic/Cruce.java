package Logic;

import Model.IndividuoReal;

public class Cruce {

	private double p;
	
	private int tam_elite;

	//private boolean bin;

	public Cruce(double p, int funcion_idx, int tam_elite) {
		this.p = p;
		this.tam_elite=tam_elite;
		//this.bin = (funcion_idx < 4 ? true : false);
	}

	

	public IndividuoReal[] aritmetico(IndividuoReal[] selec, int d, double a) {
		int n = selec.length;
		IndividuoReal[] ret = new IndividuoReal[n+tam_elite];
		//n-=tam_elite;
		
		if (n % 2 == 1) {
			ret[n - 1] = selec[n - 1];
			n--; // descarta al ultimo si es impar
		}

		int i = 0, j = 0;
		IndividuoReal ind1, ind2;
		double tmp1, tmp2;
		while (i < n) {
			ind1 = new IndividuoReal(selec[i]);
			ind2 = new IndividuoReal(selec[i + 1]);
			if (Math.random() < p) {
				for (j = 0; j < d; j++) {
					tmp1 = ind1.genes[j] * a + ind2.genes[j] * (1 - a);
					tmp2 = ind2.genes[j] * a + ind1.genes[j] * (1 - a);
					ind1.genes[j] = tmp1;
					ind2.genes[j] = tmp2;
				}
			}
			ret[i++] = ind1;
			ret[i++] = ind2;
		}
		return ret;
	}

	public IndividuoReal[] BLX(IndividuoReal[] selec, int d, double a) {
		int n = selec.length;
		IndividuoReal[] ret = new IndividuoReal[n+tam_elite];
		//n-=tam_elite;
		
		if (n % 2 == 1) {
			ret[n - 1] = selec[n - 1];
			n--; // descarta al ultimo si es impar
		}

		int i = 0, j = 0;
		IndividuoReal ind1, ind2;
		double cMax, cMin, I;
		while (i < n) {
			ind1 = new IndividuoReal(selec[i]);
			ind2 = new IndividuoReal(selec[i + 1]);
			if (Math.random() < p) {
				for (j = 0; j < d; j++) {
					cMax = (ind1.genes[j] > ind2.genes[j] ? ind1.genes[j] : ind2.genes[j]);
					cMin = (ind1.genes[j] < ind2.genes[j] ? ind1.genes[j] : ind2.genes[j]);
					I = cMax - cMin;
					ind1.genes[j] = cMin + Math.random() * I;
					ind2.genes[j] = cMin + Math.random() * I;
				}
			}
			ret[i++] = ind1;
			ret[i++] = ind2;
		}
		return ret;
	}

	// TODO
	public IndividuoReal[] PMX(IndividuoReal[] selec) {
		int n = selec.length;
		IndividuoReal[] ret = new IndividuoReal[n+tam_elite];
		//n-=tam_elite;
		
		if (n % 2 == 1) {
			ret[n - 1] = selec[n - 1];
			n--; // descarta al ultimo si es impar
		}

		
		return ret;
	}

	// TODO
	public IndividuoReal[] OX(IndividuoReal[] selec) {
		int n = selec.length;
		IndividuoReal[] ret = new IndividuoReal[n+tam_elite];
		//n-=tam_elite;
		
		if (n % 2 == 1) {
			ret[n - 1] = selec[n - 1];
			n--; // descarta al ultimo si es impar
		}

		
		return ret;
	}

	// TODO
	public IndividuoReal[] OX_PP(IndividuoReal[] selec) {
		int n = selec.length;
		IndividuoReal[] ret = new IndividuoReal[n+tam_elite];
		//n-=tam_elite;
		
		if (n % 2 == 1) {
			ret[n - 1] = selec[n - 1];
			n--; // descarta al ultimo si es impar
		}

		
		return ret;
	}

	// TODO
	public IndividuoReal[] CX(IndividuoReal[] selec) {
		int n = selec.length;
		IndividuoReal[] ret = new IndividuoReal[n+tam_elite];
		//n-=tam_elite;
		
		if (n % 2 == 1) {
			ret[n - 1] = selec[n - 1];
			n--; // descarta al ultimo si es impar
		}

		
		return ret;
	}

	// TODO
	public IndividuoReal[] CO(IndividuoReal[] selec) {
		int n = selec.length;
		IndividuoReal[] ret = new IndividuoReal[n+tam_elite];
		//n-=tam_elite;
		
		if (n % 2 == 1) {
			ret[n - 1] = selec[n - 1];
			n--; // descarta al ultimo si es impar
		}

		
		return ret;
	}
	
	// TODO
	public IndividuoReal[] custom(IndividuoReal[] selec) {
		int n = selec.length;
		IndividuoReal[] ret = new IndividuoReal[n+tam_elite];
		//n-=tam_elite;
		
		if (n % 2 == 1) {
			ret[n - 1] = selec[n - 1];
			n--; // descarta al ultimo si es impar
		}

		
		return ret;
	}
	
	
	
	
}
