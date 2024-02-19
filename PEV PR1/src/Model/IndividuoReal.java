package Model;

import java.util.Random;

public class IndividuoReal extends Individuo {
	
	//public double[] genes;
	
	public IndividuoReal(int num, double precision) {
		Random rand = new Random();
		double prec=1;
		while(precision!=1) {
			precision*=10;
			prec*=10;
		}
		fenotipo = new double[num];
		fitness = 0;
		for (int i=0;i<num;i++) {
			// Redondea con la precision dada
			fenotipo[i]=Math.round(rand.nextDouble()*Math.PI*prec)/prec;
		}
	}

	public IndividuoReal(Individuo ind) {
		int num = ind.fenotipo.length;
		fenotipo = new double[num];
		fitness = 0;
		
		for (int i = 0; i < num; i++) {
			fenotipo[i] = ind.fenotipo[i];
		}
	 	
	}

	@Override
	public void printIndividuo() {
		for (double g : fenotipo) {			
			System.out.print(g + " ");		
		}
		System.out.println();
		
	}

	@Override
	public void calcular_fenotipo(double[] maximos, double[] minimos) {		
	}

	


}
